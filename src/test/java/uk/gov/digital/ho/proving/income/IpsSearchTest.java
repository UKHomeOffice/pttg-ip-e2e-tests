package uk.gov.digital.ho.proving.income;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import uk.gov.digital.ho.proving.income.domain.AccessCode;
import uk.gov.digital.ho.proving.income.domain.Applicant;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

public class IpsSearchTest {

    private WebDriver driver;
    private IpsSearchPage ipsSearchPage;

    private ObjectMapper objectMapper = new ObjectMapper();

    static final String MATCH_ID = "MATCH-ID";
    static final String ACCESS_ID = "ACCESS-ID";

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(options()
            .port(8111)
            .httpsPort(8112));

    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    @BeforeClass
    public static void setUpClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setUpTest() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");

        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        ipsSearchPage = new IpsSearchPage(driver);
        ipsSearchPage.start();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void thatInvalidSearchesShowErrors() {
        ipsSearchPage.search();
        assertThat(ipsSearchPage.getErrorSummaryHeader()).isNotNull().withFailMessage("The error summary should be displayed");
    }

    @Test
    public void thatUnknownIndividualReturnsNoRecord() throws IOException {
        createFailedMatchStubs();
        Applicant applicant = new Applicant("Val", "Lee", LocalDate.of(1953, 12, 6), "YS255610C");
        ipsSearchPage.search(applicant);
        assertThat(ipsSearchPage.getPageHeading()).isNotNull().withFailMessage("The page heading should exist");
        assertThat(ipsSearchPage.getPageHeading().getText()).contains("There is no record").withFailMessage("The page heading should indicate failure");
    }

    @Test
    public void thatPassingIndividualReturnsSuccess() throws IOException {
        createStubs();
        Applicant applicant = new Applicant("Laurie", "Halford", LocalDate.of(1992, 3, 1), "GH576240A");
        ipsSearchPage.search(applicant);
        assertThat(ipsSearchPage.getPageHeading()).isNotNull().withFailMessage("The page heading should exist");
        assertThat(ipsSearchPage.getPageHeading().getText()).contains("Passed").withFailMessage("The page heading should indicate success");
    }

    public void createFailedMatchStubs() throws IOException {

        stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withBody(buildAccessCodeResponse())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));

        stubFor(post(urlEqualTo("/individuals/matching/"))
                .willReturn(aResponse().withStatus(HttpStatus.FORBIDDEN.value())
                        .withBody(buildFailedMatchResponse())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));

    }

    public void createStubs() throws IOException {

        stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withBody(buildAccessCodeResponse())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));

        stubFor(post(urlEqualTo("/individuals/matching/"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withBody(buildMatchResponse())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)));

        stubFor(get(urlEqualTo("/individuals/matching/" + MATCH_ID))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(buildMatchedIndividualResponse())));

        stubFor(get(urlEqualTo("/individuals/income/?matchId=" + MATCH_ID))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(buildIncomeResponse())));

        stubFor(get(urlEqualTo("/individuals/employments/?matchId=" + MATCH_ID))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(buildEmploymentsResponse())));

        stubFor(get(urlMatching("/individuals/employments/paye\\?matchId=" + MATCH_ID + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(buildEmploymentsPayeResponse())));

        stubFor(get(urlMatching("/individuals/income/paye\\?matchId=" + MATCH_ID + "&fromDate=[0-9\\-]*&toDate=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(buildPayeIncomeResponse())));

        stubFor(get(urlMatching("/individuals/income/sa\\?matchId=" + MATCH_ID + "&fromTaxYear=[0-9\\-]*&toTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(buildEmptySaResponse())));

        stubFor(get(urlMatching("/individuals/income/sa/self-employments\\?matchId=" + MATCH_ID + "&fromTaxYear=[0-9\\-]*"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(buildSaSelfEmploymentResponse())));

    }

    private String asJson(Object input) throws JsonProcessingException {
        return objectMapper.writeValueAsString(input);
    }

    private String buildAccessCodeResponse() throws IOException {
        return getResponseFile("/template/accessCodeResponse.json");
    }

    private String buildMatchResponse() throws IOException {
        return getResponseFile("/template/matchResponse.json")
                .replace("${matchId}", MATCH_ID);
    }

    private String buildFailedMatchResponse() throws IOException {
        return getResponseFile("/template/failedMatchResponse.json")
                .replace("${matchId}", MATCH_ID);
    }

    private String buildMatchedIndividualResponse() throws IOException {
        return getResponseFile("/template/individualMatchResponse.json")
                .replace("${matchId}", MATCH_ID);
    }

    private String buildOauthResponse() throws JsonProcessingException {
        return asJson(new AccessCode(ACCESS_ID, null));
    }

    private String buildIncomeResponse() throws IOException {
        return getResponseFile("/template/incomeResponse.json")
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", MATCH_ID);
    }

    private String buildPayeIncomeResponse() throws IOException {
        return getResponseFile("/template/incomePayeResponse.json")
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", MATCH_ID);
    }


    private String buildEmploymentsResponse() throws IOException {
        return getResponseFile("/template/employmentsResponse.json")
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", MATCH_ID);
    }

    private String buildEmploymentsPayeResponse() throws IOException {
        return getResponseFile("/template/employmentsPayeResponse.json")
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", MATCH_ID);
    }

    private String buildEmptySaResponse() throws IOException {
        return getResponseFile("/template/incomeSAResponseEmpty.json")
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", MATCH_ID);
    }

    private String buildSaSelfEmploymentResponse() throws IOException {
        return getResponseFile("/template/incomeSASelfEmploymentsResponse.json")
                .replace("${port}", Integer.valueOf(wireMockRule.port()).toString())
                .replace("${matchId}", MATCH_ID);
    }
    
    private String getResponseFile(String fileName) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream(fileName), Charset.defaultCharset());
    }

}

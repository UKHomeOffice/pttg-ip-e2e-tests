package uk.gov.digital.ho.proving.income;


import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import uk.gov.digital.ho.proving.income.domain.Applicant;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class IpsSearchTest {

    private WebDriver driver;
    private IpsSearchPage ipsSearchPage;
    private HmrcStub hmrcStub = new HmrcStub();

    private String forename = "Laurie";
    private String surname = "Halford";
    private String fullname = forename + " " + surname;


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
        assertThat(ipsSearchPage.getErrorSummaryHeader()).isNotNull();
    }

    @Test
    public void thatUnknownIndividualReturnsNoRecord() throws IOException {
        hmrcStub.createFailedMatchStubs();
        Applicant applicant = new Applicant("Val", "Lee", LocalDate.of(1953, 12, 6), "YS255610C");
        ipsSearchPage.search(applicant);
        assertThat(ipsSearchPage.getPageHeading()).isNotNull();
        assertThat(ipsSearchPage.getPageHeading().getText()).contains("There is no record");
    }

    @Test
    public void thatPassingIndividualReturnsSuccess() throws IOException {
        hmrcStub.stubPassUser();
        submitValidApplicant();
        assertThat(ipsSearchPage.getPageHeading()).isNotNull();
        assertEquals("Passed", ipsSearchPage.getPageHeading().getText());
        assertEquals(fullname + " meets the Income Proving requirement",
                ipsSearchPage.getPageHeadingContent().getText());
        assertEquals("Results", ipsSearchPage.getResultHeading().getText());
        assertEquals(fullname, ipsSearchPage.getApplicantFullName().getText());
        assertEquals("03/07/2017", ipsSearchPage.getIncomeFromDate().getText());
        assertEquals("03/07/2018", ipsSearchPage.getIncomeToDate().getText());
    }

    @Test
    public void thatCatAPassingIndividualReturnsSuccess() throws IOException {
        hmrcStub.stubCatAPassUser();
        submitValidApplicant();
        assertThat(ipsSearchPage.getPageHeading()).isNotNull();
        assertEquals("Passed", ipsSearchPage.getPageHeading().getText());
        assertEquals(fullname + " meets the Income Proving requirement",
                ipsSearchPage.getPageHeadingContent().getText());
        assertEquals("Results", ipsSearchPage.getResultHeading().getText());
        assertEquals(fullname, ipsSearchPage.getApplicantFullName().getText());
        assertEquals("03/01/2018", ipsSearchPage.getIncomeFromDate().getText());
        assertEquals("03/07/2018", ipsSearchPage.getIncomeToDate().getText());
    }

    public void submitValidApplicant() {
        Applicant applicant = new Applicant(forename, surname, LocalDate.of(1992, 3, 1), "GH576240A");
        ipsSearchPage.search(applicant);
    }

}

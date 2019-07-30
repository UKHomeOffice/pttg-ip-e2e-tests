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
import java.util.ArrayList;
import java.util.Arrays;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IpsSearchTest {

    private WebDriver driver;
    private IpsSearchPage ipsSearchPage;
    private HmrcStub hmrcStub = new HmrcStub();

    private Applicant mainApplicant;
    private Applicant partnerApplicant;
    private String forename = "Laurie";
    private String surname = "Halford";
    private String fullname = forename + " " + surname;
    private String partnerForename = "John";
    private String partnerSurname = "Campbell";
    private String partnerFullname = partnerForename + " " + partnerSurname;
    private ArrayList<String> applicantEmployers = new ArrayList<>();
    private ArrayList<String> partnerEmployers = new ArrayList<>();

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
        applicantEmployers.clear();
        partnerEmployers.clear();
    }

    @Test
    public void thatInvalidSearchesShowErrors() {
        ipsSearchPage.search();
        assertThat(ipsSearchPage.getErrorSummaryHeader()).isNotNull();
    }

    @Test
    public void thatUnknownIndividualReturnsNoRecord() throws IOException {
        hmrcStub.createFailedMatchStubs();
        Applicant applicant = new Applicant("Val", "Lee", LocalDate.of(1953, 12, 6), "YS255610C", null);
        ipsSearchPage.search(applicant);
        assertThat(ipsSearchPage.getPageHeading()).isNotNull();
        assertThat(ipsSearchPage.getPageHeading().getText()).contains("There is no record");
    }

    @Test
    public void thatPassingIndividualReturnsSuccess() throws IOException {
        applicantEmployers.add("Acme Inc");
        applicantEmployers.add("Disney Inc");

        mainApplicant = new Applicant(forename, surname, LocalDate.of(1992, 3, 1), "GH576240A", applicantEmployers);

        hmrcStub.stubPassUser(mainApplicant);
        submitValidApplicant();
        assertThat(ipsSearchPage.getPageHeading()).isNotNull();
        assertEquals(
                "Outcome '" + ipsSearchPage.getPageHeading().getText() + "' is different from " +
                        "the expected value 'Passed'",
                "Passed",
                ipsSearchPage.getPageHeading().getText());
        assertEquals(
                "Outcome header '" + ipsSearchPage.getPageHeadingContent().getText() + "' is different from " +
                        "the expected value '" + fullname + " meets the Income Proving requirement'",
                fullname + " meets the Income Proving requirement",
                ipsSearchPage.getPageHeadingContent().getText());
        assertEquals(
                "Result header '" + ipsSearchPage.getResultHeading().getText() + "' is different from " +
                        "the expected value 'Results'",
                "Results",
                ipsSearchPage.getResultHeading().getText());
        assertEquals(
                "Full name '" + ipsSearchPage.getApplicantFullName().getText() + "' is different from " +
                        "the expected value '" + fullname + "'",
                fullname,
                ipsSearchPage.getApplicantFullName().getText());
        assertEquals(
                "Income From date '" + ipsSearchPage.getIncomeFromDate().getText() + "' is different from " +
                        "the expected value '03/07/2017'",
                "03/07/2017",
                ipsSearchPage.getIncomeFromDate().getText());
        assertEquals(
                "Income To date '" + ipsSearchPage.getIncomeToDate().getText() + "' is different from " +
                        "the expected value '03/07/2018'",
                "03/07/2018",
                ipsSearchPage.getIncomeToDate().getText());
        assertTrue("Employers list don't match. Expected " + Arrays.toString(applicantEmployers.toArray()) +
                        " but found " + Arrays.toString(ipsSearchPage.getEmploymentList().toArray()),
                ipsSearchPage.getEmploymentList().containsAll(applicantEmployers));
    }

    @Test
    public void thatCatAPassingIndividualReturnsSuccess() throws IOException {
        applicantEmployers.add("Acme Inc");

        mainApplicant = new Applicant(forename, surname, LocalDate.of(1992, 3, 1), "GH576240A", applicantEmployers);

        hmrcStub.stubCatAPassUser(mainApplicant);
        submitValidApplicant();
        assertThat(ipsSearchPage.getPageHeading()).isNotNull();
        assertEquals(
                "Outcome '" + ipsSearchPage.getPageHeading().getText() + "' is different from " +
                        "the expected value 'Passed'",
                "Passed",
                ipsSearchPage.getPageHeading().getText());
        assertEquals(
                "Outcome header '" + ipsSearchPage.getPageHeadingContent().getText() + "' is different from " +
                        "the expected value '" + fullname + " meets the Income Proving requirement'",
                fullname + " meets the Income Proving requirement",
                ipsSearchPage.getPageHeadingContent().getText());
        assertEquals(
                "Result header '" + ipsSearchPage.getResultHeading().getText() + "' is different from " +
                        "the expected value 'Results'",
                "Results",
                ipsSearchPage.getResultHeading().getText());
        assertEquals(
                "Full name '" + ipsSearchPage.getApplicantFullName().getText() + "' is different from " +
                        "the expected value '" + fullname + "'",
                fullname,
                ipsSearchPage.getApplicantFullName().getText());
        assertEquals(
                "Income From date '" + ipsSearchPage.getIncomeFromDate().getText() + "' is different from " +
                        "the expected value '03/01/2018'",
                "03/01/2018",
                ipsSearchPage.getIncomeFromDate().getText());
        assertEquals(
                "Income To date '" + ipsSearchPage.getIncomeToDate().getText() + "' is different from " +
                        "the expected value '03/07/2018'",
                "03/07/2018",
                ipsSearchPage.getIncomeToDate().getText());
        assertTrue("Employers list don't match. Expected " + Arrays.toString(applicantEmployers.toArray()) +
                        " but found " + Arrays.toString(ipsSearchPage.getEmploymentList().toArray()),
                ipsSearchPage.getEmploymentList().containsAll(applicantEmployers));
    }

    @Test
    public void thatCatBPassingIndividualReturnsSuccess() throws IOException {
        applicantEmployers.add("Acme Inc");
        applicantEmployers.add("Disney Inc");

        mainApplicant = new Applicant(forename, surname, LocalDate.of(1992, 3, 1), "GH576240A", applicantEmployers);

        hmrcStub.stubCatBPassUser(mainApplicant);
        submitValidApplicant();
        assertThat(ipsSearchPage.getPageHeading()).isNotNull();
        assertEquals(
                "Outcome '" + ipsSearchPage.getPageHeading().getText() + "' is different from " +
                        "the expected value 'Passed'",
                "Passed",
                ipsSearchPage.getPageHeading().getText());
        assertEquals(
                "Outcome header '" + ipsSearchPage.getPageHeadingContent().getText() + "' is different from " +
                        "the expected value '" + fullname + " meets the Income Proving requirement'",
                fullname + " meets the Income Proving requirement",
                ipsSearchPage.getPageHeadingContent().getText());
        assertEquals(
                "Result header '" + ipsSearchPage.getResultHeading().getText() + "' is different from " +
                        "the expected value 'Results'",
                "Results",
                ipsSearchPage.getResultHeading().getText());
        assertEquals(
                "Full name '" + ipsSearchPage.getApplicantFullName().getText() + "' is different from " +
                        "the expected value '" + fullname + "'",
                fullname,
                ipsSearchPage.getApplicantFullName().getText());
        assertEquals(
                "Income From date '" + ipsSearchPage.getIncomeFromDate().getText() + "' is different from " +
                        "the expected value '03/07/2017'",
                "03/07/2017",
                ipsSearchPage.getIncomeFromDate().getText());
        assertEquals(
                "Income To date '" + ipsSearchPage.getIncomeToDate().getText() + "' is different from " +
                        "the expected value '03/07/2018'",
                "03/07/2018",
                ipsSearchPage.getIncomeToDate().getText());
        assertTrue("Employers list don't match. Expected " + Arrays.toString(applicantEmployers.toArray()) +
                        " but found " + Arrays.toString(ipsSearchPage.getEmploymentList().toArray()),
                ipsSearchPage.getEmploymentList().containsAll(applicantEmployers));
    }

    @Test
    public void thatCatBNotPassingIndividualReturnsFail() throws IOException {
        applicantEmployers.add("Acme Inc");
        applicantEmployers.add("Disney Inc");
        applicantEmployers.add("Macro Ltd");

        mainApplicant = new Applicant(forename, surname, LocalDate.of(1992, 3, 1), "GH576240A", applicantEmployers);

        hmrcStub.stubCatBNotPassUser(mainApplicant);
        submitValidApplicant();
        assertThat(ipsSearchPage.getPageHeading()).isNotNull();
        assertEquals(
                "Outcome '" + ipsSearchPage.getPageHeading().getText() + "' is different from " +
                        "the expected value 'Not passed'",
                "Not passed",
                ipsSearchPage.getPageHeading().getText());
        assertEquals(
                "Outcome header '" + ipsSearchPage.getPageHeadingContent().getText() + "' is different from " +
                        "the expected value '" + fullname + " does not meet the Income Proving requirement'",
                fullname + " does not meet the Income Proving requirement",
                ipsSearchPage.getPageHeadingContent().getText());
        assertEquals(
                "Result header '" + ipsSearchPage.getResultHeading().getText() + "' is different from " +
                        "the expected value 'Results'",
                "Results",
                ipsSearchPage.getResultHeading().getText());
        assertEquals(
                "Full name '" + ipsSearchPage.getApplicantFullName().getText() + "' is different from " +
                        "the expected value '" + fullname + "'",
                fullname,
                ipsSearchPage.getApplicantFullName().getText());
        assertTrue("Employers list don't match. Expected " + Arrays.toString(applicantEmployers.toArray()) +
                        " but found " + Arrays.toString(ipsSearchPage.getEmploymentList().toArray()),
                ipsSearchPage.getEmploymentList().containsAll(applicantEmployers));
    }

    @Test
    public void thatCatANotPassingIndividualReturnsFail() throws IOException {

        applicantEmployers.add("Acme Inc");
        applicantEmployers.add("Disney Inc");
        partnerEmployers.add("Macro Ltd");

        mainApplicant = new Applicant(forename, surname, LocalDate.of(1992, 3, 1),
                "GH576240A", applicantEmployers);
        partnerApplicant = new Applicant(partnerForename, partnerSurname, LocalDate.of(1984, 4, 30),
                "TW308454C", partnerEmployers);

        hmrcStub.stubCatANotPassUser(mainApplicant, partnerApplicant);

        submitValidApplicantWithPartner();
        assertThat(ipsSearchPage.getPageHeading()).isNotNull();
        assertEquals(
                "Outcome '" + ipsSearchPage.getPageHeading().getText() + "' is different from " +
                        "the expected value 'Not passed'",
                "Not passed",
                ipsSearchPage.getPageHeading().getText());
        assertEquals(
                "Outcome header '" + ipsSearchPage.getPageHeadingContent().getText() + "' is different from " +
                        "the expected value '" + fullname + " does not meet the Income Proving requirement'",
                fullname + " does not meet the Income Proving requirement",
                ipsSearchPage.getPageHeadingContent().getText());
        assertEquals(
                "Result header '" + ipsSearchPage.getResultHeading().getText() + "' is different from " +
                        "the expected value 'Results'",
                "Results",
                ipsSearchPage.getResultHeading().getText());
        assertEquals(
                "Full name '" + ipsSearchPage.getApplicantFullName().getText() + "' is different from " +
                        "the expected value '" + fullname + "'",
                fullname,
                ipsSearchPage.getApplicantFullName().getText());
        assertEquals(
                "Partner Full name '" + ipsSearchPage.getPartnerFullName().getText() + "' is different from " +
                        "the expected value '" + partnerFullname + "'",
                partnerFullname,
                ipsSearchPage.getPartnerFullName().getText());
        assertTrue("Employers list don't match. Expected " + Arrays.toString(applicantEmployers.toArray()) +
                        Arrays.toString(partnerEmployers.toArray()) +
                        " but found " + Arrays.toString(ipsSearchPage.getEmploymentList().toArray()),
                ipsSearchPage.getEmploymentList().containsAll(applicantEmployers) &&
                        ipsSearchPage.getEmploymentList().containsAll(partnerEmployers));
    }

    @Test
    public void thatCatFPassingIndividualReturnsPotentialPass() throws IOException {
        applicantEmployers.add("Acme Inc");

        mainApplicant = new Applicant(forename, surname, LocalDate.of(1992, 3, 1), "GH576240A", applicantEmployers);

        hmrcStub.stubCatFPassUser(mainApplicant);
        submitValidApplicant();
        assertThat(ipsSearchPage.getPageHeading()).isNotNull();
        assertEquals(
                "Outcome '" + ipsSearchPage.getPageHeading().getText() + "' is different from " +
                        "the expected value 'Potential Pass'",
                "Potential Pass",
                ipsSearchPage.getPageHeading().getText());
        assertEquals(
                "Outcome header '" + ipsSearchPage.getPageHeadingContent().getText() + "' is different from " +
                        "the expected value 'Check for evidence of current self employment'",
                "Check for evidence of current self employment",
                ipsSearchPage.getPageHeadingContent().getText());
        assertEquals(
                "Result header '" + ipsSearchPage.getResultHeading().getText() + "' is different from " +
                        "the expected value 'Results'",
                "Results",
                ipsSearchPage.getResultHeading().getText());
        assertEquals(
                "Full name '" + ipsSearchPage.getApplicantFullName().getText() + "' is different from " +
                        "the expected value '" + fullname + "'",
                fullname,
                ipsSearchPage.getApplicantFullName().getText());
        assertEquals(
                "Income From date '" + ipsSearchPage.getIncomeFromDate().getText() + "' is different from " +
                        "the expected value '06/04/2017'",
                "06/04/2017",
                ipsSearchPage.getIncomeFromDate().getText());
        assertEquals(
                "Income To date '" + ipsSearchPage.getIncomeToDate().getText() + "' is different from " +
                        "the expected value '05/04/2018'",
                "05/04/2018",
                ipsSearchPage.getIncomeToDate().getText());
    }

    private void submitValidApplicant() {
        ipsSearchPage.search(mainApplicant);
    }

    private void submitValidApplicantWithPartner() {
        ipsSearchPage.search(mainApplicant, partnerApplicant);
    }

}

package uk.gov.digital.ho.proving.income;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import uk.gov.digital.ho.proving.income.domain.Applicant;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class IpsSearchTest {

    private WebDriver driver;
    private IpsSearchPage ipsSearchPage;

    @BeforeClass
    public static void setUpClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setUpTest() throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.acceptInsecureCerts();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), capabilities);
        ipsSearchPage = new IpsSearchPage(driver);
        ipsSearchPage.start();
    }

    @Test
    public void thatInvalidSearchesShowErrors() {
        ipsSearchPage.search();
        assertThat(ipsSearchPage.getErrorSummaryHeader()).isNotNull().withFailMessage("The error summary should be displayed");
    }

    @Test
    public void thatUnknownIndividualReturnsNoRecord() {
        Applicant applicant = new Applicant("Val", "Lee", LocalDate.of(1953, 12, 6), "YS255610C");
        ipsSearchPage.search();
        assertThat(ipsSearchPage.getPageHeading()).isNotNull().withFailMessage("The page heading exists");
        assertThat(ipsSearchPage.getPageHeading().getText()).contains("There is no record");
    }

}

package uk.gov.digital.ho.proving.income;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

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
    }

    @Test
    @Ignore
    public void thatInvalidSearchesShowErrors() {
        ipsSearchPage.start();
        ipsSearchPage.search();
        assertThat(ipsSearchPage.getErrorSummaryHeader()).isNotNull().withFailMessage("The error summary should be displayed");
    }

    @After
    public void tearDown()  {
//        webDriver.quit();
    }

}

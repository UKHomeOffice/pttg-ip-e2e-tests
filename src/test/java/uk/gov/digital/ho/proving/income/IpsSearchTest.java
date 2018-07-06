package uk.gov.digital.ho.proving.income;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class IpsSearchTest {

    private WebDriver driver;
    private IpsSearchPage ipsSearchPage;

    @BeforeClass
    public static void setUpClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setUpTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        ChromeDriverService chromeDriverService = new ChromeDriverService.Builder().usingPort(4444).build();
        driver = new ChromeDriver(chromeDriverService, options);
        ipsSearchPage = new IpsSearchPage(driver);

    }

    @Test
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

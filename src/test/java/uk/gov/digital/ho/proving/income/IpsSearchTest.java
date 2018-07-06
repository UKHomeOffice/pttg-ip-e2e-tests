package uk.gov.digital.ho.proving.income;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class IpsSearchTest {

    private WebDriver webDriver;
    private IpsSearchPage ipsSearchPage;

    @BeforeClass
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setUpTest() {
        webDriver = new ChromeDriver();
        ipsSearchPage = new IpsSearchPage(webDriver);

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

package uk.gov.digital.ho.proving.income;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GoogleTest {

    private WebDriver driver;
    private GooglePage google;

    @BeforeClass
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setUpTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        google = new GooglePage(driver);
    }

    @Test
    public void googleTest() throws InterruptedException {
        google.goTo();
        google.searchFor("automation");
        Assert.assertTrue(google.getResults().size() >= 10);
    }

    @After
    public void tearDown()  {
//        driver.quit();
    }

}
package uk.gov.digital.ho.proving.income;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleTest.class);

    private WebDriver driver;
    private GooglePage google;

    @BeforeClass
    public static void setUpClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setUpTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        try {
            LOGGER.info("Creating new chrome driver");
            driver = new ChromeDriver(options);
        } catch(Exception e) {
            LOGGER.error("Failed to create chrome web driver due to ", e);
            throw(e);
        }
        google = new GooglePage(driver);
    }

    @Test
    @Ignore
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
package uk.gov.digital.ho.proving.income;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IpsSearchPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "forename")
    private WebElement forename;

    @FindBy(id = "surname")
    private WebElement surname;

    @FindBy(id = "dateOfBirthDay")
    private WebElement dateOfBirthDay;

    @FindBy(id = "dateOfBirthMonth")
    private WebElement dateOfBirthMonth;

    @FindBy(id = "dateOfBirthYear")
    private WebElement dateOfBirthYear;

    @FindBy(id = "nino")
    private WebElement nino;

    @FindBy(id = "dependants")
    private WebElement dependants;

    @FindBy(id = "applicationRaisedDateDay")
    private WebElement applicationRaisedDateDay;

    @FindBy(id = "applicationRaisedDateMonth")
    private WebElement applicationRaisedDateMonth;

    @FindBy(id = "applicationRaisedDateYear")
    private WebElement applicationRaisedDateYear;

    @FindBy(id = "searchBtn")
    private WebElement searchButton;

    @FindBy(id = "kc-login")
    private WebElement loginButton;

    @FindBy(id = "validation-error-summary-heading")
    private WebElement errorSummaryHeader;

    @FindBy(id = "error-summary-heading-example-1")
    private WebElement loginErrorSummaryHeader;

    public IpsSearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 30);
    }

    public void start() {
        this.driver.get("https://ui:8000");
    }

    public void search() {
        this.searchButton.click();
    }

    public WebElement getErrorSummaryHeader() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("validation-error-summary-heading")));
        return errorSummaryHeader;
    }

    public void login() {
        this.loginButton.click();
    }

    public WebElement getLoginErrorSummaryHeader() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("error-summary-heading-example-1")));
        return loginErrorSummaryHeader;
    }
}

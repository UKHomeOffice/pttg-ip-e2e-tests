package uk.gov.digital.ho.proving.income;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.digital.ho.proving.income.domain.Applicant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IpsSearchPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpsSearchPage.class);


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

    @FindBy(id = "secondIndividualBtn")
    private WebElement secondIndividualLink;

    @FindBy(id = "partnerForename")
    private WebElement partnerForename;

    @FindBy(id = "partnerSurname")
    private WebElement partnerSurname;

    @FindBy(id = "partnerDateOfBirthDay")
    private WebElement partnerDateOfBirthDay;

    @FindBy(id = "partnerDateOfBirthMonth")
    private WebElement partnerDateOfBirthMonth;

    @FindBy(id = "partnerDateOfBirthYear")
    private WebElement partnerDateOfBirthYear;

    @FindBy(id = "partnerNino")
    private WebElement partnerNino;

    @FindBy(id = "dependants")
    private WebElement dependants;

    @FindBy(id = "applicationRaisedDateDay")
    private WebElement applicationRaisedDateDay;

    @FindBy(id = "applicationRaisedDateMonth")
    private WebElement applicationRaisedDateMonth;

    @FindBy(id = "applicationRaisedDateYear")
    private WebElement applicationRaisedDateYear;

    @FindBy(id = "submitBtn")
    private WebElement searchButton;

    @FindBy(id = "validation-error-summary-heading")
    private WebElement errorSummaryHeader;

    @FindBy(id = "pageDynamicHeading")
    private WebElement pageHeading;

    @FindBy(id = "outcomeBoxSummary")
    private WebElement pageHeadingContent;

    @FindBy(css = "div[ng-if=haveResult] > h2")
    private WebElement resultHeading;

    @FindBy(id = "yourSearchIndividualName0")
    private WebElement applicantFullName;

    @FindBy(id = "yourSearchIndividualName1")
    private WebElement partnerFullName;

    @FindBy(id = "outcomeFromDate0")
    private WebElement incomeFromDate;

    @FindBy(id = "outcomeToDate0")
    private WebElement incomeToDate;

    @FindBy(css = "li[ng-repeat='e in i.employers']")
    private List<WebElement> employmentList;

    public IpsSearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 40);
    }

    public void start() {
        this.driver.get("http://ui:8000/#!/familymigration");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("submitBtn")));
    }

    public void search() {
        this.searchButton.click();
    }

    public void search(Applicant applicant) {
        forename.sendKeys(applicant.forename());
        surname.sendKeys(applicant.surname());
        dateOfBirthDay.sendKeys(Integer.valueOf(applicant.dateOfBirth().getDayOfMonth()).toString());
        dateOfBirthMonth.sendKeys(Integer.valueOf(applicant.dateOfBirth().getMonthValue()).toString());
        dateOfBirthYear.sendKeys(Integer.valueOf(applicant.dateOfBirth().getYear()).toString());
        nino.sendKeys(applicant.nino());

        dependants.sendKeys("0");
        applicationRaisedDateDay.sendKeys("3");
        applicationRaisedDateMonth.sendKeys("7");
        applicationRaisedDateYear.sendKeys("2018");

        search();
    }

    public void search(Applicant applicant, Applicant partner) {
        forename.sendKeys(applicant.forename());
        surname.sendKeys(applicant.surname());
        dateOfBirthDay.sendKeys(Integer.valueOf(applicant.dateOfBirth().getDayOfMonth()).toString());
        dateOfBirthMonth.sendKeys(Integer.valueOf(applicant.dateOfBirth().getMonthValue()).toString());
        dateOfBirthYear.sendKeys(Integer.valueOf(applicant.dateOfBirth().getYear()).toString());
        nino.sendKeys(applicant.nino());

        secondIndividualLink.click();
        partnerForename.sendKeys(partner.forename());
        partnerSurname.sendKeys(partner.surname());
        partnerDateOfBirthDay.sendKeys(Integer.valueOf(partner.dateOfBirth().getDayOfMonth()).toString());
        partnerDateOfBirthMonth.sendKeys(Integer.valueOf(partner.dateOfBirth().getMonthValue()).toString());
        partnerDateOfBirthYear.sendKeys(Integer.valueOf(partner.dateOfBirth().getYear()).toString());
        partnerNino.sendKeys(partner.nino());

        dependants.sendKeys("0");
        applicationRaisedDateDay.sendKeys("3");
        applicationRaisedDateMonth.sendKeys("7");
        applicationRaisedDateYear.sendKeys("2018");

        search();
    }

    public WebElement getErrorSummaryHeader() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("validation-error-summary-heading")));
        return errorSummaryHeader;
    }

    public WebElement getPageHeading() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pageDynamicHeading")));
        return pageHeading;
    }

    public WebElement getPageHeadingContent() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("outcomeBoxSummary")));
        return pageHeadingContent;
    }

    public WebElement getResultHeading() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[ng-if=haveResult] > h2")));
        return resultHeading;
    }

    public WebElement getApplicantFullName() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("yourSearchIndividualName0")));
        return applicantFullName;
    }

    public WebElement getPartnerFullName() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("yourSearchIndividualName1")));
        return partnerFullName;
    }

    public WebElement getIncomeFromDate() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("outcomeFromDate0")));
        return incomeFromDate;
    }

    public WebElement getIncomeToDate() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("outcomeToDate0")));
        return incomeToDate;
    }

    public List<String> getEmploymentList() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[ng-repeat='e in i.employers']")));
        return employmentList.stream().map(WebElement::getText).collect(Collectors.toCollection(ArrayList::new));
    }

}

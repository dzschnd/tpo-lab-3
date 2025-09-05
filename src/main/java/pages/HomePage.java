package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.Constants;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePage extends Page {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@data-test-id='origin-input']")
    private WebElement originInput;

    @FindBy(xpath = "//input[@data-test-id='destination-input']")
    private WebElement destinationInput;

    @FindBy(xpath = "//button[@data-test-id='start-date-field']")
    private WebElement startDateButton;

    @FindBy(xpath = "//button[@data-test-id='end-date-field']")
    private WebElement endDateButton;

    @FindBy(xpath = "//div[@data-test-id='avia-footer']//label[@data-test-id='checkbox']")
    private WebElement adToggleLabel;

    @FindBy(xpath = "//div[@data-test-id='avia-footer']//input[@type='checkbox']")
    private WebElement adToggleInput;

    @FindBy(xpath = "//button[@data-test-id='form-submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@data-test-id='exact-calendar']")
    private WebElement exactCalendar;

    @FindBy(xpath = "//button[@data-test-id='profile-button']")
    private WebElement profileButton;

    @FindBy(xpath = MODAL_BASE_XPATH + "//div[@role='dialog']//button[normalize-space(.)='Save']")
    private WebElement saveLocationButton;

    @FindBy(xpath = "//button[@data-test-id='login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//button[@data-test-id='logout-button']")
    private WebElement logoutButton;

    @FindBy(xpath = MODAL_BASE_XPATH + "//div[@role='dialog']")
    private WebElement modalDialog;

    @FindBy(xpath = MODAL_BASE_XPATH
            + "//div[@role='dialog']//div[@data-test-id='login-form']//button[@data-test-id='button']")
    private WebElement moreAuthMethodsButton;

    @FindBy(xpath = MODAL_BASE_XPATH + "[last()]//div[@role='dialog']")
    private WebElement secondaryModalDialog;

    @FindBy(xpath = MODAL_BASE_XPATH + "//div[@role='dialog']//button[@data-test-id='method-button-phone-number']")
    private WebElement authPhoneNumberInputButton;

    @FindBy(xpath = MODAL_BASE_XPATH + "[last()]//div[@role='dialog']//button[@data-test-id='button']")
    private WebElement dialogSubmitButton;

    @FindBy(xpath = MODAL_BASE_XPATH
            + "[last()]//div[@role='dialog']//div[normalize-space(.)='Закрыть']/parent::button[@data-test-id='button']")
    private WebElement dialogCloseButton;

    @FindBy(xpath = MODAL_BASE_XPATH + "//div[@role='dialog']//input[@data-test-id='input']")
    private WebElement authPhoneNumberInput;

    @FindBy(xpath = MODAL_BASE_XPATH + "//div[@role='dialog']//input[@id='form_email_input']")
    private WebElement authEmailInput;

    @FindBy(xpath = "//button[@data-test-id='accept-cookies-button']")
    private WebElement acceptCookiesButton;

    public void confirmLocation() {
        wait.until(ExpectedConditions.elementToBeClickable(saveLocationButton)).click();
    }

    public void disableAds() {
        if (adToggleInput.isSelected()) {
            adToggleLabel.click();
        }
    }

    public void acceptCookies() {
        shortWait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton)).click();
    }

    public SearchPage searchFlights(String origin, String destination, LocalDate startDate, LocalDate endDate) {
        inputFlightPlaces(origin, destination);
        pickDates(startDate, endDate);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        return PageFactory.initElements(driver, SearchPage.class);
    }

    public void login(String phoneNumber) {
        if (!phoneNumber.startsWith("+79") || phoneNumber.length() != 12) {
            throw new InvalidArgumentException("Phone number must start with '+79' and include 11 digits after '+'");
        }
        wait.until(ExpectedConditions.elementToBeClickable(profileButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOf(modalDialog));

        wait.until(ExpectedConditions.elementToBeClickable(moreAuthMethodsButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(authPhoneNumberInputButton)).click();
        wait.until(ExpectedConditions.visibilityOf(secondaryModalDialog));

        wait.until(ExpectedConditions.elementToBeClickable(authPhoneNumberInput))
                .sendKeys(String.valueOf(phoneNumber).substring(3));
        wait.until(ExpectedConditions.elementToBeClickable(dialogSubmitButton)).click();

        wait.until(ExpectedConditions.visibilityOf(authEmailInput));
        new Actions(driver)
                .moveByOffset(10, 10)
                .click()
                .perform();

        wait.until(ExpectedConditions.elementToBeClickable(profileButton)).click();
        wait.until(ExpectedConditions.visibilityOf(logoutButton));
    }

    private void inputFlightPlaces(String origin, String destination) {
        wait.until(ExpectedConditions.attributeToBeNotEmpty(originInput, "value"));
        replaceInput(originInput, origin);
        // Debounce 👎
        new Actions(driver).pause(Duration.ofMillis(Constants.SHORT_WAIT_SECONDS * 1000)).perform();

        pickCitySuggestion(originInput);

        replaceInput(destinationInput, destination);
        pickCitySuggestion(destinationInput);
    }

    private void pickDates(LocalDate startDate, LocalDate endDate) {
        wait.until(ExpectedConditions.elementToBeClickable(startDateButton)).click();
        wait.until(ExpectedConditions.visibilityOf(exactCalendar));
        clickDateCell(startDate);
        clickDateCell(endDate);
    }

    private void replaceInput(WebElement input, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(input));
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.DELETE);
        input.sendKeys(value);
    }

    private void pickCitySuggestion(WebElement input) {
        String menuId = input.getAttribute("aria-controls");
        if (menuId == null || menuId.isBlank()) {
            return;
        }

        By option = By.xpath("//*[@id='" + menuId
                + "']//li[starts-with(@data-test-id,'suggested-city-') and @role='option'][1]");

        wait.until(ExpectedConditions.presenceOfElementLocated(option));

        input.sendKeys(Keys.ARROW_DOWN);
        input.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(menuId)));
    }

    private void clickDateCell(LocalDate date) {
        String day = date.format(DateTimeFormatter.ISO_LOCAL_DATE); // yyyy-MM-dd
        By buttonSelector = By.xpath("//div[@data-test-id='exact-calendar']" +
                "//td[@data-day='" + day + "']" +
                "//button[contains(concat(' ', normalize-space(@class), ' '), ' rdp-day_button ')]");

        wait.until(ExpectedConditions.elementToBeClickable(buttonSelector)).click();
    }
}

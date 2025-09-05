package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.Constants;

import java.util.Set;

public class SearchPage extends Page {
    public SearchPage(WebDriver driver) {
        super(driver);
    }

    private final String FIRST_TICKET_RESULT_XPATH = "//div[@data-test-id='search-results-items-list']//div[@data-test-id='ticket-preview'][1]";
    private final String FIRST_TICKET_RESULT_BUTTON_XPATH = FIRST_TICKET_RESULT_XPATH
            + "//button[@data-test-id='button']";
    private final String CLOSE_MODAL_BUTTON_XPATH = MODAL_BASE_XPATH + "//button[@data-test-id='round-button']";

    @FindBy(xpath = "//div[@data-test-id='search-results-items-list']")
    private WebElement searchResults;

    @FindBy(xpath = "//div[@data-test-id='search-results-items-list']//div[@data-test-id='ticket-preview'][1]//button[@data-test-id='button']")
    private WebElement firstSearchResultButton;

    @FindBy(xpath = "//div[@data-test-id='side-filters']")
    private WebElement filtersPanel;

    @FindBy(xpath = "//div[@data-test-id='filter-group-sort_side_group']/div[@role='button']")
    private WebElement sortButton;

    @FindBy(xpath = "//div[@data-test-id='single-choice-filter-sort-price_asc']")
    private WebElement sortCheapestFirstButton;

    @FindBy(xpath = "//div[@data-test-id='ticket-modal']")
    private WebElement ticketModal;

    @FindBy(xpath = "(//div[@data-test-id='ticket-modal' and @aria-hidden='false']" +
            "//div[@data-test-id='drawer-content']" +
            "//div[starts-with(@data-test-id,'proposal-') and not(.//*[contains(.,'Авиасейлс')])]" +
            "//button[starts-with(@data-test-id,'proposal-') and contains(@data-test-id,'-button')])[1]")
    private WebElement firstNonAviasalesBuyButton;

    public boolean isResultsLoaded() {
        wait.until(ExpectedConditions.visibilityOf(searchResults));
        return true;
    }

    public void pickCheapestFlight() {
        wait.until(ExpectedConditions.visibilityOf(searchResults));
        wait.until(ExpectedConditions.visibilityOf(filtersPanel));

        new Actions(driver).moveToElement(wait.until(ExpectedConditions.visibilityOf(sortButton))).perform();
        wait.until(ExpectedConditions.elementToBeClickable(sortButton)).click();
        WebElement firstElementBeforeSort = driver.findElement(By.xpath(
                FIRST_TICKET_RESULT_XPATH));
        wait.until(ExpectedConditions.elementToBeClickable(sortCheapestFirstButton)).click();
        wait.until(ExpectedConditions.stalenessOf(firstElementBeforeSort));

        closeRoundButtonModalIfPresent();
        closeRoundButtonModalIfPresent();
        closeRoundButtonModalIfPresent();
        try {
            WebElement pickButton = shortWait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(FIRST_TICKET_RESULT_BUTTON_XPATH)));
            wait.until(ExpectedConditions.elementToBeClickable(pickButton)).click();
        } catch (TimeoutException e) {
            WebElement card = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(
                            FIRST_TICKET_RESULT_XPATH)));
            card.click();
        }
        wait.until(ExpectedConditions.visibilityOf(ticketModal));

        Set<String> oldHandles = driver.getWindowHandles();
        wait.until(ExpectedConditions.elementToBeClickable(firstNonAviasalesBuyButton))
                .click();
        String newHandle = wait.until(d -> {
            Set<String> hs = d.getWindowHandles();
            hs.removeAll(oldHandles);
            return hs.isEmpty() ? null : hs.iterator().next();
        });
        driver.switchTo().window(newHandle);
        wait.until(d -> !d.getCurrentUrl().startsWith(Constants.BASE_URL));
    }

    private void closeRoundButtonModalIfPresent() {
        try {
            WebElement modalButton = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(CLOSE_MODAL_BUTTON_XPATH)));
            shortWait.until(ExpectedConditions.elementToBeClickable(modalButton)).click();
        } catch (TimeoutException ignored) {
        }
    }
}

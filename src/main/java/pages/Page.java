package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Constants;

public abstract class Page {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final WebDriverWait shortWait;
    protected final String MODAL_BASE_XPATH = "//div[@id='avs-modal-container']//div[@data-test-id='modal_base']";

    public Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_WAIT_SECONDS));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(Constants.SHORT_WAIT_SECONDS));
    }
}

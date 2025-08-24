package pages;

import org.openqa.selenium.WebDriver;

import constants.Constants;

public abstract class Page {

    protected String path;
    public WebDriver driver;

    public Page(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(Constants.BASE_URL + path);
    }
}

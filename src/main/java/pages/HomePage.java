package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class HomePage extends Page {
    public HomePage(WebDriver driver) {
        super(driver);
        this.path = "";
    }

    @FindBy(xpath = "//h1[@class='header__title']")
    private WebElement title;

    public String getTitle() {
        return title.getText();
    }
}

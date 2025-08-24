package ru.itmo.scs.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class HomePage extends Page {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        driver.get("https://www.google.com");

        var element = driver.findElement(By.name("q"));
        element.sendKeys("Selenium");
        element.submit();

        (new WebDriverWait(driver, java.time.Duration.ofSeconds(60))).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("selenium");
            }
        });

        return driver.getTitle();
    }

}

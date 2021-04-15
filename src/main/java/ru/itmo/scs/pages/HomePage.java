package ru.itmo.scs.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

/**
 * Created by i.isaev on 14.04.2021.
 */
public class HomePage extends Page {

    public HomePage(WebDriver driver) {
       super(driver);
    }

    public String getTitle() {
        driver.get("https://www.google.com");

        var element = driver.findElement(By.name("q"));
        element.sendKeys("Selenium");
        element.submit();

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("selenium");
            }
        });

        return driver.getTitle();
    }

}

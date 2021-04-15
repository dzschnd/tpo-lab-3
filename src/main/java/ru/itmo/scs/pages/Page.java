package ru.itmo.scs.pages;

import org.openqa.selenium.WebDriver;

/**
 * Created by i.isaev on 15.04.2021.
 */
public abstract class Page {

    public WebDriver driver;

    public Page(WebDriver driver) {
        this.driver = driver;
    }
}

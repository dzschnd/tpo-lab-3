package ru.itmo.scs.pages;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by i.isaev on 14.04.2021.
 */
public class HomePageTest {

    private static final Logger logger = Logger.getLogger(HomePageTest.class);

    public List<WebDriver> driverList;

    @BeforeEach
    public void setUp() {
        driverList = new ArrayList<>();

        WebDriverManager.chromedriver().setup();
        driverList.add(new ChromeDriver());

        WebDriverManager.firefoxdriver().setup();
        driverList.add(new FirefoxDriver());
    }

    @BeforeEach
    public void tearDown() {
        // driverList.forEach(WebDriver::quit);
    }

    @Test
    public void getTitleTest() {
        driverList.forEach(webDriver -> {
            assertEquals("Selenium - Поиск в Google", PageFactory.initElements(webDriver, HomePage.class)
                    .getTitle());
            webDriver.quit();
        });
    }
}

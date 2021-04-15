package ru.itmo.scs.pages;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import ru.itmo.scs.*;
import ru.itmo.scs.exceptions.InvalidPropertiesException;
import ru.itmo.scs.utils.Properties;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by i.isaev on 14.04.2021.
 */
public class HomePageTest {

    private static final Logger logger = Logger.getLogger(HomePageTest.class);

    public Context context;
    public List<WebDriver> driverList;

    @BeforeEach
    public void setUp() {
        context = new Context();
        driverList = new ArrayList<>();
        try {
            Properties.getInstance().reading(context);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        }

        if (context.getGeckodriver() != null) {
            System.setProperty(Constants.WEBDRIVER_FIREFOX_DRIVER, context.getGeckodriver());
            driverList.add(new FirefoxDriver());
        }
        if (context.getChromedriver() != null) {
            System.setProperty(Constants.WEBDRIVER_CHROME_DRIVER, context.getChromedriver());
            driverList.add(new ChromeDriver());
        }
        if (driverList.isEmpty()) throw new InvalidPropertiesException();
    }

    @BeforeEach
    public void tearDown() {
//        driverList.forEach(WebDriver::quit);
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

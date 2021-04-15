package ru.itmo.scs;

/**
 * Created by i.isaev on 14.04.2021.
 */
public class Context {
    private String chromedriver;
    private String geckodriver;

    public String getChromedriver() {
        return chromedriver;
    }

    public void setChromedriver(String chromedriver) {
        this.chromedriver = chromedriver;
    }

    public String getGeckodriver() {
        return geckodriver;
    }

    public void setGeckodriver(String geckodriver) {
        this.geckodriver = geckodriver;
    }
}

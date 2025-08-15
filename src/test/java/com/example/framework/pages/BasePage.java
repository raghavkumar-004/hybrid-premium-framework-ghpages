package com.example.framework.pages;

import com.example.framework.drivers.DriverFactory;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    protected final WebDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
    }

    public void open(String absoluteUrl) {
        driver.get(absoluteUrl);
    }
}

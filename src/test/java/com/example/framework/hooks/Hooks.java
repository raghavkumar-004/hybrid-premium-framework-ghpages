package com.example.framework.hooks;

import com.example.framework.drivers.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class Hooks {

    @After
    public void afterEach(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                WebDriver d = DriverFactory.peek();
                if (d != null) {
                    byte[] shot = ((TakesScreenshot) d).getScreenshotAs(OutputType.BYTES);
                    Allure.addAttachment("Failure screenshot", new ByteArrayInputStream(shot));
                }
            } catch (Exception ignored) {}
        }
        DriverFactory.quit();
    }
}

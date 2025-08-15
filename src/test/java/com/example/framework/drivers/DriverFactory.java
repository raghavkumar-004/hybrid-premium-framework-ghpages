package com.example.framework.drivers;

import com.example.framework.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();

    public static WebDriver getDriver() {
        WebDriver d = TL.get();
        if (d == null) {
            String browser = System.getProperty("browser", ConfigReader.get("browser", "chrome"));
            boolean headless = Boolean.parseBoolean(System.getProperty("headless", ConfigReader.get("headless", "true")));
            switch (browser.toLowerCase()) {
                case "firefox" -> {
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions fo = new FirefoxOptions();
                    if (headless) fo.addArguments("-headless");
                    d = new FirefoxDriver(fo);
                }
                default -> {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions co = new ChromeOptions();
                    if (headless) co.addArguments("--headless=new");
                    co.addArguments("--no-sandbox","--disable-gpu","--window-size=1920,1080");
                    d = new ChromeDriver(co);
                }
            }
            TL.set(d);
        }
        return d;
    }

    public static WebDriver peek() { return TL.get(); }

    public static void quit() {
        WebDriver d = TL.get();
        if (d != null) {
            d.quit();
            TL.remove();
        }
    }
}

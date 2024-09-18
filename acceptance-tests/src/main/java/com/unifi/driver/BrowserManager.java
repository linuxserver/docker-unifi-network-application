package com.unifi.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class BrowserManager {
    ChromeOptions options;
    WebDriver driver;

    public WebDriver create() {
        options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors", "--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        return driver;
    }
    
    public WebDriver getDriver() {
        if (driver == null) {
            return create();
        }
        return driver;
    }
}

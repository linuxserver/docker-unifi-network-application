package com.unifi.driver;

import org.openqa.selenium.WebDriver;

public class DriverProvider {

    private static ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    private DriverProvider() {
    }

    public static WebDriver getDriverInstance() {
        if (drivers.get() == null) {
            createNewDriver();
        }
        return drivers.get();
    }

    public static void closeDriver() {
        if (drivers.get() != null) {
            drivers.get().quit();
            drivers.remove();
        }
    }

    private static void createNewDriver() {
        WebDriver driver = new BrowserManager().create();
        drivers.set(driver);
    }
}

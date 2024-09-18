package com.unifi.driver.pages;

import com.unifi.driver.DriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class BasePage {

    WebDriver driver = DriverProvider.getDriverInstance();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(30000));

    public void clickElement(By by) {
        System.out.println("Clicking on element " + by.toString());
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }
    
    public void focusAndClickElement(By by) {
        System.out.println("Focusing the element " + by.toString());
        new Actions(driver).moveToElement(wait.until(ExpectedConditions.elementToBeClickable(by))).click().perform();
    }

    public void sendKeysToElement(String text, By by) {
        System.out.println("Sending keys onto element " + by.toString());
        wait.until(ExpectedConditions.elementToBeClickable(by)).sendKeys(text);
    }
    
    public String getTextFromElement(By by) {
        return driver.findElement(by).getText();
    }
}

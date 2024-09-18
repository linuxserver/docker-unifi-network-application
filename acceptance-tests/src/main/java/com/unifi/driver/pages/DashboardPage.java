package com.unifi.driver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DashboardPage extends BasePage{
    private static By dashboardButton = By.xpath("//a[@class='component__rHEvxowz icon-light__rHEvxowz active__rHEvxowz']");
    private static By topologyButton = By.xpath("//a[@class='component__rHEvxowz icon-light__rHEvxowz active__rHEvxowz']");
    private static By textFromActibvityTab = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[4]/div[1]/span[1]");
    private static By settingsTab = By.xpath("//a[@data-testid='navigation-settings']");
    private static By systemTab = By.xpath("//span[@data-testid='system']");
    private static By countryField = By.xpath("//div[@class='inputContainer__xvBRiNo6 inputContainer-light-secondary__xvBRiNo6 inputContainer-secondary__xvBRiNo6']");
    
    public void makeSomeActivitiesForAdminChecking(){
       clickElement(topologyButton);
       clickElement(dashboardButton);
       driver.navigate().refresh();
       wait.until(ExpectedConditions.visibilityOfElementLocated(textFromActibvityTab));
   }

    public static By getTextFromActibvityTab() {
        return textFromActibvityTab;
    }
    
    public String checkCountryCode() {
        clickElement(settingsTab);
        clickElement(systemTab);
        return getTextFromElement(countryField);
    }
}

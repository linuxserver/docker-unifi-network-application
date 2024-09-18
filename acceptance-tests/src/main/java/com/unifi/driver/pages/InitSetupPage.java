package com.unifi.driver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InitSetupPage extends BasePage{
    private static By termsCheckBox = By.xpath("//input[@id='tosAndEula']");
    private static By nextButton = By.cssSelector("button[type='submit'] span[class='content__jTNy2Cxe']");
    private static By skipButton = By.xpath("//span[contains(text(),'Skip')]");
    private static By createUIAccountButton = By.cssSelector("button[class='button__jTNy2Cxe button-light__jTNy2Cxe secondary__jTNy2Cxe secondary-light__jTNy2Cxe is-accessible__jTNy2Cxe is-accessible-light__jTNy2Cxe medium__jTNy2Cxe'] span[class='content__jTNy2Cxe'] span");
    private static By advancedSetupLink = By.xpath("//span[contains(text(),'Advanced Setup')]");
    private static String countryCodeText;

    private static By countryCode = By.xpath("//*[@id=\"root\"]/div[1]/div[3]/div/form/div/div[2]/div[2]");


    public static String getCountryCodeText() {
        return countryCodeText;
    }

    public static By getTermsCheckBox() {
        return termsCheckBox;
    }

    public static By getNextButton() {
        return nextButton;
    }


    public static By getCreateUIAccountButton() {
        return createUIAccountButton;
    }
    
    public void prepareForSetupAccount(){
        countryCodeText = wait.until(ExpectedConditions.presenceOfElementLocated(countryCode)).getText();
        clickElement(termsCheckBox);
        clickElement(nextButton);
        clickElement(advancedSetupLink);
        clickElement(skipButton);
    }
}

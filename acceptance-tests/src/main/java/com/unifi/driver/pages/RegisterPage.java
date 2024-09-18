package com.unifi.driver.pages;

import org.openqa.selenium.By;

public class RegisterPage extends BasePage{
    private String userName;
    private String email;
    private String password;
    
    private static By userNameField = By.xpath("//input[@id='localAdminUsername']");
    private static By userEmailField = By.xpath("//input[@id='localAdminEmail']");
    private static By userPasswordField = By.xpath("//input[@id='localAdminPassword']");
    private static By userConfirmPasswordField = By.xpath("//input[@id='localAdminPasswordConfirm']");
    
    private static By finishButton = By.xpath("//span[normalize-space()='Finish']");



    public RegisterPage(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    
    public void fillFormsAndFinish () {
        sendKeysToElement(this.userName, userNameField);
        sendKeysToElement(this.email, userEmailField);
        sendKeysToElement(this.password, userPasswordField);
        sendKeysToElement(this.password, userConfirmPasswordField);
        clickElement(finishButton);
    }
}

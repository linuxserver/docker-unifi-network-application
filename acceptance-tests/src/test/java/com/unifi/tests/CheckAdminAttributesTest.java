package com.unifi.tests;

import com.unifi.driver.pages.DashboardPage;
import com.unifi.driver.pages.InitSetupPage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class CheckAdminAttributesTest extends BaseTest{
    
    private DashboardPage dashboardPage;
    String expectedAdminActivities = "admin opened UniFi Network via the web.";
    private SoftAssert softAssert = new SoftAssert();

    @Test(priority = 1)
    public void isAdminNameIsPresentInActivityTabAndCountryCodeIsTheSame(){
        System.out.println("Starting isAdminNameIsPresentInActivityTabAndCountryCodeIsTheSame test");
        dashboardPage = new DashboardPage();
        dashboardPage.makeSomeActivitiesForAdminChecking();
        String actualActivityText = dashboardPage.getTextFromElement(DashboardPage.getTextFromActibvityTab());
        softAssert.assertTrue(actualActivityText.equals(expectedAdminActivities));
        String actualCountryCode = dashboardPage.checkCountryCode();
        softAssert.assertTrue(InitSetupPage.getCountryCodeText().contains(actualCountryCode));
        
    }
}

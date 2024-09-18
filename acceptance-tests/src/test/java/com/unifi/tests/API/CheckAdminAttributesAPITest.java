package com.unifi.tests.API;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class CheckAdminAttributesAPITest extends BaseAPITest {

    Response response;
    private String username = "admin";
    private String password = "password";
    
    
    @Test(priority = 2)
    public void checkAdminName(){
        System.out.println("Starting checkAdminName test");
        initLogin(username, password);
        response = makeGetRequestAndReturnResponse("/self");
        String actualName = response.jsonPath().getString("data[0].name");
        Assert.assertEquals(actualName, "admin");
    }
    
    @Test(priority = 3)
    public void checkCountryCode() {
        System.out.println("Starting checkCountryCode test");
        initLogin(username, password);
        response = makeGetRequestAndReturnResponse("/s/default/get/setting/country");
        String expectedCountryCode = "840";
        String actualCountryCode = response.jsonPath().getString("data[0].code");
        Assert.assertEquals(actualCountryCode, expectedCountryCode);
    }
}

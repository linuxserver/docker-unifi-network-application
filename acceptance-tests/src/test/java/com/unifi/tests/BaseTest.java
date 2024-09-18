package com.unifi.tests;

import com.unifi.driver.DriverProvider;
import com.unifi.driver.pages.InitSetupPage;
import com.unifi.driver.pages.RegisterPage;

import com.unifi.driver.utils.DockerContainerManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {
    protected WebDriver driver;
    protected InitSetupPage initSetupPage;
    protected RegisterPage registerPage;
    private String name = "admin";
    private String email = "network-admin@gmail.com";
    private String password = "password";
    private String URL = "https://127.0.0.1:8443/";
    private DockerContainerManager dockerContainerManager = new DockerContainerManager();


    @BeforeClass
    public void setup() {
        dockerContainerManager.editSystemProperties();
        dockerContainerManager.restartContainer(dockerContainerManager.getContainerName());
        //wait until application starting ... 
        //TODO: check docker logs instead of sleeping 
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.driver = DriverProvider.getDriverInstance();
        driver.get(URL);
        initSetupPage = new InitSetupPage();
        initSetupPage.prepareForSetupAccount();
        registerPage = new RegisterPage(name, email, password);
        registerPage.fillFormsAndFinish();
    }
    
    @AfterClass
    public void close(){
        driver.quit();
    }
    
   
    

}

package com.unifi.tests.API;


import com.unifi.driver.utils.DockerContainerManager;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;


import static io.restassured.RestAssured.given;

public class BaseAPITest {
    private String username = "admin";
    private String password = "password";
    private DockerContainerManager dockerContainerManager = new DockerContainerManager();
    private Cookies cookies;


    public void initRestAssured () {
        RestAssured.baseURI = "https://127.0.0.1";
        RestAssured.port = 8443;
        RestAssured.basePath = "/api";
    }



    @BeforeClass
    public void setup(){
        initRestAssured();
        dockerContainerManager.editSystemProperties();
        dockerContainerManager.restartContainer(dockerContainerManager.getContainerName());
        //wait until application starting ... 
        //TODO: check docker logs instead of sleeping 
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        createNewUser();
    }
    public Response makeGetRequestAndReturnResponse (String path) {
        return given()
                .relaxedHTTPSValidation()
                .cookie(String.valueOf(cookies))
                .header("Content-Type","application/json").header("connection","keep-alive").header("Accept-Encoding","gzp,deflate,br")
                .when()
                .get(path)
                .then()
                .extract().response();

    }

    public void initLogin(String username, String password) {
        String initAdminLoginPayload = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"remember\":false,\"strict\":true}";
        Response response = given()
                .relaxedHTTPSValidation()
                .body(initAdminLoginPayload)
                .when()
                .post("/login")
                .then()
                .extract().response();
        cookies = response.getDetailedCookies();
        Assert.assertEquals(response.statusCode(), 200);
    }

    public void makePostRequest(String body, String path){
        Response response = given()
                .cookie(String.valueOf(cookies))
                .relaxedHTTPSValidation()
                .body(body)
                .when()
                .post(path)
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), 200);
    }

    public void createNewUser(){
        String newUserBody = "{\"cmd\": \"add-default-admin\", \"name\": \"admin\", \"email\": \"network-admin@gmail.com\", \"x_password\": \"password\"}";
        makePostRequest(newUserBody,"/cmd/sitemgr");
        String applicationName = "{\"name\": \"UniFi Network\"}";
        makePostRequest(applicationName, "/set/setting/super_identity");
        String countryCode = "{\"code\": \"840\"}";
        makePostRequest(countryCode, "/set/setting/country");
        String timeZone = "{\"timezone\": \"Europe/Riga\"}";
        makePostRequest(timeZone, "/set/setting/locale");
        String configuredState = "{\"cmd\": \"set-installed\"}";
        makePostRequest(configuredState, "/cmd/system");
    }
}

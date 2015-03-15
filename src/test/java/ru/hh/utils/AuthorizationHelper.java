package ru.hh.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.data.HHUser;
import ru.hh.data.Token;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertThat;

/**
 * Created by HonneyHelen on 15-Mar-15.
 */
public class AuthorizationHelper {

    private final static String clientID = "RCSJM8GMNO4R0PR51QP398CRFM67V3NNUR3115A8TMENOSBLN2JQDSA0S7CGI883";
    private final static String clientSecret = "PNM5F54P848SO1EJ2BDTI1AME1S512PE4AK7FSDAUOTOS654JD1P4I3TRHFMCRD6";
    private static final Logger log = LoggerFactory.getLogger(AuthorizationHelper.class);

    private WebDriver driver;
    private HHUser data;


    public AuthorizationHelper() {

        this.data = new HHUser();
        prepareTestData();
        driver = new HtmlUnitDriver();
        driver.get("https://m.hh.ru/oauth/authorize?response_type=code&client_id=" + clientID);

    }

    private  void prepareTestData() {
        log.info("Passing authorization");
        data = new HHUser();
        data.setEmail("sharovahelene@gmail.com");
        data.setPassword("41556422");
        data.setFirst_name("Helen");
        data.setLast_name("Sharova");
        data.setIs_in_search(true);
        data.setIs_applicant(true);

    }

    public Token getAccessToken() throws IOException {
        String authCode = getAuthorizationCode();
        String uri = String.format("https://m.hh.ru/oauth/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s",
                clientID, clientSecret, authCode);
        final HttpUriRequest request = new HttpPost(uri);
        final HttpResponse response = HttpClientBuilder.create().build().execute(request);
        final Token token = RetrieveUtil.retrieveResourceFromResponse(response, Token.class);
        log.info("Token: {}", token);
        return token;

    }

    private String getAuthorizationCode() {
        String currentURL = driver.getCurrentUrl();
        if (currentURL.contains("account/login")) {
            enterCredentials();
            return cutCodeFromURL(driver.getCurrentUrl());
        } else {
            return cutCodeFromURL(currentURL);
        }
    }

    private void enterCredentials() {
        WebElement login = driver.findElement(By.name("username"));
        login.sendKeys(data.getEmail());

        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(data.getPassword());

        WebElement submit = driver.findElement(By.xpath("//input[@data-qa='account-login-submit']"));
        submit.click();
        new WebDriverWait(driver, 180).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.findElement(By.xpath("//h2[text()='Мои события']")).isDisplayed();
            }

        });

    }

    private String cutCodeFromURL(String url) {
        try{
            return url.split("code=")[1];
        }catch(Exception ex){
            return null;
        }
    }

    public HHUser getData() {
        return data;
    }

    public void setData(HHUser data) {
        this.data = data;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.instagramcredentialvalidation;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author stephen
 */
public class LoginValidation {
    PhantomJSDriver driver;  //Ghostdriver
    private String username  = "";
    private String password  = "";
    private String ip        = "";
    private String port      = "";
    private String proxyUser = "";
    private String proxyPass = "";
    private boolean result = false;
    
    public LoginValidation(String parameters){
        SetParameters(parameters);
        loadLightWeightDriverCustom();
        login();
        driver.quit();
    }
    private void loadLightWeightDriverCustom() {
         File PHANTOMJS_EXE = new File("//home/innwadmin/phantomjs/bin/phantomjs");  // Linux File
        // File PHANTOMJS_EXE = new File("/Users/stephen.hyde/repositories/phantomjs-2.1.1-macosx/bin/phantomjs");
        // File PHANTOMJS_EXE = new File("C:/Users/stephen/Documents/Instanetwork/Instagram AutoLike/InstagramAutoLike/phantomjs-2.0.0-windows/bin/phantomjs.exe"); // Windows File

        ArrayList<String> cliArgsCap = new ArrayList();
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("phantomjs.binary.path",
                PHANTOMJS_EXE.getAbsolutePath());
        caps.setJavascriptEnabled(true);
        cliArgsCap.add("--proxy=" + ip + ":" + port); 
        if (!proxyUser.equalsIgnoreCase("none")) {
           cliArgsCap.add("--proxy-auth=" + proxyUser + ":" + proxyPass);
        }
        cliArgsCap.add("--max-disk-cache-size=0");
        cliArgsCap.add("--disk-cache=false");
        cliArgsCap.add("--webdriver-loglevel=NONE");
        caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        driver = new PhantomJSDriver(caps);
        driver.manage().window().maximize();
    }
    private void login(){
        driver.get("https://www.instagram.com/accounts/login/");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);      
        List<WebElement> user = driver.findElements(By.xpath("//input[@name='username']"));
        List<WebElement> pass = driver.findElements(By.xpath("//input[@name='password']"));
        List<WebElement> login = driver.findElements(By.xpath("//span[1]/button[contains(@class, '_ah57t')]"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if(user.size() > 0 && pass.size() > 0 && login.size() > 0){
            user.get(0).sendKeys(username);
            pass.get(0).sendKeys(password);
            sleepExtraPageLoad();
            login.get(0).click();
            sleepExtraPageLoad();
        }
        else{
            result = false;
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> name = driver.findElements(By.xpath("//a[contains(@class, '_soakw')]"));
        result = name.size() > 0;      
    }
    private void SetParameters(String param) {
        int count = StringUtils.countMatches(param, ",");
        if(count == 5){
            String[] array = param.split(",");
            username  = array[0];
            password  = array[1];
            System.out.println("USERNAME " + username);
            System.out.println("PASSWORD " + password);
            ip        = array[2];
            port      = array[3];
            proxyUser = array[4];
            proxyPass = array[5];
        }
        else{          
           result = false;
        }
    }
    public boolean GetResult(){
        return result;
    }
    
    private void sleepExtraPageLoad() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception on sleepDuringWebstaLogin");
        }
    }
}

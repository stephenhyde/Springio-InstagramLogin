/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.instagramcredentialvalidation;

import java.io.File;
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
       // File PHANTOMJS_EXE = new File("C:/Users/stephen/Documents/Instanetwork/Instagram AutoLike/InstagramAutoLike/phantomjs-2.0.0-windows/bin/phantomjs.exe"); // Windows File

        ArrayList<String> cliArgsCap = new ArrayList();
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("phantomjs.binary.path",
                PHANTOMJS_EXE.getAbsolutePath());
        caps.setJavascriptEnabled(true);
           cliArgsCap.add("--proxy=" + ip + ":" + port); //8080 for tinyproxy
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
        List<WebElement> login = driver.findElements(By.xpath("//button[@class='_rz1lq _k2yal _84y62 _7xso1 _nv5lf']"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if(user.size() > 0 && pass.size() > 0 && login.size() > 0){
            user.get(0).sendKeys(username);
            pass.get(0).sendKeys(password);
            login.get(0).click();
        }
        else{
            result = false;
        }
        List<WebElement> name = driver.findElements(By.xpath("//a[@class='_6ssv5']"));
        result = name.size() > 0;      
    }
    private void SetParameters(String param) {
        int count = StringUtils.countMatches(param, ",");
        if(count == 5){
            String[] array = param.split(",");
            username  = array[0];
            password  = array[1];
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
}

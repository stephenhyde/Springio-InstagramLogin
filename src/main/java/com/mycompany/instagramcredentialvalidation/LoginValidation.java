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
    private boolean result = false;
    private Profile profile;
    
    public LoginValidation(Profile p){
        profile = p;
        System.out.println("Username " + profile.getUsername());
        System.out.println("Password " + profile.getPassword());
        System.out.println("Ip " + profile.getIp());
        System.out.println("Port " + profile.getPort());
        System.out.println("ProxyUsername " + profile.getProxyUsername());
        System.out.println("ProxyPassword " + profile.getProxyPassword());
        
        if (profile.getUsername() == null || profile.getPassword() == null || profile.getIp() == null || profile.getPort() == null || profile.getProxyUsername() == null || profile.getProxyPassword() == null) {
            System.out.println("Result1 " + result);
            return;
        }
        loadLightWeightDriverCustom();
        login();
        System.out.println("Result " + result);
        driver.quit();
    }
    
    private void loadLightWeightDriverCustom() {
        // File PHANTOMJS_EXE = new File("//home/innwadmin/phantomjs/bin/phantomjs");  // Linux File
         File PHANTOMJS_EXE = new File("/Users/stephen.hyde/repositories/phantomjs-2.1.1-macosx/bin/phantomjs");
        // File PHANTOMJS_EXE = new File("C:/Users/stephen/Documents/Instanetwork/Instagram AutoLike/InstagramAutoLike/phantomjs-2.0.0-windows/bin/phantomjs.exe"); // Windows File

        ArrayList<String> cliArgsCap = new ArrayList();
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("phantomjs.binary.path",
                PHANTOMJS_EXE.getAbsolutePath());
        caps.setJavascriptEnabled(true);
        cliArgsCap.add("--proxy=" + profile.getIp() + ":" + profile.getPort()); 
        if (!profile.getProxyUsername().equalsIgnoreCase("none")) {
           cliArgsCap.add("--proxy-auth=" + profile.getProxyUsername() + ":" + profile.getProxyPassword());
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
            user.get(0).sendKeys(profile.getUsername());
            pass.get(0).sendKeys(profile.getPassword());
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

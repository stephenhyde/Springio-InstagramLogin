/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.instagramcredentialvalidation;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 *
 * @author stephen
 */
public class LoginValidation {
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
        login();
        System.out.println("Result " + result);
    }
   
    private void login(){
        String s;
        String scriptResult = null;
        String location = "/home/innwadmin/python-instagram-login/startloginscript.py";
//        String location = "/Users/stephen.hyde/repositories/python-instagram-login/startloginscript.py";
        String proxy = profile.getIp() + ":" + profile.getPort();
        if (!profile.getProxyUsername().equalsIgnoreCase("none")) {
            proxy = profile.getProxyUsername() + ":" + profile.getPassword() + "@" + proxy;
        }
        try {
            Process process = Runtime.getRuntime().exec("python  " + location + " " + profile.getUsername() + " " + profile.getPassword() + " " + proxy);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            // read the output from the command
            System.out.println("Output of the script:\n");
            while ((s = stdInput.readLine()) != null) {
                scriptResult = s;
                System.out.println(s);
            }
        } catch(IOException e) {
            result = false;
            return;
        }
        System.out.println(scriptResult);
        if (scriptResult != null && scriptResult.equalsIgnoreCase("False")) {
            result = false;
        } else {
            result = true;
        }
    }

    public boolean GetResult(){
        return result;
    }
}

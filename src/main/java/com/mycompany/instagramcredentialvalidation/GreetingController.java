/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.instagramcredentialvalidation;

/**
 *
 * @author stephen
 */
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/validate")
    public profileProperties validate(@RequestParam(value="properties", defaultValue="") String properties) {
        LoginValidation login = new LoginValidation(properties);
        return new profileProperties(counter.incrementAndGet(), login.GetResult());
    }
}
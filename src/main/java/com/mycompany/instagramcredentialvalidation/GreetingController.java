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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/validate")
@CrossOrigin
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method = RequestMethod.POST)
    public profileProperties validate(@RequestBody Profile profile) {
        LoginValidation login = new LoginValidation(profile);
        return new profileProperties(counter.incrementAndGet(), login.GetResult());
    }
}

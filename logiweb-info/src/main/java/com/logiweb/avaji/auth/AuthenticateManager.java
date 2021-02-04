package com.logiweb.avaji.auth;

import com.logiweb.avaji.auth.jms.AuthenticateConsumer;
import com.logiweb.avaji.auth.jms.AuthenticateSender;
import com.logiweb.avaji.auth.jms.AuthenticatedUser;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model
public class AuthenticateManager {

    private List<AuthenticatedUser> authenticatedUsers;

    @Inject
    private AuthenticateSender sender;

    @Inject
    private AuthenticateConsumer consumer;


    @PostConstruct
    private void init() {
        sender.send();
        authenticatedUsers = consumer.getUsers();
    }

    public boolean isAuthenticated(String email, String password) {
        long result = authenticatedUsers.stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password)).count();
        return result != 0;
    }

}

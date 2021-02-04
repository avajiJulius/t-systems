package com.logiweb.avaji.auth;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {
    private String email;
    private String password;
    private boolean authenticate;

    @EJB
    private AuthenticateManager authenticateManager;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuthenticate() {
        return authenticate;
    }

    public void login() {
        this.authenticate = authenticateManager.isAuthenticated(email, password);
    }
}

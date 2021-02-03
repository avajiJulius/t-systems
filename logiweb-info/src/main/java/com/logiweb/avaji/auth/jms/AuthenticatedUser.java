package com.logiweb.avaji.auth.jms;

public class AuthenticatedUser {

    private String email;
    private String password;

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

    public static class Builder {

        private AuthenticatedUser newUser;

        public Builder() {
            newUser = new AuthenticatedUser();
        }

        public Builder withEmail(String email) {
            newUser.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            newUser.password = password;
            return this;
        }

        public AuthenticatedUser build() {
            return newUser;
        }
    }
}

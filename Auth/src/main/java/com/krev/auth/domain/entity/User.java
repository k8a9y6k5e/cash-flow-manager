package com.krev.auth.domain.entity;

import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;

    public User(Long id, String username, String password, String email) {
        try{
            this.validate(username, password, email);

            this.id = id;
            this.username = username;
            this.password = password;
            this.email = email;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public User(String username, String password, String email) {
        try{
            this.validate(username, password, email);

            this.username = username;
            this.password = password;
            this.email = email;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private void validate(String username, String password, String email) throws Exception {
        if(username == null || username.isBlank()) {
            throw new Exception("Username can't be null or empty");
        }

        if(password == null || password.isBlank()) {
            throw new Exception("Password can't be null or empty");
        }

        if(email == null || email.isBlank()) {
            throw new Exception("Email can't be null or empty");
        }
    }
}

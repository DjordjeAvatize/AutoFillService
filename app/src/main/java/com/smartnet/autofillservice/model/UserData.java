package com.smartnet.autofillservice.model;

public class UserData {


    public String username;
    public String password;
    private static UserData instance;


    public static UserData getInstance() {
        if(instance == null){
            instance = new UserData();
        }
        instance.username = "Djole";
        instance.password = "123456";
        return instance;
    }
}

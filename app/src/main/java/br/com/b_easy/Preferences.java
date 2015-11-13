package br.com.b_easy;

import br.com.b_easy.Client.User;
import br.com.b_easy.DataBaseModel.UserBD;

/**
 * Created by Tiago on 11/10/2015.
 */
public class Preferences {



    //SERVER CONFIG
    //public static final String SERVER_URL = "http://192.168.1.105:8080/SocialMenuServer/rest/";
    public static final String SERVER_URL = "http://10.0.3.2:8080/SocialMenuServer/rest/";
    //public static final String SERVER_URL = "http://104.131.62.76:8080/SocialMenu/rest/";
    public static final String USER_TOMCAT_AUTH = "admin";
    public static final String PASSWORD_TOMCAT_AUTH = "admin";
    public static final String PASSWORD_BASE = "b_easy";

    private static Preferences mInstance;
    private UserBD user;

    private Preferences(){
    }

    public static Preferences getInstance(){
        if(mInstance == null)
            mInstance = new Preferences();

        return mInstance;
    }

    public void setUser(UserBD usuario){
        this.user = usuario;
    }

    public UserBD getUser(){
        return this.user;
    }

}
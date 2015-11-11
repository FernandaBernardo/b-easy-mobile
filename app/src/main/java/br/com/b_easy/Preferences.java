package br.com.b_easy;

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
    public static final String PASSWORD_BASE = "social_menu";

    private static Preferences mInstance;
    //private Usuario usuario;

    private Preferences(){
    }

    public static Preferences getInstance(){
        if(mInstance == null)
            mInstance = new Preferences();

        return mInstance;
    }

//    public void setUsuario(Usuario usuario){
//        this.usuario = usuario;
//    }
//
//    public Usuario getUsuario(){
//        return this.usuario;
//    }

}
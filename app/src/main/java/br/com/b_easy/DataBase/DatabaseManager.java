package br.com.b_easy.DataBase;

import android.content.Context;
import android.provider.ContactsContract;

import br.com.b_easy.Application.ApplicationClass;

/**
 * Created by Tiago on 9/24/2015.
 */
public class DatabaseManager {


    static private DatabaseManager instance;
    static private DatabaseHelper helper;

    public DatabaseManager(){
        this.helper = new DatabaseHelper(ApplicationClass.getInstance());
    }

    public static DatabaseHelper getHelper() {
        if(instance == null)
            instance = new DatabaseManager();
        return  helper;
    }

}

package br.com.b_easy.DataBase;

import android.content.Context;
import android.provider.ContactsContract;

/**
 * Created by Tiago on 9/24/2015.
 */
public class DatabaseManager {


    static private DatabaseManager instance;
    static private DatabaseHelper helper;

    public DatabaseManager(Context ctx){
        this.helper = new DatabaseHelper(ctx);
    }

    static public void init(Context ctx){
        if(instance == null){
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance(){
        return  instance;
    }

    public static DatabaseHelper getHelper() {
        return helper;
    }

}

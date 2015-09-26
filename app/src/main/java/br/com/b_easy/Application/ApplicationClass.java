package br.com.b_easy.Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Tiago on 9/26/2015.
 */
public class ApplicationClass extends Application {

    private static ApplicationClass mInstance;

    public void onCreate(){
        super.onCreate();
        mInstance = this;
    }

    public static ApplicationClass getInstance(){
        return mInstance;
    }

    public static Context getContext(){
        return mInstance.getApplicationContext();
    }


}

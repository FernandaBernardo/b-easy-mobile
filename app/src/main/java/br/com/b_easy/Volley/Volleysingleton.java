package br.com.b_easy.Volley;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import br.com.b_easy.Application.ApplicationClass;

/**
 * Created by Tiago on 9/30/2015.
 */
public class VolleySingleton {
    private static VolleySingleton sInstance = null;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;

    private VolleySingleton(){
        mRequestQueue = Volley.newRequestQueue(ApplicationClass.getContext());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageCache());
    }

    public static VolleySingleton getInstance(){
        if(sInstance == null){
            sInstance = new VolleySingleton();
        }

        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

    public ImageLoader getmImageLoader(){
        return mImageLoader;
    }


}

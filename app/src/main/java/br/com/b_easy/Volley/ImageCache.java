package br.com.b_easy.Volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Tiago on 9/30/2015.
 */
public class ImageCache implements ImageLoader.ImageCache {

    int sizeCache = (int) Runtime.getRuntime().maxMemory()/1024/8;

    private LruCache<String, Bitmap> cache = new LruCache<>(sizeCache);

    @Override
    public Bitmap getBitmap(String url) {
        return cache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
    }
}

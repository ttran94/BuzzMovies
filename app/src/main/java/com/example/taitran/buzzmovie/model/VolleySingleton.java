package com.example.taitran.buzzmovie.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


/**
 * Created by taitr on 2/27/2016.
 */
public class VolleySingleton {

    private RequestQueue requestQueue;
    private ImageLoader image;
    private static VolleySingleton instance;

    /**
     * create a queue and handle the background thread
     * this is useful when loading a large amount of data from server
     * @param context get the application context
     */
    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context);

        image = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>((4 * 1024 * 1024));

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    /**
     *
     * @param context get the application context
     * @return VolleySingleton
     */
    public static VolleySingleton getInstance(Context context) {
       if (instance == null) {
           instance = new VolleySingleton(context);
       }
        return instance;
    }

    /**
     *
     * @return an ImageLoader object that contains the bitmap of
     * the image Url
     */
    public ImageLoader getImage() {
        return image;
    }

    /**
     *
     * @return a RequestQueue object that will be use to
     * make a request to the server
     */
    public RequestQueue getRequest() {
        return requestQueue;
    }



}

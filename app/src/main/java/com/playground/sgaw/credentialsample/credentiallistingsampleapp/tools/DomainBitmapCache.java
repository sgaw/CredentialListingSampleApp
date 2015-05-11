package com.playground.sgaw.credentialsample.credentiallistingsampleapp.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Implements LRU cache for storing domain icons.
 */
public class DomainBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    private final static String TAG = "LruBitmapCache";
    private final static int NUM_SCREENS = 3;
    // Where to reference this number? https://developer.android.com/training/volley/request.html
    private final static int BYTES_PER_PIXEL = 4;


    /**
     * Compute a max cache size given the approximate capacity of the display.
     *
     * See {@url https://developer.android.com/training/volley/request.html}.
     *
     * @param context
     * @return
     */
    private static int cacheSizeFromDisplay(Context context) {
        final DisplayMetrics displayMetrics = context.getResources().
                getDisplayMetrics();
        final int screenPixels = displayMetrics.heightPixels * displayMetrics.widthPixels;
        return NUM_SCREENS * screenPixels * BYTES_PER_PIXEL;
    }

    public DomainBitmapCache(int maxSize) {
        super(maxSize);
    }

    /**
     * Create a bitmap cache that defaults to hold several screen's worth of images.
     * @param context
     */
    public DomainBitmapCache(Context context) {
        this(cacheSizeFromDisplay(context));
    }


    @Override
    public Bitmap getBitmap(String url) {
        Log.i(TAG, String.format("getBitmap(%s)", url));
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}

package com.playground.sgaw.credentialsample.credentiallistingsampleapp.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Implements LRU cache for storing domain icons.
 */
public class DomainBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    private final static String TAG = "LruBitmapCache";

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public DomainBitmapCache(Context context, int maxSize) {
        super(maxSize);
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

package com.playground.sgaw.credentialsample.credentiallistingsampleapp.tools;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Client for handling downloading small files over the Internet.
 */
public class ContentFetcher {
    private static final String CORPUS_URL =
            "https://dl.dropboxusercontent.com/u/2532281/listing.txt";
    private static String TAG = "ContentFetcher";
    private static ContentFetcher sContentFetcher = null;

    private RequestQueue mRequestQueue = null;
    private ImageLoader mImageLoader = null;

    /**
     * Getter for content fetcher client.
     * @param context
     * @return singleton ContentFetcher
     */
    public static ContentFetcher get(Context context) {
        if (sContentFetcher == null) {
            sContentFetcher = new ContentFetcher(context);
        }

        return sContentFetcher;
    }

    private ContentFetcher(final Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        mImageLoader = new ImageLoader(mRequestQueue, new DomainBitmapCache(context));
    }

    /**
     * Initiate a request to download the corpus of credential listings.
     *
     * @param responseListener to notify when corpus download and processing is complete.
     */
    public void requestListingCorpus(Response.Listener<String> responseListener) {
        StringRequest request = new StringRequest(Request.Method.GET, CORPUS_URL, responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, String.format("Unable to download corpus %s", error.toString()));
                    }
                });
        request.setTag(TAG);
        mRequestQueue.add(request);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void cancelRequests(Object tag) {
        mRequestQueue.cancelAll(TAG);
        // TODO(sgaw): To cancel outstanding ImageLoader cancel requests?
    }

}

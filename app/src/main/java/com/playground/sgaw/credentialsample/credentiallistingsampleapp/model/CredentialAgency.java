package com.playground.sgaw.credentialsample.credentiallistingsampleapp.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.playground.sgaw.credentialsample.credentiallistingsampleapp.ContentFetcher;

import java.util.ArrayList;

/**
 * Collection of login credentials for global listing.
 *
 * Persists for the application lifecycle (independent of activities, fragments, etc.)
 */
public class CredentialAgency {
    private static final String TAG = "CredentialAgency";
    private static final String IGNORE_SUFFIX = "@2x";
    private static final String ICON_SUFFIX = ".png";
    private static CredentialAgency sCredentialAgency;

    private Context mAppContext; // Access to resources, storage, etc.
    // What content is shown after filtering from search
    private ArrayList<Credential> mDisplayedCredentials;
    private ImmutableList<Credential> mOriginalCredentials;

    /**
     * Listener for completion of credential agency initialization.  Corpus for credentials
     * are fetched remotely.
     */
    public interface ListingCorpusListener {
        void onCorpusDownloaded();
    }


    // Singleton initialization and retrieval.
    public static CredentialAgency get(Context c) {
        if (sCredentialAgency == null) {
            sCredentialAgency = new CredentialAgency(c.getApplicationContext());
        }
        return sCredentialAgency;
    }

    private CredentialAgency(Context context) {
        mAppContext = context;
        mOriginalCredentials = ImmutableList.of();
        mDisplayedCredentials = Lists.newArrayList();
    }

    public void init(final ListingCorpusListener listener) {
        Log.i(TAG, "init()");

        // Data for which credentials are supported needs to be fetched over the network.
        ContentFetcher.get(mAppContext).requestListingCorpus(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "Listener.onResponse()");
                ImmutableList.Builder<Credential> builder = ImmutableList.builder();

                // BUGBUG(sgaw): Mutiple calls to init will result in the old credential ids
                // will accessing new credential instances.
                int id = 0;
                for (String filename : response.split("\n")) {
                    Log.v(TAG, String.format("Considering file: %s", filename));
                    String domain = iconFilenameToDomain(filename.trim());
                    // There are duplicate domains listed
                    if (!domain.endsWith(IGNORE_SUFFIX)) {
                        builder.add(new Credential(id, domain));
                        Log.v(TAG, String.format("Added domain: %s", domain));
                    }
                }

                mOriginalCredentials = builder.build();
                mDisplayedCredentials = Lists.newArrayList(mOriginalCredentials);
                Log.i(TAG, response);
                listener.onCorpusDownloaded();
            }
        });
    }

    /**
     * Returns the icon's filename (format {site}_co_uk.png) with the domain {site}.co_uk
     *
     * @param icon_filename
     * @return Decoded domain name
     */
    private String iconFilenameToDomain(String icon_filename) {
        Log.i(TAG, String.format("iconFilenameToDomain(%s)", icon_filename));
        StringBuffer buffer = new StringBuffer(icon_filename);
        buffer.replace(buffer.lastIndexOf(ICON_SUFFIX), icon_filename.length(), "");

        // Decode domains by replacing underscores with periods
        for (int i = 0; i < buffer.length(); i++) {
            if (buffer.charAt(i) == '_') {
                buffer.setCharAt(i, '.');
            }
        }
        Log.v(TAG, String.format("domain transformed to %s", buffer.toString()));
        return buffer.toString();
    }

    /**
     * Returns the number of elements in the currently displayed collections (after filtering).
     * @return
     */
    public int size() {
        return mDisplayedCredentials.size();
    }

    public Credential getCredentialWithId(int id) {
        return mOriginalCredentials.get(id);
    }

    public Credential getCredential(int position) {
        Log.i(TAG, String.format("getCredential(%d)", position));
        return mDisplayedCredentials.get(position);
    }

    public void filter(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            restore();
        } else {
            mDisplayedCredentials = Lists.newArrayList(Collections2.filter(mOriginalCredentials,
                    Credential.newPredicate(pattern)));
        }
    }

    public void restore() {
        mDisplayedCredentials = Lists.newArrayList(mOriginalCredentials);
    }
}

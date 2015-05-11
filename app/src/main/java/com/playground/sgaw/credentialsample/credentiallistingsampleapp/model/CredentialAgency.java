package com.playground.sgaw.credentialsample.credentiallistingsampleapp.model;

import android.content.Context;
import android.util.Log;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * Collection of login credentials for global listing.
 *
 * Persists for the application lifecycle (independent of activities, fragments, etc.)
 */
public class CredentialAgency {
    private static final String TAG = "sgaw.CredentialAgency";
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

    public void init(ListingCorpusListener listener) {
        Log.i(TAG, "init()");
        ImmutableList.Builder<Credential> builder = ImmutableList.builder();
        // TODO(sgaw): Fetch login credentials from file.
        for (int i = 0; i < 100; i++) {
            builder.add(new Credential(i, String.format("example-%d.com", i)));
        }
        mOriginalCredentials = builder.build();
        mDisplayedCredentials = Lists.newArrayList(mOriginalCredentials);
        listener.onCorpusDownloaded();
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

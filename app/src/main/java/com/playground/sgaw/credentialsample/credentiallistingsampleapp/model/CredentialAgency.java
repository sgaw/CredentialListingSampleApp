package com.playground.sgaw.credentialsample.credentiallistingsampleapp.model;

import android.content.Context;
import android.util.Log;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.regex.Pattern;

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

    private CredentialAgency(Context context) {
        mAppContext = context;
        mOriginalCredentials = null;
    }

    public void init() {
        Log.i(TAG, "init()");
        ImmutableList.Builder<Credential> builder = ImmutableList.builder();
        // TODO(sgaw): Fetch login credentials from file.
        for (int i = 0; i < 100; i++) {
            builder.add(new Credential(i, String.format("example-%d.com", i)));
        }
        mOriginalCredentials = builder.build();
        mDisplayedCredentials = Lists.newArrayList(mOriginalCredentials);

        // TODO(sgaw): Make this part of a search button result
        mDisplayedCredentials = Lists.newArrayList(Collections2.filter(mOriginalCredentials,
                Credential.containsPattern("5")));
    }

    // Singleton initialization and retrieval.
    public static CredentialAgency get(Context c) {
        if (sCredentialAgency == null) {
            sCredentialAgency = new CredentialAgency(c.getApplicationContext());

            sCredentialAgency.init();
        }
        return sCredentialAgency;
    }

    public ArrayList<Credential> getCredentials() {
        return mDisplayedCredentials;
    }

    public Credential getCredentialWithId(int id) {
        return mOriginalCredentials.get(id);
    }

    public Credential getCredential(int position) {
        Log.i(TAG, String.format("getCredential(%d)", position));
        return mDisplayedCredentials.get(position);
    }
}

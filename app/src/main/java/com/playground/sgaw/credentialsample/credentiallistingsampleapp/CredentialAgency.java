package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import android.content.Context;
import android.gesture.Prediction;

import java.util.ArrayList;

/**
 * Collection of login credentials for global listing.
 *
 * Persists for the application lifecycle (independent of activities, fragments, etc.)
 */
public class CredentialAgency {
    private static CredentialAgency sCredentialAgency;

    private Context mAppContext; // Access to resources, storage, etc.
    private ArrayList<Credential> mCredentials;

    private CredentialAgency(Context context) {
        mAppContext = context;
        mCredentials = new ArrayList<Credential>();
    }

    public void init() {
        // TODO(sgaw): Fetch login credentials from file.
        for (int i = 0; i < 100; i++) {
            mCredentials.add(new Credential(String.format("example-%d.com", i)));
        }
    }

    // Make collection of login credentials a singleton
    public static CredentialAgency get(Context c) {
        if (sCredentialAgency == null) {
            sCredentialAgency = new CredentialAgency(c.getApplicationContext());
        }
        return sCredentialAgency;
    }

    public ArrayList<Credential> getCredentials() {
        return mCredentials;
    }

    // TODO(sgaw): Make a credential view that uses Collections2.filter(.., Predicate)
    // Need to install Guava
}

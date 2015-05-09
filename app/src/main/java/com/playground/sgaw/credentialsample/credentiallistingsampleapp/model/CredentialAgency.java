package com.playground.sgaw.credentialsample.credentiallistingsampleapp.model;

import android.content.Context;
import android.util.Log;

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
    private ArrayList<Credential> mCredentials;

    private CredentialAgency(Context context) {
        mAppContext = context;
        mCredentials = new ArrayList<Credential>();
    }

    public void init() {
        Log.i(TAG, "init()");
        // TODO(sgaw): Fetch login credentials from file.
        for (int i = 0; i < 100; i++) {
            mCredentials.add(new Credential(i, String.format("example-%d.com", i)));
        }
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
        return mCredentials;
    }

    public Credential getCredentialWithId(int id) {
        return mCredentials.get(id);
    }

    // TODO(sgaw): Track a current credential collection view that uses Collections2.filter(.., Predicate)
    // Need to install Guava
    public Credential getCredential(int position) {
        Log.i(TAG, String.format("getCredential(%d)", position));
        return mCredentials.get(position);
    }

}

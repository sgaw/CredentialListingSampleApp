package com.playground.sgaw.credentialsample.credentiallistingsampleapp.model;

import android.graphics.Bitmap;

import com.google.common.base.Predicate;

/**
 * Represents the credentials for a login to an account on a specified domain.
 */
public class Credential {
    private final int mId; // Make this a UUID? simpler for random access this way.
    private final static String DEFAULT_USERNAME = "test_user@fakedomain.com";
    private final String domain;
    private final Bitmap icon;

    public static Predicate<Credential> containsPattern(final String pattern) {
        return new Predicate<Credential>() {
            @Override
            public boolean apply(Credential input) {
                return input.getDomain().contains(pattern);
            }
        };
    }

    public Credential(int id, String domain) {
        this.mId = id;
        // We can futz login info for now since we don't actually need it for the example.
        this.domain = domain;
        this.icon = null;
    }

    public String getDomain() {
        return domain;
    }

    public String getUsername() {
        return DEFAULT_USERNAME;
    }

    public boolean hasIcon() {
        return icon != null;
    }

    public Bitmap getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return domain;
    }

    public int getId() {
        return mId;
    }
}


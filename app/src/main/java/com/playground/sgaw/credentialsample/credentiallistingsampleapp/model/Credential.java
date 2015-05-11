package com.playground.sgaw.credentialsample.credentiallistingsampleapp.model;

import android.util.Log;

import com.google.common.base.Predicate;

/**
 * Represents the credentials for a login to an account on a specified mDomain.
 */
public class Credential {
    private static final String ICON_ENDPOINT =
            "https://s3-eu-west-1.amazonaws.com/static-icons/_android48";
    private static final String TAG = "Credential";
    private static final String ICON_SUFFIX = ".png";
    private final static String DEFAULT_USERNAME = "test_user@fakedomain.com";

    private final int mId; // Make this a UUID? simpler for random access this way.
    private final String mDomain;
    // For simplicity, duplicate filename and domain rather than deriving.
    private final String mIconFilename;


    /**
     * Predicate to determine if the mDomain of a credential contains the specified string.
     *
     * @param pattern pattern to match in the mDomain of the credential.  Assumes the string
     *                is not a regular expression.
     * @return Predicate that is true only when the credential contains the specified string.
     */
    public static Predicate<Credential> newPredicate(final String pattern) {
        return new Predicate<Credential>() {
            @Override
            public boolean apply(Credential input) {
                return input.getDomain().contains(pattern);
            }
        };
    }

    /**
     * Returns the icon's filename (format {site}_co_uk.png) with the domain {site}.co_uk
     *
     * @param icon_filename
     * @return Decoded domain name
     */
    public static String decodeDomain(String icon_filename) {
        Log.i(TAG, String.format("decodeDomain(%s)", icon_filename));
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

    public Credential(int id, String iconFilenname, String domain) {
        this.mId = id;
        this.mIconFilename = iconFilenname;
        // We can futz login info for now since we don't actually need it for the example.
        this.mDomain = domain;
    }

    public String getDomain() {
        return mDomain;
    }

    public String getUsername() {
        return DEFAULT_USERNAME;
    }

    @Override
    public String toString() {
        return mDomain;
    }

    public int getId() {
        return mId;
    }

    public String getIconUrl() {
        return String.format("%s/%s", ICON_ENDPOINT, mIconFilename);
    }
}


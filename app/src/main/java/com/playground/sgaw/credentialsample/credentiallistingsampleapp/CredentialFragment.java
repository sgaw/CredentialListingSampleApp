package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment for viewing the details of a login credential.
 */
public class CredentialFragment extends Fragment {
    // TODO(sgaw): find some initialization mechanism.
    private Credential mCredential = new Credential("foobar.com");

    public CredentialFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_credential, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.domain);
        textView.setText(mCredential.getDomain());

        textView = (TextView) rootView.findViewById(R.id.username);
        textView.setText(mCredential.getUsername());

        // TODO(sgaw): Setup icon rendering.

        return rootView;
    }
}

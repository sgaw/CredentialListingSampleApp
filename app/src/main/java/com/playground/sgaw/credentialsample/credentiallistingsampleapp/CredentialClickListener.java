package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import com.playground.sgaw.credentialsample.credentiallistingsampleapp.model.Credential;

/**
* Listener interface for receiving credential selection events.
*/
public interface CredentialClickListener {
    public void onCredentialSelected(Credential credential);
}

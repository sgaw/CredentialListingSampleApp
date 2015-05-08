package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * ViewAdapter for managing the user's login credentials
 */
public class CredentialListAdapter extends RecyclerView.Adapter<CredentialListAdapter.ViewHolder> {
    private static final String TAG = "CredentialListAdapter";
    private final CredentialAgency mCredentialAgency;

    public CredentialListAdapter(CredentialAgency credentialAgency) {
        this.mCredentialAgency = credentialAgency;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_credential, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindCredential(mCredentialAgency.getCredential(position));

    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount()");
        return mCredentialAgency.getCredentials().size();
    }

    /**
     * Manage the view to display a single credential.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "CredentialListAdapter.ViewHolder";
        private final TextView mDomainTextView;
        private final TextView mUsernameTextView;
        private final ImageView mIconImageView;

        private Credential mCredential;

        public ViewHolder(View v) {
            super(v);

            mDomainTextView = (TextView) v.findViewById(R.id.domain);
            mUsernameTextView = (TextView) v.findViewById(R.id.username);
            mIconImageView = (ImageView) v.findViewById(R.id.icon);
        }

        // Each time we change the credential, update the view's content.
        public void bindCredential(Credential credential) {
            Log.i(TAG, String.format("bindCredential(%s)", credential.toString()));
            mCredential = credential;

            mDomainTextView.setText(mCredential.getDomain());
            mUsernameTextView.setText(mCredential.getUsername());

            if (mCredential.hasIcon()) {
                mIconImageView.setImageBitmap(mCredential.getIcon());
            }
        }

    }
}
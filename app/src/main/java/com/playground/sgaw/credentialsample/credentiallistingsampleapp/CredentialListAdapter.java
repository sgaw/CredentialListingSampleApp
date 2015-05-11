package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.playground.sgaw.credentialsample.credentiallistingsampleapp.tools.ContentFetcher;
import com.playground.sgaw.credentialsample.credentiallistingsampleapp.model.Credential;
import com.playground.sgaw.credentialsample.credentiallistingsampleapp.model.CredentialAgency;

/**
 * ViewAdapter for managing the user's login credentials
 */
public class CredentialListAdapter extends RecyclerView.Adapter<CredentialListAdapter.ViewHolder>
        implements CredentialAgency.ListingCorpusListener {
    private static final String TAG = "CredentialListAdapter";
    private final CredentialAgency mCredentialAgency;
    private final CredentialClickListener mCredentialClickListener;

    public CredentialListAdapter(CredentialAgency credentialAgency,
                                 CredentialClickListener credentialClickListener) {
        this.mCredentialAgency = credentialAgency;
        this.mCredentialClickListener = credentialClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_credential, parent, false);
        ViewHolder vh = new ViewHolder(v, mCredentialClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindCredential(mCredentialAgency.getCredential(position));
    }

    @Override
    public int getItemCount() {
        return mCredentialAgency.size();
    }

    /**
     * Update the view when the underlying listing corpus is initialized.
     */
    @Override
    public void onCorpusDownloaded() {
        Log.i(TAG, "onCorpusDownloaded()");
        notifyDataSetChanged();
    }

    /**
     * Manage the view to display a single credential.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "Adapter.ViewHolder";
        private final TextView mDomainTextView;
        private final TextView mUsernameTextView;
        private final NetworkImageView mIconImageView;

        private Credential mCredential;
        private CredentialClickListener mCredentialClickListener;

        public ViewHolder(View v, CredentialClickListener credentialClickListener) {
            super(v);

            mDomainTextView = (TextView) v.findViewById(R.id.domain);
            mUsernameTextView = (TextView) v.findViewById(R.id.username);
            mIconImageView = (NetworkImageView) v.findViewById(R.id.icon);

            mIconImageView.setDefaultImageResId(android.R.drawable.ic_menu_close_clear_cancel);

            mCredential = null;
            mCredentialClickListener = credentialClickListener;

            v.setOnClickListener(this);
        }

        /**
         * Update the view's content when the underlying data has changed to another credential.
         */
        public void bindCredential(Credential credential) {
            Log.i(TAG, String.format("bindCredential(%s)", credential.toString()));
            mCredential = credential;

            mDomainTextView.setText(mCredential.getDomain());
            mUsernameTextView.setText(mCredential.getUsername());
            Log.v(TAG, mCredential.getIconUrl());
            mIconImageView.setImageUrl(mCredential.getIconUrl(),
                    ContentFetcher.get(mIconImageView.getContext()).getImageLoader());
        }

        /**
         * Notify listeners that a specified credential has been selected.
         * @param v
         */
        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick(...)");
            if (mCredential != null) {
                mCredentialClickListener.onCredentialSelected(mCredential);
            }
        }
    }
}

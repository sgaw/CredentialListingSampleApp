package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.playground.sgaw.credentialsample.credentiallistingsampleapp.model.Credential;
import com.playground.sgaw.credentialsample.credentiallistingsampleapp.model.CredentialAgency;
import com.playground.sgaw.credentialsample.credentiallistingsampleapp.tools.ContentFetcher;

/**
 * Fragment for viewing the details of a login credential.
 */
public class CredentialFragment extends DialogFragment {
    public static final String EXTRA_CREDENTIAL_ID =
            "com.playground.sgaw.credentialsample.credentiallistingsampleapp.CredentialFragment.ID";
    public static final String EXTRA_CREDENTIAL_SHOWS_DIALOG =
            "com.playground.sgaw.credentialsample.credentiallistingsampleapp.CredentialFragment"
                    + "SHOWS_DIALOG";
    private static final String TAG = "CredentialFragment";

    private Credential mCredential;
    private boolean mShowsDialog;

    public CredentialFragment() {
    }

    public static CredentialFragment newInstance(Credential credential, boolean showsDialog) {
        Log.i(TAG, String.format("newInstance(%s, %b)", credential.toString(), showsDialog));
        CredentialFragment credentialFragment = new CredentialFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_CREDENTIAL_ID, credential.getId());
        bundle.putBoolean(EXTRA_CREDENTIAL_SHOWS_DIALOG, showsDialog);

        credentialFragment.setArguments(bundle);
        return credentialFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getArguments().getInt(EXTRA_CREDENTIAL_ID);
        mCredential = CredentialAgency.get(getActivity()).getCredentialWithId(id);
        Log.v(TAG, String.format("mCredential=%s", mCredential.toString()));
        mShowsDialog = getArguments().getBoolean(EXTRA_CREDENTIAL_SHOWS_DIALOG);
        Log.i(TAG, "CredentialFragment as dialog " + mShowsDialog);

        if (mCredential == null) {
            Log.e(TAG, "Unable to initialize fragment, id = " + id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_credential, container, false);
        setShowsDialog(mShowsDialog);
        Log.i(TAG, String.format("setShowsDialog(%b)", mShowsDialog));

        // TODO(sgaw): This all seems redundant to the RecyclerView.ViewHolder but there
        // doesn't seem to be a shared parent ViewHolder...
        TextView textView = (TextView) rootView.findViewById(R.id.domain);
        textView.setText(mCredential.getDomain());

        textView = (TextView) rootView.findViewById(R.id.username);
        textView.setText(mCredential.getUsername());

        NetworkImageView imageView = (NetworkImageView) rootView.findViewById(R.id.imageView);
        imageView.setDefaultImageResId(android.R.drawable.ic_menu_close_clear_cancel);
        imageView.setImageUrl(mCredential.getIconUrl(),
                ContentFetcher.get(getActivity()).getImageLoader());

        return rootView;
    }
}

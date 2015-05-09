package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Fragment for viewing the details of a login credential.
 */
public class CredentialFragment extends Fragment {
    public static final String EXTRA_CREDENTIAL_ID =
            "com.playground.sgaw.credentialsample.credentiallistingsampleapp.CredentialFragment";
    private static final String TAG = "CredentialFragment";
    private Credential mCredential;

    public CredentialFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        int id = activity.getIntent().getIntExtra(EXTRA_CREDENTIAL_ID, -1);

        mCredential = CredentialAgency.get(activity).getCredentialWithId(id);

        if (mCredential == null) {
            Log.e(TAG, "Unable to initialize fragment, id = " + id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_credential, container, false);

        // TODO(sgaw): This all seems redundant to the RecyclerView.ViewHolder but there
        // doesn't seem to be a shared parent ViewHolder...
        TextView textView = (TextView) rootView.findViewById(R.id.domain);
        textView.setText(mCredential.getDomain());

        textView = (TextView) rootView.findViewById(R.id.username);
        textView.setText(mCredential.getUsername());

        if (mCredential.hasIcon()) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.icon);
            imageView.setImageBitmap(mCredential.getIcon());
        }

        return rootView;
    }
}

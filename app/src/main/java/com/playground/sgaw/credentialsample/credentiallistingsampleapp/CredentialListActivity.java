package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class CredentialListActivity extends ActionBarActivity implements CredentialClickListener {
    private static final String TAG = "CredentialListActivity";
    private static final String DIALOG_CREDENTIAL = "credential";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentiallisting);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CredentialListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_credential_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Display the details of a specific credential.  For tablets, make this a dialog.
     * For phones, make this another screen/activity.
     *
     * @param credential
     */
    @Override
    public void onCredentialSelected(Credential credential) {
        Log.i(TAG, String.format("onCredentialSelected(%s)", credential));

        if (findViewById(R.id.detailContainer) == null) {
            Log.i(TAG, "Detected phone");
            Intent i = new Intent(this, CredentialActivity.class);
            i.putExtra(CredentialFragment.EXTRA_CREDENTIAL_ID, credential.getId());
            startActivity(i);
        } else {
            Log.i(TAG, "Detected tablet");
            FragmentManager fragmentManager = getFragmentManager();
            CredentialFragment credentialFragment = CredentialFragment.newInstance(credential,
                    true);
            credentialFragment.show(fragmentManager, DIALOG_CREDENTIAL);
        }
    }
}

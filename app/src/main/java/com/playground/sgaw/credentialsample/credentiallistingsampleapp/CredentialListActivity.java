package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class CredentialListActivity extends ActionBarActivity implements CredentialListener {
    private static final String TAG = "CredentialListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
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
        // TODO(sgaw): Add check to see if we are tablet or phone
        Intent i = new Intent(this, CredentialActivity.class);
        startActivity(i);
    }
}

package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.playground.sgaw.credentialsample.credentiallistingsampleapp.model.Credential;
import com.playground.sgaw.credentialsample.credentiallistingsampleapp.model.CredentialAgency;


public class CredentialActivity extends Activity {

    private static final String TAG = "CredentialActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(...)");
        setContentView(R.layout.activity_fragment);


        FragmentManager fragmentManager =  getFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.container);

        // TODO(sgaw): How is this check different from just defaulting to the code that's generated
        // for adding to the fragment manager in all cases?  is this because of reuse?
        if (fragment == null) {
            int id = getIntent().getIntExtra(CredentialFragment.EXTRA_CREDENTIAL_ID, -1);

            if (id < -1) {
                Log.e(TAG, "Unable to find credential to display");
            }

            Credential credential = CredentialAgency.get(this).getCredentialWithId(id);
            Log.v(TAG, String.format("credential = %s", credential));

            fragmentManager.beginTransaction()
                    .add(R.id.container, CredentialFragment.newInstance(credential, false))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_credential, menu);
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
}

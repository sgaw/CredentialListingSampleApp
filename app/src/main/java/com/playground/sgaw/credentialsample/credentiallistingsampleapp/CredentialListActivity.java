package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.playground.sgaw.credentialsample.credentiallistingsampleapp.model.Credential;
import com.playground.sgaw.credentialsample.credentiallistingsampleapp.tools.ContentFetcher;

/**
 * Home screen activity for displaying all of the credentials in the corpus (or the filtered
 * view after searching).  Click on a single credential will spawn a new activity (phone) or
 * dialog for display.  Search will filter the listing to matching credentials until search is
 * cleared.
 */
public class CredentialListActivity extends ActionBarActivity implements CredentialClickListener {
    private static final String TAG = "CredentialListActivity";
    private static final String DIALOG_CREDENTIAL = "credential";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null) {
            boolean doGridLayout = (findViewById(R.id.detailContainer) != null);

            CredentialListFragment fragment = CredentialListFragment.newInstance(doGridLayout);
            fragment.setCredentialClickListener(this);

            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
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
        switch(item.getItemId()) {
            case R.id.menu_item_search:
                onSearchRequested();
                return true;
            case R.id.menu_item_clear:
                CredentialListFragment fragment = (CredentialListFragment)
                        getFragmentManager().findFragmentById(R.id.container);
                fragment.restoreListing();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Handle search and other intents.
     *
     * Search will filter the displayed listings.
     *
     * @param intent to process
     */
    @Override
    public void onNewIntent(Intent intent) {
        // TODO(sgaw): Ideally we'd handle filtering as the user types.
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            CredentialListFragment fragment = (CredentialListFragment)
                    getFragmentManager().findFragmentById(R.id.container);

            fragment.filterListing(intent.getStringExtra(SearchManager.QUERY));
        }
    }

    /**
     * Display the details of a specific credential.  For tablets, make this a dialog.
     * For phones, make this another screen/activity.
     *
     * @param credential to display
     */
    @Override
    public void onCredentialSelected(Credential credential) {
        Log.i(TAG, String.format("onCredentialSelected(%s)", credential));

        // Only the tablets have detail containers specified.
        if (findViewById(R.id.detailContainer) == null) {
            // For phones spawn a new full-screen activity to display the credential.
            Log.i(TAG, "Detected phone");
            Intent i = new Intent(this, CredentialActivity.class);
            i.putExtra(CredentialFragment.EXTRA_CREDENTIAL_ID, credential.getId());
            startActivity(i);
        } else {
            // For tablets, show the credential as a dialog.
            Log.i(TAG, "Detected tablet");
            FragmentManager fragmentManager = getFragmentManager();
            CredentialFragment credentialFragment = CredentialFragment.newInstance(credential,
                    true);
            credentialFragment.show(fragmentManager, DIALOG_CREDENTIAL);
        }
    }
}

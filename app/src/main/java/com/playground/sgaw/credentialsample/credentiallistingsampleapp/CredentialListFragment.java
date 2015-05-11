package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.playground.sgaw.credentialsample.credentiallistingsampleapp.model.CredentialAgency;
import com.playground.sgaw.credentialsample.credentiallistingsampleapp.tools.ContentFetcher;

/**
 * Fragment for viewing a listing of login credentials.
 */
public class CredentialListFragment extends Fragment {
    public static final String EXTRA_SHOWS_GRID =
            "com.playground.sgaw.credentialsample.credentiallistingsampleapp"
                    + "CredentialListFragment.SHOWS_GRID";
    private static final String TAG = "CredentialListFragment";
    private static final int GRID_SPAN = 2;

    private CredentialClickListener mCredentialClickListener = null;
    private CredentialListAdapter mAdapter = null;

    public static CredentialListFragment newInstance(boolean doGridLayout) {
        CredentialListFragment fragment = new CredentialListFragment();

        Bundle args = new Bundle();
        args.putBoolean(EXTRA_SHOWS_GRID, doGridLayout);
        fragment.setArguments(args);
        Log.i(TAG, String.format("EXTRA_SHOWS_GRID = %b", doGridLayout));

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO(sgaw): Is this necessary if the ImageLoader is a singleton?  In-flight requests
        // are not cancelled until the fragment is cancelled.
        setRetainInstance(true);
    }

    /**
     * Cancel outstanding content fetches.
     */
    @Override
    public void onStop() {
        super.onStop();
        ContentFetcher.get(getActivity()).cancelRequests(this);
    }


    /**
     * Lay out the fragment view as a grid or list depending on the fragment argument specification.
     * Couple view to view adapter and credential list model.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_credential_list, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.listing_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = null;
        if (getArguments().getBoolean(EXTRA_SHOWS_GRID)) {
            Log.i(TAG, "GridLayoutManager");
            layoutManager = new GridLayoutManager(getActivity(), GRID_SPAN);
        } else {
            Log.i(TAG, "LinearLayoutManager");
            layoutManager = new LinearLayoutManager(getActivity());
        }

        recyclerView.setLayoutManager(layoutManager);

        CredentialAgency credentialAgency = CredentialAgency.get(getActivity());
        mAdapter = new CredentialListAdapter(credentialAgency, mCredentialClickListener);
        recyclerView.setAdapter(mAdapter);

        credentialAgency.init(mAdapter);
        return rootView;
    }

    /**
     * Filter the original credentials list to find all domains that match the specified query.
     *
     * @param query Query pattern for matching credential domains.
     */
    public void filterListing(String query) {
        Log.i(TAG, String.format("filterListing(%s)", query));
        CredentialAgency.get(getActivity()).filter(query);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Replace the currently displayed credentials to the entire corpus of login credentials.
     */
    public void restoreListing() {
        Log.i(TAG, "restoreListing()");
        CredentialAgency.get(getActivity()).restore();
        mAdapter.notifyDataSetChanged();
    }

    public void setCredentialClickListener(CredentialClickListener credentialClickListener) {
        Log.i(TAG, "setCredentialClickListener(...)");
        mCredentialClickListener = credentialClickListener;
    }
}

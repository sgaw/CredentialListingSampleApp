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

/**
 * Fragment for viewing a listing of login credentials.
 */
public class CredentialListFragment extends Fragment {
    public static final String EXTRA_SHOWS_GRID =
            "com.playground.sgaw.credentialsample.credentiallistingsampleapp"
                    + "CredentialListFragment.SHOWS_GRID";
    private static final String TAG = "CredentialListFragment";
    private static final int GRID_SPAN = 2;

    private RecyclerView.Adapter mAdapter = null;

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
    }

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

        mAdapter = new CredentialListAdapter(CredentialAgency.get(getActivity()),
                (CredentialClickListener) getActivity());
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
                getActivity().onSearchRequested();
                return true;
            case R.id.menu_item_clear:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Filter the original credentials list to find all domains that match the specified query.
     *
     * @param query Query pattern for matching credential domains.
     */
    public void filterList(String query) {
        Log.i(TAG, String.format("filterList(%s)", query));
        CredentialAgency.get(getActivity()).filter(query);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Replace the currently displayed credentials to the entire corpus of login credentials.
     */
    public void restoreList() {
        Log.i(TAG, "restoreList()");
        CredentialAgency.get(getActivity()).restore();
        mAdapter.notifyDataSetChanged();
    }
}

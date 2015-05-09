package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        LinearLayoutManager layoutManager = null;
        if (getArguments().getBoolean(EXTRA_SHOWS_GRID)) {
            Log.i(TAG, "GridLayoutManager");
            layoutManager = new GridLayoutManager(getActivity(), GRID_SPAN);
        } else {
            Log.i(TAG, "LinearLayoutManager");
            layoutManager = new LinearLayoutManager(getActivity());
        }

        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new CredentialListAdapter(
                CredentialAgency.get(getActivity()), (CredentialClickListener) getActivity());
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}

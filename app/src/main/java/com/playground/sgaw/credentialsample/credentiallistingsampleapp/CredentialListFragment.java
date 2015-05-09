package com.playground.sgaw.credentialsample.credentiallistingsampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment for viewing a listing of login credentials.
 */
public class CredentialListFragment extends Fragment {
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

        // TODO(sgaw): Be able to handle GridLinearLayoutManager versus LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.Adapter adapter = new CredentialListAdapter(
                CredentialAgency.get(getActivity()), (CredentialClickListener) getActivity());
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}

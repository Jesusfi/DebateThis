package com.example.jesusizquierdo.debatethis.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewDebateFragment extends Fragment {


    public NewDebateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_new_debate, container, false);
        ((MainActivity) getContext()).getSupportActionBar().show();
        ((MainActivity) getContext()).getSupportActionBar().setTitle("New Debate");
        return viewRoot;
    }

}

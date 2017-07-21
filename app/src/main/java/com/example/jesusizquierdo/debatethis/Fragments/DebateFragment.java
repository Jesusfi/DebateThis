package com.example.jesusizquierdo.debatethis.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class DebateFragment extends Fragment {


    public DebateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_debate, container, false);

        MaterialSpinner spinner = (MaterialSpinner) viewRoot.findViewById(R.id.spinner_debateFragment);
        FloatingActionButton floatingActionButton = (FloatingActionButton) viewRoot.findViewById(R.id.fab_add_debateFragment);



        spinner.setItems(
                "Politics",
                "Religion",
                "Social Issues",
                "Gaming",
                "Education",
                "Environment",
                "Technology",
                "Philosophy",
                "Immigration");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getContext()).startNewDebateActivity();
            }
        });
        return viewRoot;
    }

    // TODO: Rename method, update argument and hook method into UI event




   }

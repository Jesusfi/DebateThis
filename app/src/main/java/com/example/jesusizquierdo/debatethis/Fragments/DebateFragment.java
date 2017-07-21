package com.example.jesusizquierdo.debatethis.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jesusizquierdo.debatethis.Classes.Debate;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCard;
import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.FirebaseDebateCardViewHolder;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.FirebaseDebateViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class DebateFragment extends Fragment {
    private DatabaseReference databaseReference;
    RecyclerView mRecyclerView;
    List<Debate> list;

    public DebateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_debate, container, false);

      //  databaseReference = FirebaseDatabase.getInstance().getReference();
        mRecyclerView = (RecyclerView) viewRoot.findViewById(R.id.rv_display_title_debateFragment);
        list = new ArrayList<>();

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

        setUpFirebaseAdapter("Temp");
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

    private void setUpFirebaseAdapter(String date) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Debate").child("Politics");

        FirebaseRecyclerAdapter mFirebaseAdapter = new FirebaseRecyclerAdapter<Debate, FirebaseDebateViewHolder>
                (Debate.class, R.layout.debate_card_rv, FirebaseDebateViewHolder.class,
                        databaseReference) {

            @Override
            protected void populateViewHolder(FirebaseDebateViewHolder viewHolder, Debate model, int position) {
                list.add(model);
                viewHolder.bindDebate(model);
            }


        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                progressDialog.dismiss();
           }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mFirebaseAdapter);


    }


   }

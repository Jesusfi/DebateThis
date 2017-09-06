package com.example.jesusizquierdo.debatethis.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.Debate;
import com.example.jesusizquierdo.debatethis.Classes.DebateInfo;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCard;
import com.example.jesusizquierdo.debatethis.FullDebate;
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
    List<DebateInfo> list;

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

        final MaterialSpinner spinner = (MaterialSpinner) viewRoot.findViewById(R.id.spinner_debateFragment);
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
                "Immigration",
                "Popular");

        setUpFirebaseAdapter("Politics");
        spinner.getSelectedIndex();

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                if(item.equals("Popular")){
                    setUpFirebaseAdapterTop(item);
                    Toast.makeText(getContext(),"Clicked Popular", Toast.LENGTH_SHORT).show();
                }else{
                    setUpFirebaseAdapter(item);

                }

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Object> list = spinner.getItems();

                //  Toast.makeText(getContext(),"Item selected " + list.get(spinner.getSelectedIndex()).toString() ,Toast.LENGTH_SHORT).show();
                ((MainActivity) getContext()).startNewDebateActivity(list.get(spinner.getSelectedIndex()).toString());

            }
        });
        return viewRoot;
    }


    private void setUpFirebaseAdapter(String topic) {
        databaseReference = FirebaseDatabase.getInstance().getReference("DebateInfo").child(topic);

        FirebaseRecyclerAdapter mFirebaseAdapter = new FirebaseRecyclerAdapter<DebateInfo, FirebaseDebateViewHolder>
                (DebateInfo.class, R.layout.debate_card_rv, FirebaseDebateViewHolder.class,
                        databaseReference) {

            @Override
            protected void populateViewHolder(FirebaseDebateViewHolder viewHolder, DebateInfo model, int position) {
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
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mFirebaseAdapter);


    }
    private void setUpFirebaseAdapterTop(String topic) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Popular");

        FirebaseRecyclerAdapter mFirebaseAdapter = new FirebaseRecyclerAdapter<DebateInfo, FirebaseDebateViewHolder>
                (DebateInfo.class, R.layout.debate_card_rv, FirebaseDebateViewHolder.class,
                        databaseReference) {

            @Override
            protected void populateViewHolder(FirebaseDebateViewHolder viewHolder, DebateInfo model, int position) {
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
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mFirebaseAdapter);


    }


}

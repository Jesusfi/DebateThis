package com.example.jesusizquierdo.debatethis;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.Debate;
import com.example.jesusizquierdo.debatethis.Classes.DebateInfo;
import com.example.jesusizquierdo.debatethis.Classes.Points;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.FirebaseDebateViewHolder;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.FirebasePointViewHolder;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.NewPointRVAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class FullDebate extends AppCompatActivity {
    TextView title;
    Boolean isPro;
    ArrayList<Debate> temp;
    DatabaseReference databaseReference;
    //List<Points> points;
    RecyclerView recyclerView, recyclerViewCons;
    NewPointRVAdapter adapter, adapterCons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_debate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Intent intent = getIntent();
        final String key = intent.getStringExtra("key");
        final String topic = intent.getStringExtra("topic");
        isPro = true;

        temp = new ArrayList<>();

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add_point_FullDebate);
        Button viewPros = (Button) findViewById(R.id.btn_view_pros_debateFull);
        Button viewCons = (Button) findViewById(R.id.btn_view_cons_debateFull);
        title = (TextView) findViewById(R.id.tv_title_fullDebate);
        recyclerView = (RecyclerView) findViewById(R.id.rv_points_pros_fullDebate);
        recyclerViewCons = (RecyclerView) findViewById(R.id.rv_points_cons_fullDebate);

        setUpFirebaseAdapter(topic, key);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Debate")
                .child(topic)
                .child(key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Debate debate = dataSnapshot.getValue(Debate.class);
                title.setText(debate.getTitle());
                temp.add(debate);

//                if(debate.getPros() == null || debate.getCons() == null){
//                    Toast.makeText(FullDebate.this,"List is null",Toast.LENGTH_SHORT).show();
//                }else{
//                    List<Points> pointsPro = debate.getPros();
//                    List<Points> pointsCon = debate.getCons();
//                  //  recyclerView = (RecyclerView) findViewById(R.id.rv_points_pros_fullDebate);
//                    adapter = new NewPointRVAdapter(FullDebate.this,pointsPro);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FullDebate.this);
//                    recyclerView.setLayoutManager(linearLayoutManager);
//                    recyclerView.setAdapter(adapter);
//
//                    adapterCons = new NewPointRVAdapter(FullDebate.this,pointsCon);
//                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(FullDebate.this);
//                    recyclerViewCons.setLayoutManager(linearLayoutManager1);
//                    recyclerViewCons.setAdapter(adapterCons);
//                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        viewCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewCons.getVisibility() == View.GONE) {
                    recyclerViewCons.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    isPro = false;
                }
            }
        });
        viewPros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerView.getVisibility() == View.GONE) {
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerViewCons.setVisibility(View.GONE);
                    isPro = true;
                }
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNewPoint = new Intent(FullDebate.this, NewPoint.class);
                intentNewPoint.putExtra("boolean", false);
                intentNewPoint.putExtra("isPro", isPro);
                Debate debate = temp.get(0);
                Bundle bundle = new Bundle();
                bundle.putSerializable("debate", debate);
                intent.putExtras(bundle);

                intentNewPoint.putExtra("topic", topic);
                intentNewPoint.putExtra("UniqueID", temp.get(0).getUniqueID());

                startActivity(intentNewPoint);
            }
        });
    }

    private void setUpFirebaseAdapter(String topic, String key ) {
        databaseReference = FirebaseDatabase.getInstance().getReference("DebatePoints").child(topic).child(key);

        FirebaseRecyclerAdapter mFirebaseAdapter = new FirebaseRecyclerAdapter<Points, FirebasePointViewHolder>
                (Points.class, R.layout.point_view_rv, FirebasePointViewHolder.class,
                        databaseReference) {

            @Override
            protected void populateViewHolder(FirebasePointViewHolder viewHolder, Points model, int position) {
                viewHolder.bindPoint(model);
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
        recyclerViewCons.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FullDebate.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewCons.setLayoutManager(linearLayoutManager);
        recyclerViewCons.setAdapter(mFirebaseAdapter);


    }

}

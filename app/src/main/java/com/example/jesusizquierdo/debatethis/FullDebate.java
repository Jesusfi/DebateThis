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
import com.example.jesusizquierdo.debatethis.Classes.Points;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.NewPointRVAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FullDebate extends AppCompatActivity {
    TextView title;
    ArrayList<Debate> temp;
    //List<Points> points;
    RecyclerView recyclerView, recyclerViewCons;
    NewPointRVAdapter adapter,adapterCons;
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

        Intent intent = getIntent();
        final String key = intent.getStringExtra("key");
        final String topic = intent.getStringExtra("topic");

        temp = new ArrayList<>();

        Button viewPros = (Button) findViewById(R.id.btn_view_pros_debateFull);
        Button viewCons = (Button) findViewById(R.id.btn_view_cons_debateFull);
        title = (TextView) findViewById(R.id.tv_title_fullDebate);
        recyclerView = (RecyclerView) findViewById(R.id.rv_points_pros_fullDebate);
        recyclerViewCons = (RecyclerView) findViewById(R.id.rv_points_cons_fullDebate);

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

                if(debate.getPros() == null || debate.getCons() == null){
                    Toast.makeText(FullDebate.this,"List is null",Toast.LENGTH_SHORT).show();
                }else{
                    List<Points> pointsPro = debate.getPros();
                    List<Points> pointsCon = debate.getCons();
                  //  recyclerView = (RecyclerView) findViewById(R.id.rv_points_pros_fullDebate);
                    adapter = new NewPointRVAdapter(FullDebate.this,pointsPro);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FullDebate.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);

                    adapterCons = new NewPointRVAdapter(FullDebate.this,pointsCon);
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(FullDebate.this);
                    recyclerViewCons.setLayoutManager(linearLayoutManager1);
                    recyclerViewCons.setAdapter(adapterCons);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        viewCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerViewCons.getVisibility() == View.GONE){
                    recyclerViewCons.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
        viewPros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerView.getVisibility() == View.GONE){
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerViewCons.setVisibility(View.GONE);
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, temp.get(0).getTitle(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

//                Solved it! Super simple. Just had to add push() to the end of my DatabaseRefercence like so. It gives every comment a key.
//
//                commentRef = pollRef.child("comments").push();
//                commentRef.setValue(comment);


                if(recyclerView.getVisibility() == View.GONE){
                    //they are gonna write a con

                }else{
                    //they are gonna write a pro

                }


            }
        });
    }

}

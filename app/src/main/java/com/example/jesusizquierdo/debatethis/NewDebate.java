package com.example.jesusizquierdo.debatethis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.Debate;
import com.example.jesusizquierdo.debatethis.Classes.DebateInfo;
import com.example.jesusizquierdo.debatethis.Classes.Points;
import com.example.jesusizquierdo.debatethis.DialogFragments.CommentDialogFragment;
import com.example.jesusizquierdo.debatethis.DialogFragments.NewPointDialogFragment;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.NewPointRVAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewDebate extends AppCompatActivity {
    List<Points> points;
    RecyclerView recyclerView;
    NewPointRVAdapter adapter;
    int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_debate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("New Discussion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        points = new ArrayList<Points>();


        recyclerView = (RecyclerView) findViewById(R.id.rv_points_newDebate);
        adapter = new NewPointRVAdapter(this, points);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        final EditText title = (EditText) findViewById(R.id.et_title_newDebate);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Button addPoint = (Button) findViewById(R.id.btn_add_point_newDebate);

        Intent intent = getIntent();

        final String topic = intent.getStringExtra("topic");


        addPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(NewDebate.this, NewPoint.class);
                intent1.putExtra("boolean",true);
                intent1.putExtra("isPro",true);
                startActivityForResult(intent1, REQUEST_CODE);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleString = title.getText().toString().trim();

                if (TextUtils.isEmpty(titleString) || points.size() == 0) {
                    Toast.makeText(NewDebate.this, "You must add a title and at least one point", Toast.LENGTH_SHORT).show();
                    Toast.makeText(NewDebate.this, topic, Toast.LENGTH_SHORT).show();

                } else {

                    Snackbar.make(view, "Saving debate", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference()
                            .child("Debate")
                            .child(topic)
                            .push();
                    String uniqueKey = firebaseDatabase.getKey();
                    DatabaseReference saveDebateInfo = FirebaseDatabase.getInstance().getReference()
                            .child("DebateInfo")
                            .child(topic)
                            .child(uniqueKey);
                    DebateInfo debateInfo = new DebateInfo(titleString, uniqueKey, topic);
                    Debate debate = new Debate(titleString, uniqueKey);
//                    debate.setPros(points);
//                    List<Points> cons = new ArrayList<Points>();
//                    cons.add(new Points("Nothing here yet", "Nothing here yet", "Nothing here yet"));
//                    debate.setCons(cons);

                    for(int i = 0; i < points.size(); i++){

                        DatabaseReference savePoints = FirebaseDatabase.getInstance().getReference()
                                .child("DebatePoints")
                                .child(topic)
                                .child(uniqueKey)
                                .child("pros")
                                .push();
                        savePoints.setValue(points.get(i));
                    }
                    firebaseDatabase.setValue(debate);
                    saveDebateInfo.setValue(debateInfo);
                    finish();

                }


                //     firebaseDatabase.setValue(new Debate("Crime",points));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String point = data.getStringExtra("Point");
                String argument = data.getStringExtra("Argument");
                String src = data.getStringExtra("Sources");

                updateRVWithNewPoint(point, argument, src);

            }
        }

    }


    public void updateRVWithNewPoint(String header, String arg, String src) {
        Points tempPoint = new Points(header, arg, src);
        points.add(tempPoint);
        adapter.notifyDataSetChanged();

    }

}

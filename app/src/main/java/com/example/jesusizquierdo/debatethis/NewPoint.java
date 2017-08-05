package com.example.jesusizquierdo.debatethis;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.Debate;
import com.example.jesusizquierdo.debatethis.Classes.Points;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewPoint extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_point);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("New Point");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText point = (EditText) findViewById(R.id.et_point_NewPoint);
        final EditText argument = (EditText) findViewById(R.id.et_argument_NewPoint);
        final EditText sources = (EditText) findViewById(R.id.et_sources_NewPoint);

        final Boolean isNewPoint = getIntent().getExtras().getBoolean("boolean");
        final Boolean isProList = getIntent().getExtras().getBoolean("isPro");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                String pointString = point.getText().toString().trim();
                String argumentString = argument.getText().toString().trim();
                String sourcesString = sources.getText().toString().trim();

                if(TextUtils.isEmpty(pointString) || TextUtils.isEmpty(argumentString) || TextUtils.isEmpty(sourcesString)){
                    Toast.makeText(NewPoint.this, "Please add a point, argument and source",Toast.LENGTH_SHORT).show();
                }else{

                    if(isNewPoint){

                        Intent intent = new Intent();
                        intent.putExtra("Point", pointString);
                        intent.putExtra("Argument",argumentString);
                        intent.putExtra("Sources", sourcesString);
                        setResult(RESULT_OK,intent);
                        finish();
                    }else{
                        Toast.makeText(NewPoint.this,"figure out to make it work", Toast.LENGTH_SHORT).show();
                        Points points = new Points(pointString,argumentString,sourcesString);
                        addNewPoint(isProList, points);
                    }
                }
            }
        });
    }
public void addNewPoint(Boolean isPro, Points point){
    // go into Debate
        //first i need the topic
    // next i need the unique id of the item
    // next I need  t tell it to go to the pro
    // create a unique id for the new point?
    String topic = getIntent().getExtras().getString("topic");
    String uniqueID = getIntent().getExtras().getString("UniqueID");

    Debate debate = (Debate) getIntent().getExtras().getSerializable("debate");

    if(isPro){
        DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference()
        .child("Debate")
        .child("temp");

        reference.setValue("String temp");
    }else{
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Debate").child(topic).child(uniqueID).child("cons").push();
        reference.setValue(point);
    }

//    commentRef = pollRef.child("comments").push();
//    commentRef.setValue(comment);
    }
}

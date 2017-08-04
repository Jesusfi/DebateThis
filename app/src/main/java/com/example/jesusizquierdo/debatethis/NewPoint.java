package com.example.jesusizquierdo.debatethis;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
                    }
                }
            }
        });
    }

}

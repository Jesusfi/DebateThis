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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.ArticleInfoDiscussion;
import com.example.jesusizquierdo.debatethis.Classes.Articles;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCard;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCardInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NewDiscussion extends AppCompatActivity {
    Articles articles;
    EditText userOpinion;
    EditText postTitle;
    Spinner pickCategory;
    String time;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discussion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        arrayList = new ArrayList<>();
        setTime();

        getSupportActionBar().setTitle("New Discussion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        userOpinion = (EditText) findViewById(R.id.et_opinion_newDiscussion);
        postTitle = (EditText) findViewById(R.id.et_userTitle_newDiscussion);
        pickCategory = (Spinner) findViewById(R.id.spinner_categorypicker_setTitle);
        final TextView articleTitle = (TextView) findViewById(R.id.tv_article_title_newDiscussion);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add_post);

        final Intent inten = getIntent();
        Bundle bundle = this.getIntent().getExtras();
        articles = bundle.getParcelable("articles");

        articleTitle.setText(articles.getTitle());
        Toast.makeText(this,articles.getTitle(),Toast.LENGTH_SHORT).show();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String title = postTitle.getText().toString();
                final String category = pickCategory.getSelectedItem().toString();


                if (TextUtils.isEmpty(title)) {


                    Toast.makeText(NewDiscussion.this, "Must add Title", Toast.LENGTH_SHORT).show();
                } else {
                    String date = "";
                    if (arrayList.size() > 0) {
                        date = Date(arrayList.get(arrayList.size() - 1));
                    } else {
                        date = Date(arrayList.get(0));

                    }

                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Discussion").child(date).push();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Discussion Info");

                    String uniqueID = databaseReference.getKey();

                    String userName = inten.getStringExtra("username");

                    ArticleInfoDiscussion discussion= new ArticleInfoDiscussion(
                            articles.getUrl(),
                            articles.getUrlToImage(),
                            articles.getDescription(),
                            articles.getTitle(),
                            uniqueID

                    );
                    databaseReference.setValue(discussion);

                    DiscussionCardInfo cardInfo = new DiscussionCardInfo(articles.getUrl(), articles.getTitle(), uniqueID, date);


                    Toast.makeText(NewDiscussion.this, "Post added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });




    }
    public void setTime() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Time");
        Object time = ServerValue.TIMESTAMP;
        reference.setValue(time);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object time2 = dataSnapshot.getValue(Object.class);

                arrayList.add(time2.toString());

//                String text = formatter.format(new Date(Integer.getInteger(time2.toString())));
                //              Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public String Date(String time) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        formatter.setTimeZone(TimeZone.getDefault());
        String text = formatter.format(new Date(Long.parseLong(time)));

        return text;
    }

}

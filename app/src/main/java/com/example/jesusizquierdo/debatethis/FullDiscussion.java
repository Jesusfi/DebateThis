package com.example.jesusizquierdo.debatethis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.Comment;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCard;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.FirebaseCommentViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FullDiscussion extends AppCompatActivity {
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    RecyclerView mRecyclerView;
    FloatingActionButton fab,fabCancel;
    EditText newComment;
    CardView cardView;
    ArrayList<Comment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_discussion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Discussion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.comments_rv);
        fab = (FloatingActionButton) findViewById(R.id.fab_new_comment_fullDescription);
        fabCancel = (FloatingActionButton) findViewById(R.id.fab_exit_comment_fullDescription);
        newComment = (EditText) findViewById(R.id.et_new_comment_fullDescription);
        cardView = (CardView) findViewById(R.id.card_head_fullDescription);

        TextView category = (TextView) findViewById(R.id.tv_category_fullDescription);
        TextView articleTitle = (TextView) findViewById(R.id.tv_article_title_fullDescription);
        TextView userDescription = (TextView) findViewById(R.id.tv_description_fullDescription);
        TextView cardTitle = (TextView) findViewById(R.id.tv_card_title_fullDescription);
        TextView username = (TextView) findViewById(R.id.tv_name_of_card_creator_fullDescription);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading comments.....");
        progressDialog.show();

        final Intent intent  = getIntent();
        Bundle bundle = this.getIntent().getExtras();
        final DiscussionCard discussionCard = (DiscussionCard) bundle.getSerializable("discussionInfo");

        category.setText(discussionCard.getCategory());
        articleTitle.setText(discussionCard.getArticleTitle());
        cardTitle.setText(discussionCard.getTitle());
        username.setText("Discussion started by "+ discussionCard.getUserName());

        if (!TextUtils.isEmpty(discussionCard.getUserDescription())) {
            userDescription.setText(discussionCard.getUserDescription());
        }else{
            userDescription.setVisibility(View.GONE);
        }


        databaseReference = FirebaseDatabase.getInstance().getReference("Comments").child(discussionCard.getUniqueKey());
        setUpFirebaseAdapter();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newComment.getVisibility() == View.GONE){
                    fabCancel.setVisibility(View.VISIBLE);
                    newComment.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    fab.setImageDrawable(ContextCompat.getDrawable(FullDiscussion.this, R.drawable.ic_check_black_48dp));

                }else{
                    if(TextUtils.isEmpty(newComment.getText().toString().trim())){
                        Toast.makeText(FullDiscussion.this,"You must write something",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(FullDiscussion.this,"Saving Comment",Toast.LENGTH_SHORT).show();
                        fabCancel.setVisibility(View.GONE);
                        newComment.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        fab.setImageDrawable(ContextCompat.getDrawable(FullDiscussion.this,R.drawable.ic_border_color_black_48dp));


                        String userName = intent.getStringExtra("username");

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(discussionCard.getUniqueKey()).push();
                        Comment comment = new Comment(newComment.getText().toString().trim(),userName);
                        reference.setValue(comment);
                    }

                }
            }
        });
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabCancel.setVisibility(View.GONE);
                newComment.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                fab.setImageDrawable(ContextCompat.getDrawable(FullDiscussion.this,R.drawable.ic_border_color_black_48dp));
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(FullDiscussion.this,discussionCard.getUrl(),Toast.LENGTH_SHORT).show();
                startCustomChromeTab(discussionCard.getUrl());

                /*final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String url = dataSnapshot.child("Discussion Info").child(discussionCard.getUniqueKey()).child("url").getValue(String.class);
                        Toast.makeText(FullDiscussion.this,"BUGGGG",Toast.LENGTH_SHORT).show();
                        // ((MainActivity)getContext()).startCustomChromeTabTest(url);
                        //    ((MainActivity)getContext()).startWebViewFragment(url);
                        //    startActivity(new Intent(getContext(), LoginActivity.class));
                        //  getActivity().finish();
                        //  dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/
            }
        });


    }

    private void setUpFirebaseAdapter() {
        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Comment, FirebaseCommentViewHolder>(Comment.class, R.layout.comments_rv, FirebaseCommentViewHolder.class,
                databaseReference) {
            @Override
            protected void populateViewHolder(FirebaseCommentViewHolder viewHolder, Comment model, int position) {
                viewHolder.bindComments(model);
            }


        };
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FullDiscussion.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        progressDialog.dismiss();


    }
    public void startCustomChromeTab(String url) {
        Uri uri = Uri.parse(url);

        // create an intent builder
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();

        // Begin customizing
        // set toolbar colors
        intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        // set start and exit animations
        intentBuilder.setStartAnimations(this,android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        intentBuilder.setExitAnimations(this, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        // build custom tabs intent
        CustomTabsIntent customTabsIntent = intentBuilder.build();

        // launch the url
        customTabsIntent.launchUrl(this, uri);
    }

}

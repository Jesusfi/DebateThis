package com.example.jesusizquierdo.debatethis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jesusizquierdo.debatethis.Classes.ArticleInfoDiscussion;
import com.example.jesusizquierdo.debatethis.Classes.Articles;
import com.example.jesusizquierdo.debatethis.Classes.Comment;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCard;
import com.example.jesusizquierdo.debatethis.Classes.News;
import com.example.jesusizquierdo.debatethis.DialogFragments.BioDialogFragment;
import com.example.jesusizquierdo.debatethis.DialogFragments.CommentDialogFragment;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.ArticleRVAdapter;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.FirebaseCommentViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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


        TextView articleTitle = (TextView) findViewById(R.id.tv_article_title_FullDiscussion);
        TextView description = (TextView) findViewById(R.id.tv_article_description_FullDiscussion);
        ImageView imageView = (ImageView) findViewById(R.id.imageView_article_FullDiscussion);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading comments.....");
        progressDialog.show();

        final Intent intent  = getIntent();
        Bundle bundle = this.getIntent().getExtras();
        final ArticleInfoDiscussion discussionCard = (ArticleInfoDiscussion) bundle.getSerializable("discussionInfo");


        articleTitle.setText(discussionCard.getArticleTitle());
        description.setText(discussionCard.getArticleSummary());
        try {
            Picasso.with(this)
                    .load(discussionCard.getImageURL())
                    .resize(300, 300)
                    .centerCrop()
                    .noFade()
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

//



        databaseReference = FirebaseDatabase.getInstance().getReference("Comments").child(discussionCard.getUniqueKey());
        setUpFirebaseAdapter();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();

                CommentDialogFragment dialogFragment = new CommentDialogFragment();

                dialogFragment.show(fm, "Bio");

            /*    Intent intent = new Intent(FullDiscussion.this,NewDiscussion.class);
                startActivity(intent);*/

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
    public void saveComment(String commentFromFragment){

        final Intent intent  = getIntent();
        Bundle bundle = this.getIntent().getExtras();
        final ArticleInfoDiscussion discussionCard = (ArticleInfoDiscussion) bundle.getSerializable("discussionInfo");

        String userName = intent.getStringExtra("username");

        Comment comment = new Comment(commentFromFragment,userName);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(discussionCard.getUniqueKey()).push();
        reference.setValue(comment);
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


}

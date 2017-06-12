package com.example.jesusizquierdo.debatethis.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.Comment;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCard;
import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.FirebaseCommentViewHolder;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.FirebaseDebateCardViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FullDescriptionFragment extends Fragment {
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    RecyclerView mRecyclerView;
    FloatingActionButton fab,fabCancel;
    EditText newComment;
    ArrayList<Comment> list;

    public FullDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_full_description, container, false);

        ((MainActivity)getContext()).getSupportActionBar().show();


        mRecyclerView = (RecyclerView) viewRoot.findViewById(R.id.comments_rv);
        fab = (FloatingActionButton) viewRoot.findViewById(R.id.fab_new_comment_fullDescription);
        fabCancel = (FloatingActionButton) viewRoot.findViewById(R.id.fab_exit_comment_fullDescription);
        newComment = (EditText) viewRoot.findViewById(R.id.et_new_comment_fullDescription);

        CardView cardView = (CardView) viewRoot.findViewById(R.id.card_head_fullDescription);
        TextView category = (TextView) viewRoot.findViewById(R.id.tv_category_fullDescription);
        TextView articleTitle = (TextView) viewRoot.findViewById(R.id.tv_article_title_fullDescription);
        TextView userDescription = (TextView) viewRoot.findViewById(R.id.tv_description_fullDescription);
        TextView cardTitle = (TextView) viewRoot.findViewById(R.id.tv_card_title_fullDescription);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading comments.....");
        progressDialog.show();

        final DiscussionCard discussionCard = (DiscussionCard) getArguments().get("DiscussionCard");

        category.setText(discussionCard.getCategory());
        articleTitle.setText(discussionCard.getArticleTitle());
        cardTitle.setText(discussionCard.getTitle());

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
                    fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_check_black_48dp));

                }else{
                    if(TextUtils.isEmpty(newComment.getText().toString())){
                        Toast.makeText(getContext(),"You must write something",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getContext(),"Saving Comment",Toast.LENGTH_SHORT).show();
                        fabCancel.setVisibility(View.GONE);
                        newComment.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        fab.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_border_color_black_48dp));
                        String userName = ((MainActivity)getActivity()).getUserName();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(discussionCard.getUniqueKey()).push();
                        Comment comment = new Comment(newComment.getText().toString(),userName);
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
                fab.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_border_color_black_48dp));
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String url = dataSnapshot.child("Discussion").child(discussionCard.getUniqueKey()).child("url").getValue(String.class);
                        Toast.makeText(getContext(),url,Toast.LENGTH_SHORT).show();
                        ((MainActivity)getContext()).startWebViewFragment(url);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        return viewRoot;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        progressDialog.dismiss();


    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
}

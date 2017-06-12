package com.example.jesusizquierdo.debatethis.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.Articles;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCard;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCardInfo;
import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class NewDiscussionFragment extends Fragment {
    Articles articles;
    EditText userOpinion;
    EditText postTitle;
    Spinner pickCategory;
    String time;
    ArrayList<String> arrayList;
    public NewDiscussionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_new_discussion, container, false);
        arrayList = new ArrayList<>();
        setTime();
        ((MainActivity)getContext()).getSupportActionBar().show();



        userOpinion = (EditText) rootView.findViewById(R.id.et_opinion_newDiscussion);
        postTitle = (EditText) rootView.findViewById(R.id.et_userTitle_newDiscussion);
        pickCategory = (Spinner) rootView.findViewById(R.id.spinner_categorypicker_setTitle);
        final TextView articleTitle = (TextView) rootView.findViewById(R.id.tv_article_title_newDiscussion);
        FloatingActionButton floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab_add_post);

         articles = (Articles) getArguments().get("Articles");

        articleTitle.setText(articles.getTitle());
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String title = postTitle.getText().toString();
                final String category = pickCategory.getSelectedItem().toString();


                if (TextUtils.isEmpty(title)) {


                    Toast.makeText(getActivity(), "Must add Title", Toast.LENGTH_SHORT).show();
                } else {
                    String date = "";
                    if(arrayList.size() > 0){
                       date =  Date(arrayList.get(arrayList.size()-1));
                    }else{
                        date = Date(arrayList.get(0));

                    }

                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Discussion").child(date).push();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Discussion Info");

                    String uniqueID = databaseReference.getKey();

                    String userName = ((MainActivity)getActivity()).getUserName();

                    DiscussionCard newDebatCard = new DiscussionCard(
                            title,
                            category,
                            articles.getAuthor(),
                            userOpinion.getText().toString(),
                            articles.getUrl(),
                            articles.getTitle(),
                            userName,
                            uniqueID);

                    databaseReference.setValue(newDebatCard);

                    DiscussionCardInfo cardInfo = new DiscussionCardInfo(articles.getUrl(), articles.getTitle(),uniqueID);
                    reference.child(uniqueID).setValue(cardInfo);

                    getFragmentManager().popBackStack();

                    Toast.makeText(getActivity(), "Post added successfully", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return rootView;
    }

    public void checkIfCreated(){

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Discussion Info");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean isCreated = false;
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    DiscussionCardInfo cardInfo = messageSnapshot.getValue(DiscussionCardInfo.class);

                    if (cardInfo.getUrl().equals(articles.getUrl())) {
                        isCreated = true;
                        break;
                    }
                }
                if(isCreated){
                    Toast.makeText(getContext(),"Darn looks like someone beat you to it",Toast.LENGTH_SHORT).show();
                }else{

                    final String title = postTitle.getText().toString();
                    final String category = pickCategory.getSelectedItem().toString();

                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Discussion").push();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Discussion Info");

                    String uniqueID = databaseReference.getKey();

                    String userName = ((MainActivity)getActivity()).getUserName();

                    DiscussionCard newDebatCard = new DiscussionCard(title, category, articles.getAuthor(),
                            userOpinion.getText().toString(),
                            articles.getUrl(),
                            articles.getTitle(),
                            userName,
                            uniqueID);

                    databaseReference.setValue(newDebatCard);

                    DiscussionCardInfo cardInfo = new DiscussionCardInfo(articles.getUrl(), articles.getTitle(),uniqueID);
                    reference.child(uniqueID).setValue(cardInfo);

                    getFragmentManager().popBackStack();

                    Toast.makeText(getActivity(), "Post added successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }
    public void setTime(){
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
    public String Date(String time){

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        formatter.setTimeZone(TimeZone.getDefault());
        String text = formatter.format(new Date(Long.parseLong(time)));

        return  text;
    }
    @Override
    public void onPause() {
        super.onPause();
//        ((MainActivity) getActivity()).getSupportActionBar().hide();
//        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}

package com.example.jesusizquierdo.debatethis.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.Comment;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCard;
import com.example.jesusizquierdo.debatethis.Classes.Timetemp;
import com.example.jesusizquierdo.debatethis.FragmentAdapters.SimpleFragmentPageAdapter;
import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.FirebaseDebateCardViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


public class DiscussionFragment extends Fragment {
    private DatabaseReference databaseReference;
    RecyclerView mRecyclerView;
    EditText editText;
    ProgressDialog progressDialog;
    ArrayList<DiscussionCard> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_discussion, container, false);

        ((MainActivity) getContext()).getSupportActionBar().hide();
        ((MainActivity) getContext()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton floatingActionButton = (FloatingActionButton) viewRoot.findViewById(R.id.fab_calender_discussion);
        Button nextButton = (Button) viewRoot.findViewById(R.id.btn_next_discussionFragment);
        Button backButton = (Button) viewRoot.findViewById(R.id.btn_back_discussionFragment);
        final TextView displayDate = (TextView) viewRoot.findViewById(R.id.tv_date_discussionFragment);
        mRecyclerView = (RecyclerView) viewRoot.findViewById(R.id.id_for_test);
        editText = (EditText) viewRoot.findViewById(R.id.search_string);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading debates and discussions");
        progressDialog.show();
        list = new ArrayList<>();

        final Calendar calendar = Calendar.getInstance();
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        df.setTimeZone(TimeZone.getDefault());
        String date = df.format(calendar.getTime());

        displayDate.setText(date);

        setUpFirebaseAdapter(date);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date
                DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

                    // when dialog box is closed, below method will be called.
                    public void onDateSet(DatePicker view, int selectedYear,
                                          int selectedMonth, int selectedDay) {

                        String day = Integer.toString(selectedDay);
                        String month = Integer.toString(selectedMonth + 1);
                        String year = Integer.toString(selectedYear);
                        if (selectedDay < 10) {
                            day = "0" + day;
                        }
                        if (selectedMonth < 10) {
                            month = "0" + month;
                        }

                        setUpFirebaseAdapter(year + "-" + month + "-" + day);
                        displayDate.setText(year + "-" + month + "-" + day);
                        Toast.makeText(getContext(), year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                    }
                };
// Create the DatePickerDialog instance
                DatePickerDialog datePicker = new DatePickerDialog(getContext(),
                        datePickerListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));


                datePicker.setCancelable(true);
                datePicker.show();

// Listener

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.DATE, 1);
                String date = df.format(calendar.getTime());
                displayDate.setText(date);
                setUpFirebaseAdapter(date);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.DATE, -1);
                String date = df.format(calendar.getTime());
                displayDate.setText(date);
                setUpFirebaseAdapter(date);

            }
        });
        return viewRoot;
    }

    private void setUpFirebaseAdapter(String date) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Discussion").child(date);

        DatabaseReference temp = FirebaseDatabase.getInstance().getReference("Discussions").child("wer");
        FirebaseRecyclerAdapter mFirebaseAdapter = new FirebaseRecyclerAdapter<DiscussionCard, FirebaseDebateCardViewHolder>
                (DiscussionCard.class, R.layout.discussion_card_rv, FirebaseDebateCardViewHolder.class,
                        databaseReference) {

            @Override
            protected void populateViewHolder(FirebaseDebateCardViewHolder viewHolder,
                                              DiscussionCard model, int position) {
                list.add(model);
                viewHolder.bindDebateCard(model);
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mFirebaseAdapter);


    }

    public void search() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                charSequence = charSequence.toString().toLowerCase();

                final List<DiscussionCard> filteredList = new ArrayList<>();

                for (int start = 0; start < list.size(); start++) {

                    final String text = list.get(start).getTitle().toLowerCase();
                    if (text.contains(charSequence)) {

                        filteredList.add(list.get(start));
                    }
                }

            /*    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter = new SimpleAdapter(filteredList, MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed*/


            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}

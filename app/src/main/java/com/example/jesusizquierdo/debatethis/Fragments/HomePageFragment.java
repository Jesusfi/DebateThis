package com.example.jesusizquierdo.debatethis.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.jesusizquierdo.debatethis.Classes.Comment;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCard;
import com.example.jesusizquierdo.debatethis.FragmentAdapters.SimpleFragmentPageAdapter;
import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Jesus Izquierdo on 5/28/2017.
 */

public class HomePageFragment extends Fragment {
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    FloatingActionButton fab;
    RecyclerView mRecyclerView;
    EditText editText;
    ArrayList<DiscussionCard> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_home_page, container, false);


        ViewPager viewPager = (ViewPager) viewRoot.findViewById(R.id.viewpager);
        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPageAdapter adapter = new SimpleFragmentPageAdapter(getChildFragmentManager(),getActivity());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) viewRoot.findViewById(R.id.tab_sliding);
        tabLayout.setupWithViewPager(viewPager);

        return viewRoot;


    }




}

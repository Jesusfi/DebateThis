package com.example.jesusizquierdo.debatethis.FragmentAdapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jesusizquierdo.debatethis.Fragments.DebateFragment;
import com.example.jesusizquierdo.debatethis.Fragments.DiscussionFragment;
import com.example.jesusizquierdo.debatethis.Fragments.HomePageFragment;
import com.example.jesusizquierdo.debatethis.Fragments.NewsFragment;

/**
 * Created by Jesus Izquierdo on 5/30/2017.
 */

public class SimpleFragmentPageAdapter extends FragmentPagerAdapter {
    private Context context;
    public SimpleFragmentPageAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DiscussionFragment();
            case 1:
                return new DebateFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Discuss";
            case 1:
                return "Debate";
            default:
                return null;
        }
    }
}


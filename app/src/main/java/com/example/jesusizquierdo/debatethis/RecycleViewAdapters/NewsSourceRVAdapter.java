package com.example.jesusizquierdo.debatethis.RecycleViewAdapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.NewsSource;
import com.example.jesusizquierdo.debatethis.Fragments.NewsFragment;
import com.example.jesusizquierdo.debatethis.R;

import java.util.ArrayList;

/**
 * Created by Jesus Izquierdo on 5/14/2017.
 */

public class NewsSourceRVAdapter extends RecyclerView.Adapter<NewsSourceRVAdapter.MyViewHolder> {

    private int selectedPosition = 0;

    Context context;
    ArrayList<NewsSource> strings;
    NewsFragment fragment;


    public NewsSourceRVAdapter(Context context, ArrayList<NewsSource> strings, NewsFragment fragment) {
        this.context = context;
        this.strings = strings;
        this.fragment = fragment;
    }

    public NewsSourceRVAdapter(Context context, ArrayList<NewsSource> strings) {
        this.context = context;
        this.strings = strings;
        this.fragment = fragment;
    }

    @Override
    public NewsSourceRVAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_source_rv, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsSourceRVAdapter.MyViewHolder holder, final int position) {
        holder.nameOfSource.setText(strings.get(position).getName());

        final int temp = position;
        if(selectedPosition==position)
            holder.itemView.setBackgroundColor(Color.parseColor("#ADD8E6"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = temp;
                notifyDataSetChanged();
                if (fragment != null) {
                    String source2 = strings.get(temp).getAPIrequest();
                    fragment.getNews(source2);
                } else {
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameOfSource;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameOfSource = (TextView) itemView.findViewById(R.id.tv_name_of_news_source);
        }


    }
}

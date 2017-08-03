package com.example.jesusizquierdo.debatethis.RecycleViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.Articles;
import com.example.jesusizquierdo.debatethis.Classes.Points;
import com.example.jesusizquierdo.debatethis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Izquierdo on 7/21/2017.
 */

public class NewPointRVAdapter extends RecyclerView.Adapter<NewPointRVAdapter.MyViewHolder> {
    Context context;
    List<Points> list;

    public NewPointRVAdapter(Context context, List<Points> pointsList){
        this.context = context;
        this.list = pointsList;
    }
    @Override
    public NewPointRVAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_view_rv, parent, false);
        return new NewPointRVAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewPointRVAdapter.MyViewHolder holder, int position) {
        Points points = list.get(position);
        holder.point.setText(points.getHeader());
        holder.argument.setText(points.getArgument());
        holder.source.setText(points.getSource());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView point,argument,source;
        LinearLayout container;
        public MyViewHolder(View itemView) {
            super(itemView);
            container = (LinearLayout) itemView.findViewById(R.id.ll_container_pointsRV);
            point = (TextView) itemView.findViewById(R.id.tv_point_pointsRV);
            argument = (TextView) itemView.findViewById(R.id.tv_argument_pointsRV);
            source = (TextView) itemView.findViewById(R.id.tv_source_pointsRV);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Testing ", Toast.LENGTH_SHORT).show();
                    if(container.getVisibility() == View.GONE){
                        container.setVisibility(View.VISIBLE);
                    }else{
                        container.setVisibility(View.GONE);
                    }
                }
            });

        }
    }
}

package com.example.jesusizquierdo.debatethis.RecycleViewAdapters;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.Points;
import com.example.jesusizquierdo.debatethis.R;

/**
 * Created by Jesus Izquierdo on 8/5/2017.
 */

public class FirebasePointViewHolder extends RecyclerView.ViewHolder {
    Context context;
    View view;

    public FirebasePointViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        view = itemView;
    }

    public void bindPoint(Points point) {
        final LinearLayout container = (LinearLayout) itemView.findViewById(R.id.ll_container_pointsRV);
        TextView pointtv = (TextView) itemView.findViewById(R.id.tv_point_pointsRV);
        TextView argument = (TextView) itemView.findViewById(R.id.tv_argument_pointsRV);
        TextView source = (TextView) itemView.findViewById(R.id.tv_source_pointsRV);

        pointtv.setText(point.getHeader());
        argument.setText(point.getArgument());
        source.setText(point.getSource());

        view.setOnClickListener(new View.OnClickListener() {
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

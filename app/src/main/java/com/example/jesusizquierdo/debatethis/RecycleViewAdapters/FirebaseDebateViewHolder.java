package com.example.jesusizquierdo.debatethis.RecycleViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jesusizquierdo.debatethis.Classes.Debate;
import com.example.jesusizquierdo.debatethis.Classes.DebateInfo;
import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;

/**
 * Created by Jesus Izquierdo on 7/21/2017.
 */

public class FirebaseDebateViewHolder extends RecyclerView.ViewHolder {
    Context context;
    View view;

    public FirebaseDebateViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        context = itemView.getContext();
    }

    public void bindDebate(final DebateInfo debate) {
        TextView title = (TextView) itemView.findViewById(R.id.tv_title_debate_card);
        title.setText(debate.getTitle());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).startFullDebateActivity(debate.getUniqueID(), debate.getTopic());
            }
        });
    }
}

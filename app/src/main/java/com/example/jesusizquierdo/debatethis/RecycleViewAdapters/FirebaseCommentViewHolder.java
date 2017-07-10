package com.example.jesusizquierdo.debatethis.RecycleViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jesusizquierdo.debatethis.Classes.Comment;
import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;

/**
 * Created by Jesus Izquierdo on 6/7/2017.
 */

public class FirebaseCommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    View mView;
    Context mContext;

    public FirebaseCommentViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindComments(Comment comment){
        TextView comment_tv = (TextView) itemView.findViewById(R.id.comment);
        TextView nameComment = (TextView) itemView.findViewById(R.id.tv_name_comment);
        comment_tv.setText(comment.getComment());
        nameComment.setText(comment.getUserName());
    }

    @Override
    public void onClick(View view) {
    }
}

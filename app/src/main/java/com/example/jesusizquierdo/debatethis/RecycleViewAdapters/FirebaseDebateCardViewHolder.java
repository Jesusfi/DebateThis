package com.example.jesusizquierdo.debatethis.RecycleViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.Articles;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCard;
import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;

/**
 * Created by Jesus Izquierdo on 5/27/2017.
 */

public class FirebaseDebateCardViewHolder extends RecyclerView.ViewHolder  {
    View mView;
    Context mContext;

    public FirebaseDebateCardViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindDebateCard(final DiscussionCard debateCard) {
        TextView articleTitle = (TextView) itemView.findViewById(R.id.tv_article_title_discussionCard);
        TextView source = (TextView) itemView.findViewById(R.id.tv_source_discussionCard);
        TextView description = (TextView) itemView.findViewById(R.id.tv_description_discussionCard);
        TextView cardTitle = (TextView) itemView.findViewById(R.id.tv_initial_card_discussionCard);
        TextView category = (TextView) itemView.findViewById(R.id.tv_category_discussionCard);
        TextView userName = (TextView) itemView.findViewById(R.id.tv_name_of_card_creator_discussionCard);

        articleTitle.setText(debateCard.getArticleTitle());
        category.setText(debateCard.getCategory());
        cardTitle.setText(debateCard.getTitle());
        userName.setText("Discussion started by "+ debateCard.getUserName());
        source.setText("Based of article from/by: " +debateCard.getSource());

        if(TextUtils.isEmpty(debateCard.getUserDescription())){
            description.setHeight(0);

        }else{
            description.setText( debateCard.getUserDescription());

        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiscussionCard discussionCard = debateCard;
                ((MainActivity) mContext).startFullDiscussionFragment(discussionCard);

                Toast.makeText(mContext,debateCard.getUniqueKey(),Toast.LENGTH_SHORT).show();
            }
        });

    }


}

package com.example.jesusizquierdo.debatethis.RecycleViewAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesusizquierdo.debatethis.Classes.ArticleInfoDiscussion;
import com.example.jesusizquierdo.debatethis.Classes.Articles;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCard;
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCardInfo;
import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Jesus Izquierdo on 5/14/2017.
 */

public class ArticleRVAdapter extends RecyclerView.Adapter<ArticleRVAdapter.MyViewHolder> {
    Context context;
    ArrayList<Articles> articles;

    public ArticleRVAdapter(Context context, ArrayList<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public ArticleRVAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_view_rv, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleRVAdapter.MyViewHolder holder, final int position) {
        Articles articles1 = articles.get(position);
        holder.title.setText(articles1.getTitle());
        holder.description.setText(articles1.getDescription());


        Context context = holder.imageView.getContext();


        try {
            Picasso.with(context)
                    .load(articles1.getUrlToImage())
                    .resize(300, 300)
                    .centerCrop()
                    .noFade()
                    .into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, description;
        Button joinDiscussion;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_article_title);
            description = (TextView) itemView.findViewById(R.id.tv_article_description);
            joinDiscussion = (Button) itemView.findViewById(R.id.btn_join_discussion);
            imageView = (ImageView) itemView.findViewById(R.id.imageView_article);
            itemView.setOnClickListener(this);
            joinDiscussion.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.btn_join_discussion) {

                final Articles articles1 = articles.get(getAdapterPosition());

                final Calendar calendar = Calendar.getInstance();
                final DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                df.setTimeZone(TimeZone.getDefault());
                final String date = df.format(calendar.getTime());
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Discussion").child(date);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean articleAlreadyMade = false;
                        DiscussionCardInfo cardInfo = null;
                        ArticleInfoDiscussion discussion = null;
                        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                             discussion = messageSnapshot.getValue(ArticleInfoDiscussion.class);

                            if (discussion.getUrl().equals(articles1.getUrl())) {
                                articleAlreadyMade = true;
                                break;
                                //this must change
                            }
                        }
// this is a comment
                        if (!articleAlreadyMade) {
                            ((MainActivity) context).startNewDiscussionActivity(articles1);
                        } else {
                            Toast.makeText(context, "ITEM EXISTS ALREADY", Toast.LENGTH_LONG).show();
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Discussion").child(discussion.getDate()).child(discussion.getUniqueKey());
                            databaseReference1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    ArticleInfoDiscussion discussionCard = dataSnapshot.getValue(ArticleInfoDiscussion.class);
                                    ((MainActivity)context).startFullDiscussionFragment(discussionCard);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            } else {


                String url = articles.get(getAdapterPosition()).getUrl();
                ((MainActivity)context).startCustomChromeTab(url);

               // ((MainActivity) context).startWebViewFragment(url);

            }

        }

        public void createDiscussion() {

        }

        private void createAndShowAlertDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Uh-Oh");
            builder.setMessage("No discussion on the article yet, would you like to create one?");
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //TODO
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //TODO
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}

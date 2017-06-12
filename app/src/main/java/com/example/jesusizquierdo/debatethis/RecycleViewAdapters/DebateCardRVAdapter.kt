package com.example.jesusizquierdo.debatethis.RecycleViewAdapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import com.example.jesusizquierdo.debatethis.Classes.DiscussionCard
import com.example.jesusizquierdo.debatethis.R

/**
 * Created by Jesus Izquierdo on 5/21/2017.
 */
class DebateCardRVAdapter(var array: ArrayList<DiscussionCard>,
                          var onClickListener: OnClickListener) : RecyclerView.Adapter<DebateCardRVAdapter.DebateCardViewHolder>() {
    override fun onBindViewHolder(holder: DebateCardViewHolder?, position: Int) {
        holder?.title?.text = array[position].title
        holder?.category?.text = array[position].category
        holder?.description?.text = array[position].userDescription


    }

    override fun getItemCount(): Int {
        return  array.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DebateCardViewHolder {
        return DebateCardViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.discussion_card_rv,parent,false))
    }


    inner class DebateCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title:TextView
        var category:TextView
        var description:TextView

        init {
            if(onClickListener != null){
                itemView.setOnClickListener(onClickListener)
            }
            itemView.tag =  this
            title = itemView.findViewById(R.id.tv_article_title_discussionCard) as TextView
            category = itemView.findViewById(R.id.tv_category_discussionCard) as TextView
            description = itemView.findViewById(R.id.tv_description_discussionCard) as TextView


        }
    }


}
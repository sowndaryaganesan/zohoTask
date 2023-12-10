package com.app.android.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.android.activity.NewsDetailActivity
import com.app.android.databinding.InflateNewsListBinding
import com.app.android.model.NewsListResponse
import com.bumptech.glide.Glide


class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.MyViewHolder>() {

    private  var newsLists: List<NewsListResponse.Results> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setNewsList(news: List<NewsListResponse.Results>) {
        this.newsLists = news
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val binding = InflateNewsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val newsList = newsLists[position]

        holder.newsTitle.text = newsList.title



        Glide.with(holder.itemView.context)
            .load(newsList.image_url)
            .into(holder.newsImage)



        holder.newsDescription.text = newsList.summary

        holder.readMore.setOnClickListener {
            if (holder.newsDescription.maxLines == 3) {
                // Expand text
                holder.newsDescription.maxLines = Integer.MAX_VALUE
                holder.readMore.text = "Read less"
            } else {
                // Collapse text
                holder.newsDescription.maxLines = 3
                holder.readMore.text = "Read more"
            }
        }

        holder.itemView.setOnClickListener {
            NewsDetailActivity.start(
                holder.itemView.context as Activity,
                newsList.url
            )
        }

    }

    override fun getItemCount(): Int {
        return newsLists.size
    }


    class MyViewHolder(binding: InflateNewsListBinding) : RecyclerView.ViewHolder(binding.root) {


        var newsTitle = binding.txtTitle
        var newsDescription = binding.txtDesc
        var newsImage = binding.newsImage
        var readMore = binding.tvReadMore


    }



}
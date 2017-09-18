package com.kotlin.whatshappening.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kotlin.whatshappening.R;
import com.kotlin.whatshappening.activity.Activity.FullScreenImageView;
import com.kotlin.whatshappening.activity.Activity.NewsDescriptionActivity;
import com.kotlin.whatshappening.activity.model.News;

import java.util.List;

/**
 * Created by gaurav on 18/9/17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<News> news;
    private int gallery_items;
    private Context context;

    public GalleryAdapter(List<News> news, int gallery_items, Context context) {
        this.news = news;
        this.gallery_items = gallery_items;
        this.context = context;

    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(gallery_items, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {

        holder.thumbnail_text.setText(news.get(position).getTitle());
        // loading album cover using Glide library
        Glide.with(context).load(news.get(position).getUrlToImage()).into(holder.thumbnail);

// apply click events
        applyClickEvents(holder, position);
    }

    private void applyClickEvents(GalleryViewHolder holder, final int position) {
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullScreenImageView.class);
                intent.putExtra("banner", news.get(position).getUrlToImage());
                intent.putExtra("description", news.get(position).getDescription());
                intent.putExtra("title", news.get(position).getTitle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    public class GalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView thumbnail;
        TextView thumbnail_text;
        LinearLayout gallery_layout;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            gallery_layout = (LinearLayout) itemView.findViewById(R.id.gallery_items);
            thumbnail = (ImageView) itemView.findViewById(R.id.image_thumbnail);
            thumbnail_text = (TextView) itemView.findViewById(R.id.image_text);
            itemView.setOnClickListener(this);
            ;
        }

        @Override
        public void onClick(View view) {

        }
    }
}

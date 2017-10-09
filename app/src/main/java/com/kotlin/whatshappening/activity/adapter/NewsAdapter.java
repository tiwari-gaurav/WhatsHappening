package com.kotlin.whatshappening.activity.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import com.kotlin.whatshappening.R;
import com.kotlin.whatshappening.activity.Activity.NewsDescriptionActivity;
import com.kotlin.whatshappening.activity.model.News;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by gaurav on 11/9/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<News> news;
    private int rowLayout;
    private Context context;


    public static class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout newsLayout;
        TextView author, title, description, url;
        ImageView newsImage, news_thumbnail;
        private WebView mWebview;
        public TextView buttonViewOption;

        public NewsViewHolder(View itemView) {
            super(itemView);
            newsLayout = (LinearLayout) itemView.findViewById(R.id.news_layout);
            url = (TextView) itemView.findViewById(R.id.url);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            newsImage = (ImageView) itemView.findViewById(R.id.news_image);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }

    public NewsAdapter(List<News> news, int rowLayout, Context context) {
        this.news = news;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsViewHolder holder, int position) {
        holder.description.setText(news.get(position).getDescription());
        holder.title.setText(news.get(position).getTitle());
        holder.url.setText(news.get(position).getUrl());


        // loading album cover using Glide library
        Glide.with(context).load(news.get(position).getUrlToImage()).into(holder.newsImage);


// apply click events
        applyClickEvents(holder, position);

    }

    private void setupWindowAnimations() {
        Slide slide = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            slide = new Slide();
            slide.setDuration(1000);
            ((Activity) context).getWindow().setExitTransition(slide);
        }


    }

    private void applyClickEvents(final NewsViewHolder holder, final int position) {


        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, news.get(position).getUrl() + " is selected!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, NewsDescriptionActivity.class);
                intent.putExtra("url", news.get(position).getUrl());
                intent.putExtra("banner", news.get(position).getUrlToImage());
                intent.putExtra("description", news.get(position).getDescription());
                intent.putExtra("author", news.get(position).getAuthor());
                intent.putExtra("title", news.get(position).getTitle());
                intent.putExtra("release_date", news.get(position).getRelease_date());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                View sharedView = holder.newsImage;
                String transitionName = context.getString(R.string.blue_name);

                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, sharedView, transitionName);
                context.startActivity(intent, transitionActivityOptions.toBundle());
               // context.startActivity(intent);
            }
        });

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, news.get(holder.getAdapterPosition()).getUrl(),news.get(holder.getAdapterPosition()).getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    private void showPopup(final View view, final String url, final String title) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.option_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, url);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "check out this site");
                        view.getContext().startActivity(Intent.createChooser(intent, "share"));
                        break;
                    case R.id.save:
                        writeResponseBodyToDisk(url, view.getContext(), view,  title);
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    private void writeResponseBodyToDisk(String url, Context context, final View view, final String title) {

        Ion.with(context).load(url).asString()

                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        // do something with your bitmap
                        Log.d("htmlBitmap", result.toString());
                        // checkExternalMedia();
                        writeToSDFile(result.toString(), view,title);

                    }
                });
    }


    /**
     * Method to write html text file on SD card. Note that you must add a
     * WRITE_EXTERNAL_STORAGE permission to the manifest file or this method will throw
     * a FileNotFound Exception because you won't have write permission.
     */

    private void writeToSDFile(String data, View view, String title) {

        // Find the root of the external storage.
        // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal

        File root = context.getExternalFilesDir(null);
        String FileName = title + ".html";
        File dir = new File(root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, FileName);

        try {
            FileOutputStream out = new FileOutputStream(file);
            byte[] html = data.getBytes();
            out.write(html);
            out.close();
            Log.e("fileSaved", "File Save : " + file.getPath());

            showSnackBar(view, dir);
            deleteFiles(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("FileNotFound", "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void deleteFiles(File file) {

        if(file.exists()){
            Calendar time = Calendar.getInstance();
            time.add(Calendar.DAY_OF_YEAR,-1);
            //I store the required attributes here and delete them
            Date lastModified = new Date(file.lastModified());
            if(lastModified.before(time.getTime()))
            {
                //file is older than a week
                file.delete();
            }

        }
    }

    private void showSnackBar(View view, final File dir) {

        Snackbar snackbar = Snackbar
                .make(view, "News saved in your external storage", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}

package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.Locale;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Models.NewsHeadlines;
import com.squareup.picasso.Picasso;

import java.util.List;
import android.util.Log;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<NewsHeadlines> headlines;
    private SelectListener listener;

    public CustomAdapter(Context context, List<NewsHeadlines> headlines, SelectListener listener) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.text_title.setText(headlines.get(position).getTitle());
        holder.text_source.setText(headlines.get(position).getSource().getName());
        if(headlines.get(position).getUrlToImage() != null){
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.img_headline);
        }

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNewsClicked(headlines.get(holder.getAdapterPosition()));
            }
        });

        setShareIcon(holder, position);
        setHeadlineAge(holder, position);
    }

    /**
     * set headline cardview age
     **/
    public void setHeadlineAge(CustomViewHolder holder, int position){

        try{
            String publishedAtUTC = headlines.get(position).getPublishedAt().substring(11,19);
            String dateStr = publishedAtUTC;


            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = df.parse(dateStr);
            df.setTimeZone(TimeZone.getDefault());
            String formattedDate = df.format(date);

            


            holder.headline_age_textview.setText(formattedDate);
        }catch(Exception e){
            holder.headline_age_textview.setText("? min ago");
        }

    }

    /**
     * enables share buttons on article cards
     * **/
    public void setShareIcon(CustomViewHolder holder, int position){
        LinearLayout shareButton = holder.shareButton;
        ImageButton shareImage = holder.shareImage;
        String url = headlines.get(position).getUrl();
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, url);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                context.startActivity(shareIntent);
            }
        };
        shareButton.setOnClickListener(listener);
        shareImage.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }
}

package com.example.newsapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView text_title, text_source;
    ImageView img_headline;
    CardView cardview;
    LinearLayout shareButton;
    ImageButton shareImage;
    TextView headline_age_textview;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        text_title = itemView.findViewById(R.id.text_title);
        text_source = itemView.findViewById((R.id.text_source));
        img_headline = itemView.findViewById(R.id.img_headline);
        cardview = itemView.findViewById(R.id.main_container);
        shareButton = itemView.findViewById(R.id.share_btn);
        shareImage = itemView.findViewById(R.id.share_image);
        headline_age_textview = itemView.findViewById(R.id.headline_age_textview);
    }
}

package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.newsapp.Models.NewsApiResponse;
import com.example.newsapp.Models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener{

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1, b2, b3, b4, b5, b6, b7;

    private final OnFetchListener<NewsApiResponse> listener = new OnFetchListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            showNews(list);
            dialog.dismiss();

        }

        @Override
        public void onError(String message) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching News Articles");
        dialog.show();

        RequestManager manager = new RequestManager(this);
        //queries API news data and then calls showNews() to display UI
        manager.getNewsHeadlines(listener, "general", null);

        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this);

        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this);

        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this);

        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this);

        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this);

        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this);

        b7 = findViewById(R.id.btn_7);
        b7.setOnClickListener(this);


    }


    /**
     * binds adapter to list of NewsHeadlines
     * sets recyclerview adapter
     * **/
    private void showNews(List<NewsHeadlines> list){
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list, this);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onNewsClicked(NewsHeadlines headlines) {
        startActivity(
                    new Intent(MainActivity.this, DetailsActivity.class)
                        .putExtra("data", headlines)
                    );
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String category = button.getText().toString();
        dialog.setTitle("Fetching News Articles of " + category);
        dialog.show();

        RequestManager manager = new RequestManager(this);
        //queries API news data and then calls showNews() to display UI
        manager.getNewsHeadlines(listener, category, null);
    }
}
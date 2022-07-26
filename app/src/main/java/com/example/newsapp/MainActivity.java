package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsapp.Models.NewsApiResponse;
import com.example.newsapp.Models.NewsHeadlines;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Calendar;
import android.util.Log;




public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener{

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1, b2, b3, b4, b5, b6, b7;
    SearchView searchView;

    private final OnFetchListener<NewsApiResponse> listener = new OnFetchListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty()){
                Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_LONG);
                return;
            }


            showNews(list);
            dialog.dismiss();

        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT);
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

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching news articles of " + query);
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                //queries API news data and then calls showNews() to display UI
                manager.getNewsHeadlines(listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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


    /**
     * when activity is clicked, request manager gets news headlines
     * **/
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
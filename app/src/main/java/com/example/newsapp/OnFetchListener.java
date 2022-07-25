package com.example.newsapp;

import com.example.newsapp.Models.NewsHeadlines;

import java.util.List;

/**
 * interface for methods to be called according to successful or failed
 * GET requests
 * **/
public interface OnFetchListener<NewsApiResponse> {
    void onFetchData(List<NewsHeadlines> list, String message);

    void onError(String message);
}

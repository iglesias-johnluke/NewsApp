package com.example.newsapp;

import android.content.Context;
import android.widget.Toast;

import com.example.newsapp.Models.NewsApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;

    /**retrofit library is framework for interacting
     * with APIS and sending network requests**/
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    /**SERVICE defining endpoint at url ending with "top-headlines"
     * (base url defined at retrofit instance variable)
     * **/
    public interface CallNewsApi {

        @GET("top-headlines")
        Call<NewsApiResponse> callHeadlines(//USED FOR GET REQUEST by appending parameters to URL of GET request
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String q,
                @Query("apiKey") String api_key
        );
    }

    /**handle responses from activity**/
    public void getNewsHeadlines(OnFetchListener listener, String category, String query){
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class); //

        //CREATES GET REQUEST by appending parameters to URL of GET request
        Call<NewsApiResponse> call = callNewsApi.callHeadlines(
                "us", category, query, context.getString(R.string.api_key));
        try{
            /**send request to server of API and returns Response object**/
            call.enqueue(new Callback<NewsApiResponse>() {

                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT);
                    }
                    /**listener only serves to act upon successful data FETCH
                    we provide listener with response article data
                    within onCreate we define onFetchData() to display recyclerview/news cards
                     **/
                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    listener.onError("Request Failed!");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

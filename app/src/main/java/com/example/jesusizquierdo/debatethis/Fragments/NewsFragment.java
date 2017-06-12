package com.example.jesusizquierdo.debatethis.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jesusizquierdo.debatethis.Classes.Articles;
import com.example.jesusizquierdo.debatethis.Classes.News;
import com.example.jesusizquierdo.debatethis.Classes.NewsSource;
import com.example.jesusizquierdo.debatethis.MainActivity;
import com.example.jesusizquierdo.debatethis.R;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.ArticleRVAdapter;
import com.example.jesusizquierdo.debatethis.RecycleViewAdapters.NewsSourceRVAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    RecyclerView newsSourceRV, articleView;
    NewsSourceRVAdapter sourceRVAdapter;
    ArticleRVAdapter articleRVAdapter;
    News news;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_news, container, false);

        ((MainActivity)getContext()).getSupportActionBar().hide();


        newsSourceRV = (RecyclerView) viewRoot.findViewById(R.id.news_source_rv);
        articleView = (RecyclerView) viewRoot.findViewById(R.id.article_view_rv);

        ArrayList<NewsSource> nameOfSources = new ArrayList<>();
        nameOfSources.add(new NewsSource("BBC News", "bbc-news"));
        nameOfSources.add(new NewsSource("IGN", "ign"));
        nameOfSources.add(new NewsSource("Daily Mail", "daily-mail"));
        nameOfSources.add(new NewsSource("Breitbart News", "breitbart-news"));
        nameOfSources.add(new NewsSource("Reddit/r/all", "reddit-r-all"));
        nameOfSources.add(new NewsSource("Ars Technica", "ars-technica"));
        nameOfSources.add(new NewsSource("Associated Press", "associated-press"));
        nameOfSources.add(new NewsSource("BBC Sports", "bbc-sport"));
        nameOfSources.add(new NewsSource("Bild", "bild"));
        nameOfSources.add(new NewsSource("Bloomberg", "bloomberg"));
        nameOfSources.add(new NewsSource("Business Insider", "business-insider"));
        nameOfSources.add(new NewsSource("Buzzfeed", "buzzfeed"));
        nameOfSources.add(new NewsSource("CNBC", "cnbc"));
        nameOfSources.add(new NewsSource("CNN", "cnn"));

        sourceRVAdapter = new NewsSourceRVAdapter(getActivity(), nameOfSources, NewsFragment.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        newsSourceRV.setLayoutManager(linearLayoutManager);
        newsSourceRV.setAdapter(sourceRVAdapter);

        getNews("bbc-news");

        return viewRoot;
    }

    public void getNews(String source) {
        String url = "";
        url = "https://newsapi.org/v1/articles?source=" + source + "&sortBy=top&apiKey=dcbcecc73b7f4eabb4ff7a5c2dc21c0e";

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                pDialog.dismiss();

                Gson gson = new Gson();
                News news = gson.fromJson(response, News.class);

                ArrayList<Articles> articles = new ArrayList<>();

                for (int i = 0; i < news.getArticles().size(); i++) {
                    articles.add(news.getArticles().get(i));
                }

                articleRVAdapter = new ArticleRVAdapter(getActivity(), articles);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
                articleView.setLayoutManager(linearLayoutManager1);
                articleView.setAdapter(articleRVAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                if (error instanceof TimeoutError ) {
                    Toast.makeText(getActivity(),
                            "Timeout error",
                            Toast.LENGTH_LONG).show();
                }else if( error instanceof NoConnectionError) {

                    Toast.makeText(getContext(),
                            "Connection error",
                            Toast.LENGTH_LONG).show();
                }else if (error instanceof AuthFailureError) {
                    Toast.makeText(getActivity(),
                            "Auth error",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getActivity(),
                            "Server error",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getActivity(),
                            "Network error",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getActivity(),
                            "Parse error",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        queue.add(stringRequest);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}

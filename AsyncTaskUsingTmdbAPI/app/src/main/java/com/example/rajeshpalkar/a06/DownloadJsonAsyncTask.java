package com.example.rajeshpalkar.a06;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;


public class DownloadJsonAsyncTask extends AsyncTask<String, Void,MovieDataJson> {
    MovieData movieData;
    private final WeakReference<MyRecyclerViewAdapter> weakReference;

    public DownloadJsonAsyncTask(MyRecyclerViewAdapter adapter, MovieData mData){
        weakReference =new WeakReference<MyRecyclerViewAdapter>(adapter);
        movieData = mData;
    }

    @Override
    protected MovieDataJson doInBackground(String... urls) {
        MovieDataJson threadMovieData=new MovieDataJson();
        for (String url:urls){
            threadMovieData.downloadMovieDataJson(url);
        }
        return threadMovieData;
    }

    @Override
    protected void onPostExecute(MovieDataJson threadMovieData){
        for (int i=0; i<threadMovieData.getSize();i++){

           movieData.getMoviesList().add(threadMovieData.moviesList.get(i));
        }
        if (weakReference !=null){
            final MyRecyclerViewAdapter adapter= weakReference.get();
            if (adapter!=null){
                adapter.notifyDataSetChanged();
            }
        }
    }
}
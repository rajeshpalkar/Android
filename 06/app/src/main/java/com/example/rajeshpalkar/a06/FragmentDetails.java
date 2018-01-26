package com.example.rajeshpalkar.a06;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

//import android.app.Fragment;

public class FragmentDetails extends Fragment {

    HashMap movie;
    private int total =0;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LruCache<String,Bitmap>mlrucahce;

    public FragmentDetails() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentDetails newInstance(HashMap<String,?> movie) {
        FragmentDetails fragment = new FragmentDetails();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(savedInstanceState!=null)
        {
            total = savedInstanceState.getInt("Total");
        }


        MovieData m = new MovieData();
        movie = (HashMap<String,?>) getArguments().getSerializable(ARG_PARAM1);

        if(mlrucahce == null)
        {
            final int maxMem = (int) (Runtime.getRuntime().maxMemory()/1024);
            final int cacheSize = maxMem/8;

            mlrucahce = new LruCache<String,Bitmap>(cacheSize){

                @Override
                protected int sizeOf(String key,Bitmap bitmap)
                {
                    return bitmap.getByteCount()/1024;
                }
            };
        }


    }

    public void onSavedInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("Total",total);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        String mv =  (String) movie.get("title");
        TextView t2 = (TextView) rootView.findViewById(R.id.textView7);
        t2.setText(mv);

        String dir =  (String.valueOf( movie.get("runtime")));
        TextView t = (TextView) rootView.findViewById(R.id.textView5);
        t.setText(dir);

        String cast =  (String) movie.get("release_date");
        TextView t1 = (TextView) rootView.findViewById(R.id.textView6);
        t1.setText(cast);

        String des =  (String) movie.get("overview");
        TextView t3 = (TextView) rootView.findViewById(R.id.textView8);
        t3.setText(" "+des);

        ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView4);
        if(mlrucahce!=null) {
            String imagePath = (String) movie.get("poster_path");
            String imageURL = "http://image.tmdb.org/t/p/w154/" + imagePath;
            MyDownloadimageAsyncTask task = new MyDownloadimageAsyncTask(imageView);
            task.execute(imageURL);
        }

        RatingBar rb = (RatingBar) rootView.findViewById(R.id.ratingBar);
        Double movieRating = ((Double) movie.get("vote_average"));
        Double halfMovieRating = movieRating/2;
        rb.setRating(halfMovieRating.floatValue());

        return rootView;
    }


    private class MyDownloadimageAsyncTask extends AsyncTask<String,Void,Bitmap> {

        private final WeakReference<ImageView> imageRef;


        public MyDownloadimageAsyncTask(ImageView imageView)
        {
            imageRef= new WeakReference<ImageView>(imageView);
        }


        @Override
        protected Bitmap doInBackground(String... urls)
        {
            Bitmap bitmap= null;
            for(String url:urls) {
                bitmap = MyUtility.downloadImageusingHTTPGetRequest(url);
                System.out.println("bitmap :"+bitmap);
                System.out.println("url :"+url);
                System.out.println("mLRU :"+mlrucahce);
                if (bitmap != null) {
                    mlrucahce.put(url, bitmap);
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            if(imageRef!=null && bitmap!=null){
                final ImageView imageView= imageRef.get();
                if(imageView!=null)
                {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}

package com.example.rajeshpalkar.a06;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.CheckBox;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;

//import android.app.Fragment;


public class FragmentRecyclerView3 extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    private OnListItemSelectedListener mListener;
    private LruCache<String,Bitmap> lruCache;

    private RecyclerView mRecyclerView;
    MovieData movieData = new MovieData();

   // View v = (View).getLayoutInflater().inflate(R.layout.fragment_recycler_view,null);
   // CheckBox mCheckBox = (CheckBox) getView().findViewById(R.id.selection);
    LinearLayoutManager mLayoutManager;
    MyRecyclerViewAdapter mRecyclerViewAdapter;

    public FragmentRecyclerView3() {
        // Required empty public constructor
    }


    public static FragmentRecyclerView3 newInstance(int sectionNumber) {
        FragmentRecyclerView3 fragment = new FragmentRecyclerView3();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

  /*      if(savedInstanceState != null) {
            System.out.println("6666");
            mCheckBox.setChecked(savedInstanceState.getBoolean("CHECKBOX_STATE"));
        }*/
        super.onCreate(savedInstanceState);
        mListener = (OnListItemSelectedListener) getContext();
        if(lruCache == null)
        {
            final int maxMem = (int) (Runtime.getRuntime().maxMemory()/1024);
            final int cacheSize = maxMem/8;

            lruCache = new LruCache<String,Bitmap>(cacheSize){

                @Override
                protected int sizeOf(String key,Bitmap bitmap)
                {
                    return bitmap.getByteCount()/1024;
                }
            };
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
 //       savedInstanceState.putBoolean("CHECKBOX_STATE", mCheckBox.isChecked());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recycler_view,container,false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

    //    mLayoutManager = new GridLayoutManager(getActivity(),2);

        mRecyclerView.setLayoutManager(mLayoutManager);
        lruCache.evictAll();
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),movieData.getMoviesList(),lruCache);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        DownloadJsonAsyncTask downloadJsonAsyncTask = new DownloadJsonAsyncTask(mRecyclerViewAdapter,movieData);
        downloadJsonAsyncTask.execute("http://api.themoviedb.org/3/movie/upcoming?api_key=bc9bd96f5f55e22b06f52654aaf41712");



        mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener(){
            public void onItemClick( View v, int position)
            {
                HashMap<String,?> movie = (HashMap<String,?>) movieData.getItem(position);
                String id = (String) movie.get("id");
                String URL = "http://api.themoviedb.org/3/movie/"+id+"?api_key=bc9bd96f5f55e22b06f52654aaf41712";
                if(URL!=null)
                {
                    MovieDetailDataAsyncTask movieDetailAsyncTask = new MovieDetailDataAsyncTask(mListener);
                    movieDetailAsyncTask.execute(URL);

                }
            }

            public void onItemLongClick(View v, int position)
            {
               movieData.addItem(position+1,(HashMap)((HashMap) movieData.getItem(position)).clone());
                mRecyclerViewAdapter.notifyItemInserted(position+1);

            }
        });

        mRecyclerViewAdapter.setOnItemCheckListener(new MyRecyclerViewAdapter.OnItemCheckListener(){
            public void onItemCheck(View v ,int position){
                 HashMap<String,Boolean> item = (HashMap<String,Boolean>) movieData.getItem(position);
                CheckBox  vCheckbox = (CheckBox) v.findViewById(R.id.selection);
                    if(vCheckbox.isChecked()) {
                        item.put("selection", true);
                    }
                    else {
                        item.put("selection",false);
                    }

            }
        });

/*
        Button Sel = (Button) rootView.findViewById(R.id.Select);
        Button Clr = (Button) rootView.findViewById(R.id.Clear);
        Button Del = (Button) rootView.findViewById(R.id.Delete);
        Button srt = (Button) rootView.findViewById(R.id.Sort);

        final CheckBox chk = (CheckBox) rootView.findViewById(R.id.selection);

        Sel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                for(int i =0;i<mRecyclerViewAdapter.getItemCount();i++)
                {
                    HashMap<String,Boolean> item = (HashMap<String,Boolean>) movieData.getItem(i);
                    item.put("selection",true);

                }

                mRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        Clr.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                for(int i =0;i<mRecyclerViewAdapter.getItemCount();i++)
                {

                    HashMap<String,Boolean> item = (HashMap<String,Boolean>) movieData.getItem(i);
                    item.put("selection",false);

                }

                mRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        Del.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                for(int i =mRecyclerViewAdapter.getItemCount()-1;i>=0;i--)
                {
                    HashMap<String,Boolean> item = (HashMap<String,Boolean>) movieData.getItem(i);
                    Boolean b = item.get("selection");

                    if(b==true)
                    {
                        movieData.removeItem(i);
                        mRecyclerViewAdapter.notifyItemRemoved(i);
                    }
                }

            }
        });

        srt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(movieData.moviesList, new Comparator<Map<String, ?>>() {
                    @Override
                    public int compare(Map<String, ?> o1, Map<String, ?> o2) {
                        return o2.get("year").toString().compareTo(o1.get("year").toString());
                    }
                });
                mRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
*/


         itemAnimation();
         adapterAnimation();
        return rootView;
    }
    private void itemAnimation()
    {

        FadeInRightAnimator fadeInRightAnimator = new FadeInRightAnimator();
        fadeInRightAnimator.setInterpolator(new OvershootInterpolator());

        fadeInRightAnimator.setAddDuration(800);
        fadeInRightAnimator.setRemoveDuration(800);

        mRecyclerView.setItemAnimator(fadeInRightAnimator);

    }

    public void adapterAnimation()
    {
        ScaleInAnimationAdapter Adapter = new ScaleInAnimationAdapter(mRecyclerViewAdapter);
        Adapter.setDuration(400);
        mRecyclerView.setAdapter(Adapter);



    }
    public interface OnListItemSelectedListener {
        public void onListItemSelected(int position, HashMap<String, ?> movie);
    }


    public class MovieDetailDataAsyncTask extends AsyncTask<String ,Void ,HashMap> {
        private final WeakReference<OnListItemSelectedListener> weakReference;

        public MovieDetailDataAsyncTask(OnListItemSelectedListener listener){
            weakReference =new WeakReference<OnListItemSelectedListener>(listener);
        }

        @Override
        protected HashMap doInBackground(String... urls) {
            MovieDataJson threadMovieData=new MovieDataJson();
            for (String url:urls){
                try {
                    threadMovieData.downloadSingleJson(url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (threadMovieData==null)
                return null;
            return threadMovieData.map2;
        }

        @Override
        protected void onPostExecute(HashMap movie){
            final OnListItemSelectedListener mListener1= weakReference.get();

            mListener1.onListItemSelected(0, movie);

        }

    }

}

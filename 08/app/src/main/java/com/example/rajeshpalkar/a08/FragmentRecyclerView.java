package com.example.rajeshpalkar.a08;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;

//import android.app.Fragment;


public class FragmentRecyclerView extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private List<Map<String, ?>> mDataset;

    private OnListItemSelectedListener mListener;

    private RecyclerView mRecyclerView;
    MovieData movieData = new MovieData();

   // View v = (View).getLayoutInflater().inflate(R.layout.fragment_recycler_view,null);
   // CheckBox mCheckBox = (CheckBox) getView().findViewById(R.id.selection);
    LinearLayoutManager mLayoutManager;
    MyRecyclerViewAdapter mRecyclerViewAdapter;

    public FragmentRecyclerView() {
        // Required empty public constructor
    }


    public static FragmentRecyclerView newInstance(int sectionNumber) {
        FragmentRecyclerView fragment = new FragmentRecyclerView();
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
       // final ImageView pic = (ImageView) rootView.findViewById(R.id.iconImage);
       // final  CardView card = (CardView) rootView.findViewById(R.id.cardView1);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),movieData.getMoviesList());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);




        mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener(){
            public void onItemClick(View rootView, int position)
            {
                //v= (View) mRecyclerView.findViewById(R.id.iconImage);
                HashMap<String,?> movie = (HashMap<String,?>) movieData.getItem(position);
                rootView.findViewById(R.id.iconImage).setTransitionName((String) movie.get("name"));
                mListener.onListItemSelected(rootView,movie);
            }

            public void onItemLongClick(View v, int position)
            {
               movieData.addItem(position,(HashMap)((HashMap) movieData.getItem(position)).clone());
                mRecyclerViewAdapter.notifyItemInserted(position);

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
        public void onListItemSelected(View v, HashMap<String, ?> movie);
    }

}

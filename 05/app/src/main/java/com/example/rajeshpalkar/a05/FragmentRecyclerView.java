package com.example.rajeshpalkar.a05;

import android.app.Activity;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
//import android.support.v7.view.ActionMode;
import android.support.v7.widget.PopupMenu;
import android.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;

//import android.app.Fragment;


public class FragmentRecyclerView extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    private OnListItemSelectedListener mListener;

    private RecyclerView mRecyclerView;
    MovieData movieData = new MovieData();


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
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mListener = (OnListItemSelectedListener) getContext();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {

        String abc = getActivity().getLocalClassName();
        System.out.println(abc);

        if(abc.equals("Task1Activity") )
        {
            if(menu.findItem(R.id.actionSearch)==null) {
                inflater.inflate(R.menu.search_view, menu);

                SearchView search = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
                if (search != null) {
                    search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            int position = movieData.findMovie(query);
                            if (position >= 0)
                                mRecyclerView.scrollToPosition(position);

                            return true;

                        }

                        @Override
                        public boolean onQueryTextChange(String query) {
                            return true;
                        }
                    });
                }
            }

        }

        if (abc.equals("Task2Activity") )
        {
             if(menu.findItem(R.id.sortbyname)==null)
            inflater.inflate(R.menu.activity2_top_toolbar, menu);
        }


        super.onCreateOptionsMenu(menu,inflater);

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.sortbyname:
                sortByName();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recycler_view,container,false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),movieData.getMoviesList());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);


        mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener(){
            public void onItemClick( View v, int position)
            {
                HashMap<String,?> movie = (HashMap<String,?>) movieData.getItem(position);
                mListener.onListItemSelected(position,movie);
            }

            public void onItemLongClick(View v, int position)
            {
                getActivity().startActionMode(new ActionBarCallBack(position));

            }

            public void onItemOverflowMenuClick(View v, final int position)
            {
                PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    public boolean onMenuItemClick(MenuItem item){
                        switch (item.getItemId())
                        {
                            case R.id.delete:
                                movieData.removeItem(position);
                                mRecyclerViewAdapter.notifyItemRemoved(position);
                                return true;
                            case R.id.duplicate:
                                movieData.addItem(position+1, (HashMap) movieData.getItem(position).clone());
                                mRecyclerViewAdapter.notifyItemInserted(position+1);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.contextual_menu,popupMenu.getMenu());
                popupMenu.show();
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
                        item.put("selection",true);
                    }

            }
        });

        String title = "Movie List";
        mListener.changeTileToDefault(title);

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
        public void changeTileToDefault(String title);
    }


    public void sortByYear()
    {

        Collections.sort(movieData.moviesList, new Comparator<Map<String, ?>>() {
            @Override
            public int compare(Map<String, ?> o1, Map<String, ?> o2) {
                return o2.get("year").toString().compareTo(o1.get("year").toString());
            }
        });

        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void sortByName()
    {
        Collections.sort(movieData.moviesList, new Comparator<Map<String, ?>>() {
            @Override
            public int compare(Map<String, ?> o1, Map<String, ?> o2) {
                return o1.get("name").toString().compareTo(o2.get("name").toString());
            }
        });

        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    class ActionBarCallBack implements ActionMode.Callback {
        int position;

        public ActionBarCallBack(int position) {
            this.position = position;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();

            switch (id) {
                case R.id.delete:
                    movieData.removeItem(position);
                    mRecyclerViewAdapter.notifyItemRemoved(position);
                    mode.finish();
                    break;
                case R.id.duplicate:
                    movieData.addItem(position+1, (HashMap) movieData.getItem(position).clone());
                    mRecyclerViewAdapter.notifyItemInserted(position+1);
                    mode.finish();
                    break;
                default:
                    break;
            }
            return true;
        }


        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_menu, menu);
            return true;
        }

        public void onDestroyActionMode(ActionMode mode){

        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu){
            return false;
        }
    }
}

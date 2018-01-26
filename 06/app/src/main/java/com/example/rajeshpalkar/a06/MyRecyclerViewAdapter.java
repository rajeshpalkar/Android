package com.example.rajeshpalkar.a06;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/**
 * Created by rajeshpalkar on 7/17/17.
 */


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Map<String, ?>> mDataset;
    private LruCache<String,Bitmap>mlrucahce;
    private Context mContext;
    private OnItemClickListener mItemClickListener;
    private OnItemCheckListener mItemCheckListener;

    public MyRecyclerViewAdapter(Context myContext, List<Map<String, ?>> myDataset, LruCache<String,Bitmap>lruCache) {
        mContext = myContext;
        mDataset = myDataset;
        mlrucahce=lruCache;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public CheckBox vCheckbox;
        public RatingBar vrb;


        public ViewHolder(View v) {
            super(v);

            vIcon = (ImageView) v.findViewById(R.id.iconImage);
            vTitle = (TextView) v.findViewById(R.id.titleMovie);
            vDescription = (TextView) v.findViewById(R.id.description);
            vCheckbox = (CheckBox) v.findViewById(R.id.selection);
            vrb = (RatingBar) v.findViewById(R.id.ratingBar);


            v.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getAdapterPosition());

                    }
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemLongClick(v, getAdapterPosition());
                    }
                    return true;
                }
            });


            vCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (mItemCheckListener != null) {
                        mItemCheckListener.onItemCheck(buttonView, getAdapterPosition());
                    }

                }
            });
        }



        public void bindMovieData(Map<String, ?> movie) {
           // String url1 = "http://api.themoviedb.org/3/movie/now_playing?api_key=bc9bd96f5f55e22b06f52654aaf41712";
            vTitle.setText((String) movie.get("name"));
            vDescription.setText((String) movie.get("description"));

            String myURL = (String) movie.get("url");

            if(mlrucahce!=null) {
                final Bitmap mbitmap = mlrucahce.get(myURL);

                if (mbitmap != null) {
                    vIcon.setImageBitmap(mbitmap);
                } else {
                    MyDownloadimageAsyncTask imagebit = new MyDownloadimageAsyncTask(vIcon);
                    imagebit.execute(myURL);

                }

            }
          ;

           // vCheckbox.setChecked((Boolean) movie.get("selection"));
//            Double movieRating = (Double) movie.get("vote_average");
//            Double halfMovieRating = movieRating/2;
//            vrb.setRating(halfMovieRating.floatValue());
         //   task.execute(url1);
        }

    }
        public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {

          System.out.println("view type :"+viewType);
            View v=null;

            v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_layout, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }


        public int getItemViewType(int position)
    {


        return position;
    }


        public void onBindViewHolder(ViewHolder holder, int position) {
             Map<String, ?> movie = (Map<String, ?>)mDataset.get(position);
             holder.bindMovieData(movie);



        }


        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
            public void onItemLongClick(View view, int position);
        }

        public interface OnItemCheckListener{
            public void onItemCheck(View view, int position);
        }

        public void setOnItemCheckListener(final OnItemCheckListener mItemCheckListener)
        {
            this.mItemCheckListener = mItemCheckListener;
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;
        }

        public int getItemCount() {return mDataset.size();}


    private class MyDownloadimageAsyncTask extends AsyncTask<String,Void,Bitmap> {

        private final WeakReference<ImageView> imageRefernce;


        public MyDownloadimageAsyncTask(ImageView imgref1)
        {
            imageRefernce= new WeakReference<ImageView>(imgref1);
        }


        @Override
        protected Bitmap doInBackground(String... urls)
        {
            Bitmap bitmap= null;
            for(String url:urls) {
                bitmap = MyUtility.downloadImageusingHTTPGetRequest(url);

                if (bitmap != null) {
                    mlrucahce.put(url, bitmap);
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            if(imageRefernce!=null && bitmap!=null){
                final ImageView imageView= imageRefernce.get();
                if(imageView!=null)
                {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}

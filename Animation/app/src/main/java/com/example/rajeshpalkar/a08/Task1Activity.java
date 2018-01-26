package com.example.rajeshpalkar.a08;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;

public class Task1Activity extends AppCompatActivity implements FragmentRecyclerView.OnListItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);


        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, FragmentRecyclerView.newInstance(R.id.fragmentrecyclerview))
                    .commit();
        }
        else {

        }
    }

    public void onListItemSelected(View sharedImage, HashMap<String,?>movie)
    {

        FragmentDetails details = FragmentDetails.newInstance(movie);
        details.setSharedElementEnterTransition(new DetailsTransition());
        details.setEnterTransition(new Fade());
        details.setExitTransition(new Fade());
        details.setSharedElementReturnTransition(new DetailsTransition());

        ImageView pic = (ImageView) sharedImage.findViewById(R.id.iconImage);

        getSupportFragmentManager().beginTransaction()
                .addSharedElement(pic,pic.getTransitionName())
                .replace(R.id.main_container,details)
                .addToBackStack(null)
                .commit();

    }

    public class DetailsTransition extends TransitionSet{
        public DetailsTransition()
        {
            setOrdering(ORDERING_TOGETHER);
            addTransition(new ChangeBounds())
                    .addTransition(new ChangeTransform())
                    .addTransition(new ChangeImageTransform());
        }
    }
}

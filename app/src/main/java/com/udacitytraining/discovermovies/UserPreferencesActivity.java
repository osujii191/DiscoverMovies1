package com.udacitytraining.discovermovies;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.udacitytraining.discovermovies.fragments.SortMenuFragment;

/**
 * Created by osuji_000 on 4/2/2017.
 */

public class UserPreferencesActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        getFragmentManager().beginTransaction()
                .add(new SortMenuFragment(),"SortMenuFragment")
                .commit();
        return super.onCreateView(name, context, attrs);

    }
}

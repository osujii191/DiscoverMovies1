package com.udacitytraining.discovermovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.udacitytraining.discovermovies.fragments.SortMenuFragment;

/**
 * Created by osuji_000 on 4/2/2017.
 */

public class UserPreferencesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SortMenuFragment())
                .commit();
    }


}

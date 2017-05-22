package com.udacitytraining.discovermovies.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.udacitytraining.discovermovies.R;

/**
 * Created by osuji_000 on 4/2/2017.
 */

public class SortMenuFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }


}

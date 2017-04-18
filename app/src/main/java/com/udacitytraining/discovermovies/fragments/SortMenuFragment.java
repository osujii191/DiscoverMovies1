package com.udacitytraining.discovermovies.fragments;

import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacitytraining.discovermovies.R;

/**
 * Created by osuji_000 on 4/2/2017.
 */

public class SortMenuFragment extends PreferenceFragment {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);
    }


}

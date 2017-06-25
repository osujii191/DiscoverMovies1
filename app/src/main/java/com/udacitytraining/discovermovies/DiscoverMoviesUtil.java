package com.udacitytraining.discovermovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by osuji_000 on 3/27/2017.
 */

public class DiscoverMoviesUtil {




    public static URL createUrl(Context ctx, String baseUrl) {

        URL finalUrl = null;

        Uri popMoviesUri =
                Uri.parse(baseUrl).buildUpon()
                        .appendQueryParameter(ctx.getString(R.string.api_key_str),ctx.getString(R.string.tmdb_api_key)).build();

        try {
            finalUrl = new URL(popMoviesUri.toString());
        } catch (MalformedURLException e) {
            Toast.makeText(ctx,"Error creating TMDB Url",Toast.LENGTH_LONG);
        }

        return finalUrl;
    }



    public static URL createUrlFromSortPreference(Context ctx) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        String sortValue =  prefs.getString(ctx.getString(R.string.sortPreferenceKey),ctx.getString(R.string.sort_preference_default_value));

        String url = "0".equals(sortValue) ? ctx.getString(R.string.tmdb_popular_movies_base_url) : ctx.getString(R.string.tmdb_top_rated_base_url);
        return createUrl(ctx,url);
    }


    public static boolean isInternetConnected(Context ctx) {
        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected() ) {
            isConnected =  true;
        }

        return isConnected;
    }


    public static URL createUrlFromSortPreference(Context ctx, SharedPreferences sharedPreferences, String key) {

        String sortValue =  sharedPreferences.getString(key,ctx.getString(R.string.sort_preference_default_value));

        String url =  "0".equals(sortValue) ? ctx.getString(R.string.tmdb_popular_movies_base_url) : ctx.getString(R.string.tmdb_top_rated_base_url);
        return createUrl(ctx,url);

    }
}

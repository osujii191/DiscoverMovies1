package com.udacitytraining.discovermovies;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by osuji_000 on 3/27/2017.
 */

public class DiscoverMoviesUtil {

    private static final String api_key = "api_key";



    public static URL createUrl(Context ctx, String baseUrl) {

        URL finalUrl = null;

        Uri popMoviesUri =
                Uri.parse(baseUrl).buildUpon()
                        .appendQueryParameter(api_key,ctx.getString(R.string.tmdb_api_key)).build();

        try {
            finalUrl = new URL(popMoviesUri.toString());
        } catch (MalformedURLException e) {
            Toast.makeText(ctx,"Error creating TMDB Url",Toast.LENGTH_LONG);
        }

        return finalUrl;
    }


    public static URL createUrlForRatedMovies(MainActivity mainActivity) {
        return createUrl(mainActivity, mainActivity.getString(R.string.tmdb_top_rated_base_url));
    }

    public static URL createUrlForPopularMovies(MainActivity mainActivity) {
        return createUrl(mainActivity,mainActivity.getString(R.string.tmdb_popular_movies_base_url));
    }



}

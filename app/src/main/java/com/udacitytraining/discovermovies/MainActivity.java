package com.udacitytraining.discovermovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.udacitytraining.discovermovies.adapters.GridViewAdapter;
import com.udacitytraining.discovermovies.tasks.TMDBAsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, AdapterView.OnItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener{


    GridViewAdapter adapter;
    private Bundle requestUrlBundle;
    private int tmdb_loader_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (!DiscoverMoviesUtil.isInternetConnected(this)) {
            Intent noInternetIntent = new Intent(this, UnconnectedInternetActivity.class);
            startActivity(noInternetIntent);

        }

        PreferenceManager.setDefaultValues(this,R.xml.preference,false);
        GridView  gridView = (GridView) findViewById(R.id.gridViewId);
        adapter = new GridViewAdapter(this,R.layout.movie_details);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        URL queryUrl = DiscoverMoviesUtil.createUrlFromSortPreference(this);

        requestUrlBundle = new Bundle();
        requestUrlBundle.putString(getString(R.string.query_url_bundle_key),queryUrl.toString());

        tmdb_loader_id = Integer.parseInt(getString(R.string.tmdb_image_loader_id));

        if (getSupportLoaderManager().getLoader(tmdb_loader_id) == null) {
            getSupportLoaderManager().initLoader(Integer.parseInt(getString(R.string.tmdb_image_loader_id)), requestUrlBundle, this).forceLoad();
        }else {
            getSupportLoaderManager().initLoader(Integer.parseInt(getString(R.string.tmdb_image_loader_id)), requestUrlBundle, this);
        }


        //register preference listener
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();

        //check internet connectivity
        if (!DiscoverMoviesUtil.isInternetConnected(this)) {
            Intent noInternetIntent = new Intent(this, UnconnectedInternetActivity.class);
            startActivity(noInternetIntent);

        }




    }

    private void retrieveMovies(URL inputQryUrl) {



        requestUrlBundle.putString(getString(R.string.query_url_bundle_key),inputQryUrl.toString());

        Loader<String> loader =  getSupportLoaderManager().getLoader(Integer.parseInt(getString(R.string.tmdb_image_loader_id)));
        if (loader ==  null) {
            getSupportLoaderManager().initLoader(Integer.parseInt(getString(R.string.tmdb_image_loader_id)), requestUrlBundle, this);
        }else {
            getSupportLoaderManager().restartLoader(Integer.parseInt(getString(R.string.tmdb_image_loader_id)), requestUrlBundle, this).forceLoad();
        }





    }


    @Override
    public Loader<String> onCreateLoader(int id,  Bundle args) {
        if (args == null) {
            return null;
        }

        return new TMDBAsyncTaskLoader(this, args.getString(getString(R.string.query_url_bundle_key)));

    }

    @Override
    public void onLoadFinished(Loader<String> loader, String responseBody) {
        try {
            if (responseBody != null && !responseBody.isEmpty()) {

                adapter.clear();

                JSONObject jResponseObject = new JSONObject(responseBody);


                JSONArray  jArrayResults = jResponseObject.getJSONArray(getString(R.string.results_key));

                for (int i = 0; i < jArrayResults.length(); i++) {
                    JSONObject JResultsObject = jArrayResults.getJSONObject(i);
                    String picPath = JResultsObject.getString(getString(R.string.poster_path));
                    String title = JResultsObject.getString(getString(R.string.poster_title));
                    String picUri = getString(R.string.images_base_url) + picPath;
                    String releaseDate = JResultsObject.getString(getString(R.string.release_date));
                    String overview = JResultsObject.getString(getString(R.string.overview));
                    String rating = JResultsObject.getString(getString(R.string.rating));
                    adapter.add(new Movie(title,picUri,releaseDate,overview,rating));



                }

            }
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this,"data issue",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        Toast.makeText(this,"need to reset loader", Toast.LENGTH_LONG).show();
        Log.d("****onLoaderReset", "need to reset loader");
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.sortMenuId) {

            Intent userPreferenceIntent = new Intent(this,UserPreferencesActivity.class);
            startActivity(userPreferenceIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        new MenuInflater(this).inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try {
            Intent movieInfoIntent = new Intent(MainActivity.this, MovieDetailsActivity.class);
            Movie movieItem = adapter.getItem(position);

            Bundle movieInfoBundle = new Bundle();

            String title = movieItem.getTitle();
            String imagePath = movieItem.getPosterPath();
            String rating = movieItem.getRating();
            String releaseDate = movieItem.getReleaseDate();
            String plot = movieItem.getOverview();

            movieInfoBundle.putString(getString(R.string.poster_title), title);
            movieInfoBundle.putString(getString(R.string.poster_path), imagePath);
            movieInfoBundle.putString(getString(R.string.overview), plot);
            movieInfoBundle.putString(getString(R.string.rating), rating);
            movieInfoBundle.putString(getString(R.string.release_date), releaseDate);


            movieInfoIntent.putExtras(movieInfoBundle);

            startActivity(movieInfoIntent);

        }catch (Exception e) {
            Toast.makeText(this,"unable to retrieve movie details",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        // retrieve movies when preference changes
        if (key.equalsIgnoreCase(getString(R.string.sortPreferenceKey))) {
            retrieveMovies(DiscoverMoviesUtil.createUrlFromSortPreference(this, sharedPreferences, key));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //unregister preference listener
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}

package com.udacitytraining.discovermovies;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.udacitytraining.discovermovies.adapters.GridViewAdapter;
import com.udacitytraining.discovermovies.fragments.SortMenuFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    GridViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        URL popMoviesUrl = DiscoverMoviesUtil.createUrlForPopularMovies(this);
        URL topRatedUrl = DiscoverMoviesUtil.createUrlForRatedMovies(this);

        HttpRequestTask httpRequestTask = new HttpRequestTask();

        Request req = new Request.Builder().url(popMoviesUrl).build();

        httpRequestTask.execute(req);



        GridView  gridView = (GridView) findViewById(R.id.gridViewId);
        adapter = new GridViewAdapter(this,R.layout.movie_details,R.id.movieTitleViewId);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent movieInfoIntent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                Movie movieItem = adapter.getItem(position);

                Bundle movieInfoBundle = new Bundle();

                String title = movieItem.getTitle();
                String imagePath = movieItem.getPosterPath();
                String rating = movieItem.getRating();
                String releaseDate = movieItem.getRelease_date();
                String plot = movieItem.getOverview();

                movieInfoBundle.putString(getString(R.string.poster_title), title);
                movieInfoBundle.putString(getString(R.string.poster_path), imagePath);
                movieInfoBundle.putString(getString(R.string.overview),plot);
                movieInfoBundle.putString(getString(R.string.rating),rating);
                movieInfoBundle.putString(getString(R.string.release_date),releaseDate);


                movieInfoIntent.putExtras(movieInfoBundle);

                startActivity(movieInfoIntent);
            }
        });

    }





    public class HttpRequestTask extends AsyncTask<Request,Void, String> {

        private  String resBody;

        private final String LOG_TAG = this.getClass().getCanonicalName();


        @Override
        protected String doInBackground(Request... params) {
            resBody = "";
            Response  res = null;
            OkHttpClient client = new OkHttpClient();

            for (Request req : params) {
                try {
                    res = client.newCall(req).execute();
                    resBody = res.body().string();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this,"Network issue", Toast.LENGTH_LONG).show();
                }finally {
                    if (res != null) {
                        res.close();
                    }
                }
            }


            return resBody;
        }


        @Override
        protected void onPostExecute(String responseBody) {


            try {
                if (responseBody != null && !responseBody.isEmpty()) {

                    JSONObject jResponseObject = new JSONObject(responseBody);


                    JSONArray  jArrayResults = jResponseObject.getJSONArray(getString(R.string.results_key));
                    Log.e(LOG_TAG, jArrayResults.toString());
                    for (int i = 0; i < jArrayResults.length(); i++) {
                        JSONObject JResultsObject = jArrayResults.getJSONObject(i);
                        String picPath = JResultsObject.getString(getString(R.string.poster_path));
                        String title = JResultsObject.getString(getString(R.string.poster_title));
                        String picUri = getString(R.string.images_base_url) + picPath;
                        String releaseDate = JResultsObject.getString(getString(R.string.release_date));
                        String overview = JResultsObject.getString(getString(R.string.overview));
                        String rating = JResultsObject.getString(getString(R.string.rating));
                        adapter.add(new Movie(title,picUri,releaseDate,overview,rating));

                        Log.e(LOG_TAG,"new picpath: " + picUri);

                    }

                }
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this,"data issue",Toast.LENGTH_SHORT).show();
            }


        }


    }

    @Override
    protected void onResume() {
        super.onResume();
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
}

package com.udacitytraining.discovermovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.udacitytraining.discovermovies.adapters.GridViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateMovieList();

        URL popMoviesUrl = DiscoverMoviesUtil.createUrlForPopularMovies(this);
        URL topRatedUrl = DiscoverMoviesUtil.createUrlForRatedMovies(this);

        HttpRequestTask httpRequestTask = new HttpRequestTask();

        Request req = new Request.Builder().url(popMoviesUrl).build();

        httpRequestTask.execute(req);



        GridView  gridView = (GridView) findViewById(R.id.gridViewId);
        GridViewAdapter adapter = new GridViewAdapter(this,R.layout.movie_details,R.id.movieTitleViewId,movieList);
        gridView.setAdapter(adapter);

    }

    private void populateMovieList() {
        movieList = new ArrayList<>();

        int[] picsArray = getPicsArray();


        for (int i = 0; i < 8; i++) {
            Movie movie = new Movie("Title"+i,picsArray[i]);

            movieList.add(movie);
        }
    }

    private int[] getPicsArray() {

        int[] picArray = new int[8];
        picArray[0] = R.drawable.sample_0;
        picArray[1] = R.drawable.sample_1;
        picArray[2] = R.drawable.sample_2;
        picArray[3] = R.drawable.sample_3;
        picArray[4] = R.drawable.sample_4;
        picArray[5] = R.drawable.sample_5;
        picArray[6] = R.drawable.sample_6;
        picArray[7] = R.drawable.sample_7;


        return picArray;


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
                    Toast.makeText(MainActivity.this,"Network issue", Toast.LENGTH_LONG);
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

                    JSONObject jsonObject = new JSONObject(responseBody);
                    Log.e(LOG_TAG, jsonObject.toString());
                }
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this,"data issue",Toast.LENGTH_SHORT);
            }


        }

        public  String getResponse() {
            return resBody.toString();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

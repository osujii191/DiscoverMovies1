package com.udacitytraining.discovermovies.tasks;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by osuji_000 on 6/11/2017.
 */

public class TMDBAsyncTaskLoader extends AsyncTaskLoader<String> {

    String queryUrl;

    public TMDBAsyncTaskLoader(Context context, String qry) {
        super(context);
        this.queryUrl = qry;
    }

    @Override
    public String loadInBackground() {

        if (null == queryUrl) {
            return null;
        }

        String resBody = "";
        Response res = null;
        OkHttpClient client = new OkHttpClient();

        Request req = new Request.Builder().url(queryUrl).build();
        try {
            res = client.newCall(req).execute();
            resBody = res.body().string();
        } catch (IOException e) {

        }finally {
            if (res != null) {
                res.close();
            }
        }



        return resBody;
    }
}

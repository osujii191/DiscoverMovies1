package com.udacitytraining.discovermovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by osuji_000 on 5/6/2017.
 */

public class MovieDetailsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_extra_info);

        setMovieInfo();
    }

    private void setMovieInfo() {
        Bundle movieInfoBundle = getIntent().getExtras();
        if (movieInfoBundle != null) {

            //set title
            TextView movieTitle = (TextView) findViewById(R.id.titleInfoViewId);
            movieTitle.setText(movieInfoBundle.getString(getString(R.string.poster_title)));

            //set image
            ImageView movieThumbnail = (ImageView) findViewById(R.id.thumbnailViewId);
            String posterUri = movieInfoBundle.getString(getString(R.string.poster_path));
            Picasso.with(this).load(posterUri).into(movieThumbnail);

            //set overview
            TextView  overview = (TextView) findViewById(R.id.plotInfoViewId);
            overview.setText(movieInfoBundle.getString(getString(R.string.overview)));

            //set release date
            TextView  releaseDate = (TextView) findViewById(R.id.releaseDateInfoViewId);
            releaseDate.setText(movieInfoBundle.getString(getString(R.string.release_date)));

            //set rating
            TextView  rating = (TextView) findViewById(R.id.ratingInfoViewId);
            rating.setText(movieInfoBundle.getString(getString(R.string.rating)));

        }





    }


}

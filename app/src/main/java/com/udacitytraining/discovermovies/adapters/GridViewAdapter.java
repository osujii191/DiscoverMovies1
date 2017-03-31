package com.udacitytraining.discovermovies.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.udacitytraining.discovermovies.Movie;
import com.udacitytraining.discovermovies.R;

import java.util.List;

/**
 * Created by osuji_000 on 3/23/2017.
 */

public class GridViewAdapter extends ArrayAdapter<Movie> {


    public GridViewAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public GridViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Movie> objects) {
        super(context, resource, objects);
    }

    public GridViewAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<Movie> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_details,parent,false);

        }

        Movie movie = (Movie) getItem(position);

        ImageView movieImage = (ImageView) convertView.findViewById(R.id.movieImageViewId);
        TextView movieText = (TextView) convertView.findViewById(R.id.movieTitleViewId);

        movieText.setText(movie.getTitle());
        //Picasso.with(parent.getContext()).load(movie.getImageNum()).into(movieImage);
        Glide.with(parent.getContext()).load(movie.getImageNum()).asBitmap().fitCenter().into(movieImage);




        return convertView;
    }
}

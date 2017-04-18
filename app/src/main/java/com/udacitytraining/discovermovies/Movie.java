package com.udacitytraining.discovermovies;

/**
 * Created by osuji_000 on 3/23/2017.
 */

public class Movie {

    private int imageNum;
    private String posterPath;


    private String title;

    public Movie(String title, int i) {
        this.title = title;
        this.imageNum = i;

    }

    public Movie(String title, String posterPath) {
        this.title = title;
        this.posterPath = posterPath;
        this.imageNum = -1;

    }


    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }



    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }





}

package com.udacitytraining.discovermovies;

/**
 * Created by osuji_000 on 3/23/2017.
 */

public class Movie {

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    private int imageNum;


    private String title;

    public Movie(String title, int i) {
        this.title = title;
        this.imageNum = i;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }





}

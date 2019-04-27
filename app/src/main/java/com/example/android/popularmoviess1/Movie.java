package com.example.android.popularmoviess1;

import java.io.Serializable;

public class Movie implements Serializable {
    private final float voteAverage;
    private final String title,posterPath,overview,releaseDate;

    public Movie(String title,String posterPath,String overview,String releaseDate,float voteAverage){
        this.title=title;
        this.posterPath=posterPath;
        this.overview=overview;
        this.releaseDate=releaseDate;
        this.voteAverage=voteAverage;
    }

    public String getTitle(){return title;}

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}

package com.example.android.popularmoviess1;

import org.json.JSONObject;

public class MoviesJSONUtils {

    private static final String VOTE_AVERAGE="vote_average";
    private static final String RELEASE_DATE="release_date";
    private static final String TITLE="title";
    private static final String POSTER_PATH="poster_path";
    private static final String OVERVIEW="overview";
    private static final String RESULTS="results";

    public static Movie[] getJsonResults(String inputString){
        JSONObject movieJson ;
        try {
        movieJson = new JSONObject(inputString);
        int resultsCount = movieJson.getJSONArray(RESULTS).length();
        Movie[] movieArray = new Movie[resultsCount];
        for(int i=0;i<resultsCount;i++){
            String title = movieJson.getJSONArray(RESULTS).getJSONObject(i).get(TITLE).toString();
            String posterPath = movieJson.getJSONArray(RESULTS).getJSONObject(i).get(POSTER_PATH).toString();
            String overview = movieJson.getJSONArray(RESULTS).getJSONObject(i).get(OVERVIEW).toString();
            String releaseDate = movieJson.getJSONArray(RESULTS).getJSONObject(i).get(RELEASE_DATE).toString();
            float voteAverage = Float.parseFloat(movieJson.getJSONArray(RESULTS).getJSONObject(i).get(VOTE_AVERAGE).toString());

            Movie movie =new Movie(title,posterPath,overview,releaseDate,voteAverage);
            movieArray[i]=movie;
        }
        return movieArray;
        }
        catch (Exception ex){
        return null;
        }
    }
}

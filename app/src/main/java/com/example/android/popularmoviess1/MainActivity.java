package com.example.android.popularmoviess1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.popularmoviess1.datasource.MovieData;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener{

    private final String mostPopularMovies = "http://api.themoviedb.org/3/movie/popular";
    private static ProgressBar mProgressBar;
    private int movieSortOrder = 2;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.pb_loading_indicator);
        moviesAdapter = new MoviesAdapter(this);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(moviesAdapter);
        mRecyclerView.setHasFixedSize(true);
        new FetchMoviesData().execute(mostPopularMovies);
    }

    private class FetchMoviesData extends AsyncTask<String,Void,Movie[]>{

        @Override
        protected void onPreExecute(){
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... params){
            if(params.length==0) return null;
            if(!isConnectedToInternet()){
                this.cancel(true);
            }

            String sortType = params[0];
            URL moviesURL = MovieData.buildURL(sortType);
            try{
                String response = MovieData.getResponseFromHttpUrl(moviesURL);
                return MoviesJSONUtils.getJsonResults(response);
            }
            catch (Exception ex){
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] params){
            mProgressBar.setVisibility(View.GONE);
            moviesAdapter.setMoviesData(params);
        }

        @Override
        protected void onCancelled(){
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"Poor internet connection!!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int selectedId = item.getItemId();
        switch (selectedId){
            case R.id.sort_top_rated :
                sortMoviesList(1);
                break;

            case R.id.sort_popularity :
                sortMoviesList(2);
                break;
        }
        return true;
    }

    private void sortMoviesList(int sortOrder){
        String highestRatedMovies = "http://api.themoviedb.org/3/movie/top_rated";
        if(movieSortOrder!=sortOrder){
            if(sortOrder==1){new FetchMoviesData().execute(highestRatedMovies);}
            else{new FetchMoviesData().execute(mostPopularMovies);}
            movieSortOrder = sortOrder;
        }
        else{
            Toast.makeText(this,"Movies already sorted in that order",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onListItemClick(Movie selectedMovie){
        Intent detailIntent = new Intent(this,MovieDetailActivity.class);
        detailIntent.putExtra("MovieData",selectedMovie);
        startActivity(detailIntent);
    }

    private boolean isConnectedToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = null;
        if(connectivityManager!=null) {net = connectivityManager.getActiveNetworkInfo();}
        return net!=null && net.isConnected();
    }
}

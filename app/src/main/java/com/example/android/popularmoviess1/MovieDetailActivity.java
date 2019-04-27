package com.example.android.popularmoviess1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //initialize variables
        ImageView moviePosterImageView = findViewById(R.id.movie_image_view);
        TextView titleTextView = findViewById(R.id.movie_title_tv);
        TextView voteAverageTextView = findViewById(R.id.movie_vote_tv);
        TextView overviewTextView = findViewById(R.id.movie_overview_tv);
        TextView releaseDateTextView = findViewById(R.id.movie_release_tv);

        Movie movie = (Movie) getIntent().getSerializableExtra("MovieData");

        String imagePathPartial = "http://image.tmdb.org/t/p/w185/";
        String posterPath = imagePathPartial.concat(movie.getPosterPath());
        Picasso.get().load(posterPath).into(moviePosterImageView);

        /*Picasso.get().load(imagePathPartial.concat(posterPath)).resize(550,1000)
                .centerCrop()
                .into(moviePosterImageView);*/
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(moviePosterImageView);

        titleTextView.setText(movie.getTitle()+"\n\n\n");
        voteAverageTextView.setText(movie.getVoteAverage()+"\n\n\n");
        overviewTextView.setText(movie.getOverview()+"\n\n\n");
        releaseDateTextView.setText(movie.getReleaseDate()+"\n\n");
    }
}

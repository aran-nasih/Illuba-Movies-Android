package com.example.android.illubamoviesandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    private TextView title;
    private TextView vote;
    private TextView date;
    private ImageView poster;
    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        this.title = (TextView) findViewById(R.id.movie_detail_title);
        this.vote = (TextView) findViewById(R.id.movie_detail_vote);
        this.date = (TextView) findViewById(R.id.movie_detail_date);
        this.desc = (TextView) findViewById(R.id.movie_detail_desc);
        this.poster = (ImageView) findViewById(R.id.movie_detail_poster);

        Intent intent = getIntent();

        Movie movie = (Movie) intent.getSerializableExtra("movie");

        this.title.setText(movie.getTitle());
        this.vote.setText(movie.getVote() + "");
        this.date.setText(movie.getDate());
        this.desc.setText(movie.getDescription());
        Picasso.with(this)
                .load(movie.getPoster())
                .into(this.poster);

        setTitle(movie.getTitle());
    }
}

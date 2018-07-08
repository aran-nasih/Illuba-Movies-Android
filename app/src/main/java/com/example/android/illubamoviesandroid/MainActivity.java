package com.example.android.illubamoviesandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public String APIKey = "9e1045f266bd24cc57c3983d679b4f30";
    public String movieApiString = "https://api.themoviedb.org/3/movie/";

    private static final int NUM_GRID_COLUMNS = 2;
    public RecyclerView moviesRV;
    public MovieListAdapter movieListAdapter;

    public boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.moviesRV = (RecyclerView) findViewById(R.id.movies_rv);

        makeApiRequest(1);
    }

    void makeApiRequest(int sort) {
        if (sort == 1)
            this.movieApiString = this.movieApiString + "popular?api_key=" + this.APIKey + "&language=en-US&page=1";
        else
            this.movieApiString = this.movieApiString + "top_rated?api_key=" + this.APIKey + "&language=en-US&page=1";
        new MovieQueryTask().execute(this.movieApiString);
    }

    public class MovieQueryTask extends AsyncTask<String, List<Movie>, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            Utils utils = new Utils();
            List<Movie> movieList = utils.fetchData(strings[0]);
            return movieList;
        }

        protected void onPostExecute(List<Movie> movieList) {
            setMovieAdapter(movieList);
        }
    }

    public void setMovieAdapter(List<Movie> movieList) {
        if (!flag) {
            makeApiRequest(1);
            this.flag = true;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUM_GRID_COLUMNS);
        this.moviesRV.setLayoutManager(gridLayoutManager);
//        this.moviesRV.setNestedScrollingEnabled(false);
        this.movieListAdapter = new MovieListAdapter(this, movieList);
        this.moviesRV.setAdapter(this.movieListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.most_popular) {
            this.movieApiString = "https://api.themoviedb.org/3/movie/";
            makeApiRequest(1);
            return true;
        } else if (id == R.id.highest_rated) {
            this.movieApiString = "https://api.themoviedb.org/3/movie/";
            makeApiRequest(2);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

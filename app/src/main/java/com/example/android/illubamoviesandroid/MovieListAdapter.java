package com.example.android.illubamoviesandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private static final String TAG = "MovieListAdapter";
    private static final int NUM_GRID_COLUMNS = 3;

    private List<Movie> listOfMovie;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView moviePosetr;

        public ViewHolder(View itemView) {
            super(itemView);
            moviePosetr = (ImageView) itemView.findViewById(R.id.movie_post_image);

            int gridWidth = context.getResources().getDisplayMetrics().widthPixels;
            int imgWidth = gridWidth;

            moviePosetr.setMaxHeight(imgWidth);
            moviePosetr.setMaxWidth(imgWidth);
        }
    }

    public MovieListAdapter(Context context, List<Movie> listOfMovie) {
        this.context = context;
        this.listOfMovie = listOfMovie;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.layout_movie_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context)
                .load(listOfMovie.get(position).getPoster())
                .into(holder.moviePosetr);

        final int positionF = position;
        holder.moviePosetr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetail.class);
                intent.putExtra("movie", listOfMovie.get(positionF));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listOfMovie.size();
    }


}

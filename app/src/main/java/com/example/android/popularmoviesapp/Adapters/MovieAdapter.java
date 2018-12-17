package com.example.android.popularmoviesapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View.OnClickListener;

import com.example.android.popularmoviesapp.Model.Movie;
import com.example.android.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String TAG = MovieAdapter.class.getSimpleName();
    private final MovieOnClickHandler movieOnClickHandler;
    private List<Movie> movies;

    public MovieAdapter(List<Movie> movies, MovieOnClickHandler clickHandler) {
        this.movies = movies;
        this.movieOnClickHandler = clickHandler;
    }

    public interface MovieOnClickHandler {
        void onClick(int movie_position);
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_image_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = movies.get(i);
        Picasso.get()
                .load(movie.getPosterImageUrl())
                .placeholder(R.drawable.poster_image_loading)
                .error(R.drawable.poster_image_error)
                .resize(185, 275)
                .centerCrop()
                .into(viewHolder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        @BindView(R.id.iv_movie_poster)
        ImageView moviePoster;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            movieOnClickHandler.onClick(getAdapterPosition());
        }
    }
}

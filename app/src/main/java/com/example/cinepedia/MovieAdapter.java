package com.example.cinepedia;

import static com.example.cinepedia.MainActivity.allMovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.cinepedia.MovieDetailActivity;
import com.example.cinepedia.R;
import com.example.cinepedia.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private Context context;
    private RequestOptions imageOptions;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
        setupImageOptions();
    }

    private void setupImageOptions() {
        imageOptions = new RequestOptions()
//                .placeholder(R.drawable.ic_image_placeholder)
//                .error(R.drawable.ic_image_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        // Apply additional styling to match material design
        view.setElevation(12f); // Match login card elevation
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        // Apply text styling to match login page
        holder.movieTitle.setTextSize(18);
        holder.movieTitle.setTextColor(context.getResources().getColor(R.color.text_primary));

        holder.movieDescription.setTextSize(14);
        holder.movieDescription.setTextColor(context.getResources().getColor(R.color.text_secondary));

        holder.movieTitle.setText(movie.getTitle());
        holder.movieDescription.setText(movie.getDescription());
        holder.movieRating.setText(String.format("%.1f", movie.getRating()));

        // Enhanced image loading with Glide
        Glide.with(context)
                .load(movie.getPosterUrl())
                .apply(imageOptions)
                .thumbnail(0.1f)
                .into(holder.moviePoster);

        // Apply ripple effect on click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("movie_id", movie.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void updateMovies(List<Movie> newMovies) {
        this.movies = newMovies;
        notifyDataSetChanged();
    }
    public void filterByCategory(String category) {
        if (category.equals("All")) {
            updateMovies(allMovies);
        } else {
            List<Movie> filteredMovies = allMovies.stream()
                    .filter(movie -> movie.getCategory().equals(category))
                    .collect(Collectors.toList());
            updateMovies(filteredMovies);
        }
    }
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieTitle;
        TextView movieDescription;
        TextView movieRating;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieDescription = itemView.findViewById(R.id.movieDescription);
            movieRating = itemView.findViewById(R.id.movieRating);
        }
    }
}
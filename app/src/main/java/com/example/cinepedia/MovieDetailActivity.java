package com.example.cinepedia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinepedia.CastAdapter;
import com.example.cinepedia.CastMember;
import com.example.cinepedia.Movie;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView movieBackdrop;
    private TextView movieTitle;
    private TextView movieRating;
    private TextView movieYear;
    private TextView movieDescription;
    private RecyclerView castRecyclerView;
    private MaterialButton bookButton;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Initialize views
        movieBackdrop = findViewById(R.id.movieBackdrop);
        movieTitle = findViewById(R.id.movieTitle);
        movieRating = findViewById(R.id.movieRating);
        movieYear = findViewById(R.id.movieYear);
        movieDescription = findViewById(R.id.movieDescription);
        castRecyclerView = findViewById(R.id.castRecyclerView);
        bookButton = findViewById(R.id.bookButton);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Get movie data from intent
        int movieId = getIntent().getIntExtra("movie_id", -1);
        // TODO: Replace with actual API call to get movie details
        loadMovieDetails(movieId);

        // Set up cast RecyclerView
        castRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        // Set up booking button
        bookButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SeatSelectionActivity.class);
            intent.putExtra("movie_title", movie.getTitle());
            startActivity(intent);
        });
    }

    private void loadMovieDetails(int movieId) {
        List<Movie> allMovies = MainActivity.getAllMovies();

        // Find the movie with matching ID
        for (Movie m : allMovies) {
            if (m.getId() == movieId) {
                movie = m;
                break;
            }
        }

        if (movie == null) {
            finish(); // Close activity if movie not found
            return;
        }
        TextView titleTextView = findViewById(R.id.movieTitle);
        titleTextView.setText(movie.getTitle());

        //Similar updates for other UI elements using movie object data.

        //Update cast list
        RecyclerView castRecyclerView = findViewById(R.id.castRecyclerView);
//        CastAdapter castAdapter = new CastAdapter(movie.getCast());
//        castRecyclerView.setAdapter(castAdapter);
        // TODO: Replace with actual API call
        // For now, we'll use sample data
//        List<CastMember> sampleCastMembers = new ArrayList<>();
//        sampleCastMembers.add(new CastMember(1, "Robert Downey Jr.", "Lead Actor",
//                "https://example.com/photo1.jpg"));
//        sampleCastMembers.add(new CastMember(2, "Scarlett Johansson", "Supporting Actor",
//                "https://example.com/photo2.jpg"));
//        sampleCastMembers.add(new CastMember(3, "Chris Evans", "Supporting Actor",
//                "https://example.com/photo3.jpg"));

//        movie = new Movie(
//                movieId,
//                "The Dark Knight",
//                "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, " +
//                        "Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
//                "https://example.com/dark_knight_poster.jpg",
//                9.0,
//                2008,
//                sampleCastMembers,
//                "https://www.bookmyshow.com/movies/the-dark-knight"
//        );

        // Update UI with movie details
        Glide.with(this)
                .load(movie.getPosterUrl())
                .centerCrop()
                .into(movieBackdrop);

        movieTitle.setText(movie.getTitle());
        movieRating.setText(String.format("%.1f/10", movie.getRating()));
        movieYear.setText(String.valueOf(movie.getYear()));
        movieDescription.setText(movie.getDescription());

        // Set up cast adapter
//        CastAdapter castAdapter = new CastAdapter(this, movie.getCast());
//        castRecyclerView.setAdapter(castAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

//    private List<CastMember> getSampleCastMembers() {
//        List<CastMember> cast = new ArrayList<>();
//        cast.add(new CastMember(1, "Christian Bale", "Bruce Wayne / Batman",
//                "https://example.com/bale.jpg"));
//        cast.add(new CastMember(2, "Heath Ledger", "Joker",
//                "https://example.com/ledger.jpg"));
//        cast.add(new CastMember(3, "Aaron Eckhart", "Harvey Dent",
//                "https://example.com/eckhart.jpg"));
//        cast.add(new CastMember(4, "Michael Caine", "Alfred",
//                "https://example.com/caine.jpg"));
//        return cast;
//    }
//}
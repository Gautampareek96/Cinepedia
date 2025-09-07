package com.example.cinepedia;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private RecyclerView moviesRecyclerView;
    private MovieAdapter movieAdapter;
    static List<Movie> allMovies;
    private List<Movie> filteredMovies;
    private TextInputEditText searchInput;
    private String currentCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesRecyclerView = findViewById(R.id.moviesRecyclerView);
        searchInput = findViewById(R.id.searchInput);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ChipGroup categoryChipGroup = findViewById(R.id.categoryChipGroup);
        categoryChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chipAll) currentCategory = "All";
            else if (checkedId == R.id.chipComedy) currentCategory = "Comedy";
            else if (checkedId == R.id.chipDrama) currentCategory = "Drama";
            else if (checkedId == R.id.chipThriller) currentCategory = "Thriller";

            filterMovies(searchInput.getText() != null ? searchInput.getText().toString() : "");
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMovies(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        loadSampleMovies();
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.navigation_home); // Default

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return true;
        });
    }

    public static List<Movie> getAllMovies() {
        return allMovies;
    }

    private void loadSampleMovies() {
        allMovies = new ArrayList<>();

        List<CastMember> darkKnightCast = new ArrayList<>();
        darkKnightCast.add(new CastMember(1, "Christian Bale", "Batman", "https://image.tmdb.org/t/p/w185/8qBylBsQf4llkGrWR3qAsOtOU8O.jpg"));
        darkKnightCast.add(new CastMember(2, "Heath Ledger", "Joker", "https://image.tmdb.org/t/p/w185/5M7oN3sznp99hWYQ9sX0xheswWX.jpg"));

        List<CastMember> inceptionCast = new ArrayList<>();
        inceptionCast.add(new CastMember(4, "Leonardo DiCaprio", "Cobb", "https://image.tmdb.org/t/p/w185/wo2hJpn04vbtmh0B9utCFdsQhxM.jpg"));

        allMovies.add(new Movie(1, "The Dark Knight", "Joker brings chaos to Gotham.", "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg", 9.0, 2008, darkKnightCast, "https://bookmyshow.com/dark-knight", "Thriller"));
        allMovies.add(new Movie(2, "Inception", "Dream within a dream.", "https://image.tmdb.org/t/p/w500/8IB2e4r4oVhHnANbnm7O3Tj6tF8.jpg", 8.8, 2010, inceptionCast, "https://bookmyshow.com/inception", "Thriller"));
        allMovies.add(new Movie(3, "The Shawshank Redemption", "Hope and prison life.", "https://image.tmdb.org/t/p/w500/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg", 9.3, 1994, inceptionCast, "https://bookmyshow.com/shawshank", "Drama"));
        allMovies.add(new Movie(4, "Pulp Fiction", "Mob hitmen & stories.", "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg", 8.9, 1994, inceptionCast, "https://bookmyshow.com/pulp-fiction", "Comedy"));
        allMovies.add(new Movie(5, "The Matrix", "Reality is a simulation.", "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg", 8.7, 1999, inceptionCast, "https://bookmyshow.com/matrix", "Drama"));

        movieAdapter = new MovieAdapter(this, allMovies);
        moviesRecyclerView.setAdapter(movieAdapter);
    }

    private void filterMovies(String query) {
        filteredMovies = new ArrayList<>(allMovies);

        if (!currentCategory.equals("All")) {
            filteredMovies = filteredMovies.stream()
                    .filter(movie -> movie.getCategory().equalsIgnoreCase(currentCategory))
                    .collect(Collectors.toList());
        }

        if (!query.isEmpty()) {
            filteredMovies = filteredMovies.stream()
                    .filter(movie -> movie.getTitle().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }

        movieAdapter.updateMovies(filteredMovies);
    }
}
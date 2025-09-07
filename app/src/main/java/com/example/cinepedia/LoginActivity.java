package com.example.cinepedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText usernameInput, passwordInput;
    private MaterialButton loginButton, registerButton;
    private Spinner languageSpinner;
    private DatabaseHelper dbHelper; // Database instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load saved language
        loadLocale();

        setContentView(R.layout.activity_login);

        // Initialize views
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        languageSpinner = findViewById(R.id.languageSpinner);
        dbHelper = new DatabaseHelper(this); // Initialize SQLite Database

        // Setup language dropdown (Spinner)
        setupLanguageSpinner();

        // Set click listeners
        loginButton.setOnClickListener(v -> handleLogin());
        registerButton.setOnClickListener(v -> navigateToRegister());
    }

    private void setupLanguageSpinner() {
        String[] languages = {"English", "French", "Hindi"};
        String[] languageCodes = {"en", "fr", "hi"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);
        languageSpinner.setAdapter(adapter);

        // Get saved language and set the spinner position
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String savedLang = prefs.getString("App_Lang", "en");

        int index = 0;
        for (int i = 0; i < languageCodes.length; i++) {
            if (languageCodes[i].equals(savedLang)) {
                index = i;
                break;
            }
        }
        languageSpinner.setSelection(index);

        // Change language when an item is selected
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLangCode = languageCodes[position];
                if (!selectedLangCode.equals(savedLang)) {
                    setLocale(selectedLangCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setLocale(String langCode) {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String currentLang = prefs.getString("App_Lang", "en"); // Default: English

        // Only update if the selected language is different
        if (!currentLang.equals(langCode)) {
            Locale locale = new Locale(langCode);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());

            // Save new language preference
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("App_Lang", langCode);
            editor.apply();

            // Restart activity to apply changes
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(intent);
        }
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String langCode = prefs.getString("App_Lang", "en"); // Default: English
        setLocale(langCode);
    }

    private void handleLogin() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the user exists
        if (dbHelper.checkUser(username, password)) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

            // 🔐 Save login session using SharedPreferences
            SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", username); // Save the username
            editor.apply();

            // Navigate to home/main activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
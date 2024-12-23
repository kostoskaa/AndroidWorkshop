package com.example.workshop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.workshop.DBHandler;
import com.example.workshop.R;
import com.example.workshop.databinding.ActivityRatingBinding;

public class RatingActivity extends AppCompatActivity {

    private ActivityRatingBinding binding;
    private RatingBar ratingBar;
    private Button submitButton;
    private DBHandler dbHandler;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rating);
        binding = ActivityRatingBinding.inflate(getLayoutInflater());

        String ratedUserEmail = getIntent().getStringExtra("RATED_USER");

        dbHandler = new DBHandler(this);

        binding.submitButton.setOnClickListener(v -> {
            float rating = binding.ratingBar.getRating();

            if (rating > 0) {

                Log.d("RatingActivity", "Rated User Email: " + ratedUserEmail);

                boolean success = dbHandler.updateRating(ratedUserEmail, rating);

                if (success) {
                    Toast.makeText(this, "Rating updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to update rating.", Toast.LENGTH_SHORT).show();
                }

                finish();
            } else {
                Toast.makeText(this, "Please select a rating.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
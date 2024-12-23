package com.example.workshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.workshop.databinding.ActivityDriverDetailsBinding;

import java.util.Locale;

public class DriverDetails extends AppCompatActivity {

    DBHandler dbHandler;
    ActivityDriverDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_details);

        binding = ActivityDriverDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String driverEmail = getIntent().getStringExtra("RATED_USER_EMAIL");
        if (driverEmail == null || driverEmail.isEmpty()) {
            Toast.makeText(this, "Driver email not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        dbHandler = new DBHandler(this);
        Cursor cursor = dbHandler.getData(driverEmail);
        String email = "";

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(0);
            email = cursor.getString(1);
            float rating = cursor.getFloat(2);
            String carType = cursor.getString(3);
            String regPlate = cursor.getString(4);


            TextView driverName = findViewById(R.id.driverName);
            TextView driverRating = findViewById(R.id.driverRating);
            TextView driverCarType = findViewById(R.id.driverCar);
            TextView driverRegPlate = findViewById(R.id.driverRegistration);

            driverName.setText(name);
            driverRating.setText(String.format(Locale.getDefault(), "%.1f", rating));
            driverCarType.setText(carType);
            driverRegPlate.setText(regPlate);

            cursor.close();
        } else {
            Toast.makeText(this, "Driver not found", Toast.LENGTH_SHORT).show();
        }


        binding.rateDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverDetails.this,RatingActivity.class);
                intent.putExtra("RATED_USER", driverEmail);
                startActivity(intent);
            }
        });

//        String finalEmail = email;
        binding.ChooseRide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(DriverDetails.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
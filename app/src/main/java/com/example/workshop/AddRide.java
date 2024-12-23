package com.example.workshop;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.workshop.databinding.ActivityAddRideBinding;
import com.example.workshop.databinding.ActivityMainBinding;

public class AddRide extends AppCompatActivity {
    DBHandler dbHandler;
    ActivityAddRideBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_ride);
        dbHandler = new DBHandler(this);
        binding = ActivityAddRideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submitride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                String driverEmail = sharedPreferences.getString("DRIVER_EMAIL", null);

                String start1 = binding.startcity.getText().toString();
                String destionation1 = binding.destinationcity.getText().toString();

                String start = binding.start.getText().toString();
                String[] parts = start.split(",");
                Float lng = null, ltd = null, lng1 = null, ltd1 = null;
                if (parts.length == 2){
                    ltd = Float.parseFloat(parts[0].trim());
                    lng = Float.parseFloat(parts[1].trim());
                }
                else {
                    Toast.makeText(AddRide.this, "Please enter valid coordinates!", Toast.LENGTH_SHORT).show();
                }
                String destination = binding.destination.getText().toString();
                String[] parts1 = destination.split(",");
                if (parts1.length == 2){
                    ltd1 = Float.parseFloat(parts1[0].trim());
                    lng1 = Float.parseFloat(parts1[1].trim());
                }
                else {
                    Toast.makeText(AddRide.this, "Please enter valid coordinates!", Toast.LENGTH_SHORT).show();
                }
                String pricestr = binding.price.getText().toString();
                Integer price = Integer.parseInt(pricestr);
                String time = binding.time.getText().toString();
                String date = binding.date.getText().toString();
                if (start.isEmpty() || destination.isEmpty() || pricestr.isEmpty() || time.isEmpty() || date.isEmpty() || start1.isEmpty() || destionation1.isEmpty()){
                    Toast.makeText(AddRide.this, "Please fill everything!", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean insert = dbHandler.insertRide(driverEmail,null,price, time, date, ltd, lng, ltd1, lng1, start1, destionation1);
                    if (insert){
                        Toast.makeText(AddRide.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddRide.this, MapDriver.class);
                        intent.putExtra("DRIVER_EMAIL", driverEmail);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(AddRide.this, "Fail", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}
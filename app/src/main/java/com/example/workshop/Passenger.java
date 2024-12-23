package com.example.workshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workshop.databinding.ActivityDriverBinding;
import com.example.workshop.databinding.ActivityPassengerBinding;

import java.util.ArrayList;

public class Passenger extends AppCompatActivity {


    RecyclerView mRecyclerView;
    private ArrayList<String> email,start, dest;
    private ArrayList<Integer> price;

    MyAdapter2 mAdapter;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_passenger);
        dbHandler = new DBHandler(this);

        email = new ArrayList<>();
        start = new ArrayList<>();
        dest = new ArrayList<>();
        price = new ArrayList<>();

        mRecyclerView = findViewById(R.id.list);

        mAdapter = new MyAdapter2(this, email,start, dest, price);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        displayData();

    }
    private void displayData() {
        Cursor cursor = dbHandler.getDataForRides();
        if(cursor.getCount()==0){
            Toast.makeText(Passenger.this,"There are no rides to display.",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                email.add(cursor.getString(0));
                start.add(cursor.getString(1));
                dest.add(cursor.getString(2));
                price.add(cursor.getInt(3));
            }
        }
    }
}
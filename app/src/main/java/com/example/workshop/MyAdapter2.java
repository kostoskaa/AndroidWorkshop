package com.example.workshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {

    private Context mContext;
    private ArrayList email, start, dest, price;
    
    private ArrayList rating;

    public MyAdapter2(Context mContext, ArrayList email, ArrayList start, ArrayList dest, ArrayList price) {
        this.mContext = mContext;
        this.email = email;
        this.start = start;
        this.dest = dest;
        this.price = price;
    }

    @NonNull
    @Override
    public MyAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_my_adapter2, parent, false);
        return new MyAdapter2.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.ViewHolder holder, int position) {

        holder.email.setText(String.valueOf(email.get(position)));
        holder.start.setText(String.valueOf(start.get(position)));
        holder.dest.setText(String.valueOf(dest.get(position)));
        holder.price.setText(String.valueOf(price.get(position)));

        holder.chooseButton.setOnClickListener(v -> {
            String driverEmail = email.get(position).toString();
            Log.d("MyAdapter2", "Driver email: " + driverEmail);

            Intent intent = new Intent(mContext, DriverDetails.class);
            intent.putExtra("RATED_USER_EMAIL", driverEmail);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return email.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView email,start,dest,price;

        public Button chooseButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            email =  itemView.findViewById(R.id.DriverEmail);
            start = itemView.findViewById(R.id.DriverLocation);
            dest = itemView.findViewById(R.id.DriverDestination);
            price = itemView.findViewById(R.id.DriverPrice);
            chooseButton = itemView.findViewById(R.id.ChooseRide);
        }

    }
}
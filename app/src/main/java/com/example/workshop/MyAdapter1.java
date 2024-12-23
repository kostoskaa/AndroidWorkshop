package com.example.workshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.ViewHolder>  {

    private Context mContext;
    private ArrayList email, name;


    private ArrayList rating;

    public MyAdapter1(Context mContext, ArrayList name, ArrayList email, ArrayList rating) {
        this.mContext = mContext;
        this.name = name;
        this.email = email;
        this.rating = rating;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_my_adapter1, parent, false);
        return new MyAdapter1.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.email.setText(String.valueOf(email.get(position)));
        holder.name.setText(String.valueOf(name.get(position)));
        Float currentRating = (rating.get(position) != null) ? (Float) rating.get(position) : 0.0f;
        holder.rating.setText(String.format(Locale.getDefault(), "%.1f", currentRating));

        holder.rateButton.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, RatingActivity.class);
            intent.putExtra("USER_ROLE", "driver");
            intent.putExtra("RATED_USER_EMAIL", email.get(position).toString()); // Email of the passenger
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return email.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView email,name,rating;

        public Button rateButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            email =  itemView.findViewById(R.id.PassengerEmail);
            name = itemView.findViewById(R.id.PassengerName);
            rating = itemView.findViewById(R.id.PassengerRating);
            rateButton = itemView.findViewById(R.id.RatePassenger);
        }
    }
}
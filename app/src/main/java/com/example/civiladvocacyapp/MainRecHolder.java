package com.example.civiladvocacyapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainRecHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView name;

    public MainRecHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        name = itemView.findViewById(R.id.name);

    }
}

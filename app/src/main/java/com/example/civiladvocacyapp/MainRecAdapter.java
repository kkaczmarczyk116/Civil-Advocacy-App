package com.example.civiladvocacyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainRecAdapter extends RecyclerView.Adapter<MainRecHolder> {
    private final MainActivity main;
    private ArrayList<MainRec> mainRecList = new ArrayList<>();

    public MainRecAdapter(MainActivity main, ArrayList<MainRec> mainRecList) {
        this.main = main;
        this.mainRecList = mainRecList;
    }

    @NonNull
    @Override
    public MainRecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainlayout,parent,false);
        return new MainRecHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecHolder holder, int position) {
        MainRec mr = mainRecList.get(position);
        holder.name.setText(mr.getName());
        holder.title.setText(mr.getTitle());
    }

    @Override
    public int getItemCount() {
        return mainRecList.size();
    }
}

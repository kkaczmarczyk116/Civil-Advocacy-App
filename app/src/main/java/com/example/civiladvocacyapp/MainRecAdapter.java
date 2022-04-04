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
    private SelectListener listener;

    public MainRecAdapter(MainActivity main, ArrayList<MainRec> mainRecList, SelectListener listener) {
        this.main = main;
        this.mainRecList = mainRecList;
        this.listener = listener;
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
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(mainRecList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainRecList.size();
    }
}

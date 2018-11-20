package com.aksharam.greenpooling.helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aksharam.greenpooling.R;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class transactionsAdaptor extends RecyclerView.Adapter<transactionsAdaptor.MyViewHolder> {

    private List<transactions> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public transactionsAdaptor(List<transactions> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transactions_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        transactions movie = moviesList.get(position);
        holder.title.setText(movie.getmethod());
        holder.genre.setText(movie.getamount());
        holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
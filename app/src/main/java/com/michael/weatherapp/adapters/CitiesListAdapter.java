package com.michael.weatherapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michael.weatherapp.R;
import com.michael.weatherapp.models.CityWeatherModel;
import com.michael.weatherapp.ui.CityDetailActivity;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CitiesListAdapter extends RecyclerView.Adapter<CitiesListAdapter.CitiesListViewHolder> {
    private ArrayList<String> mCitiesArrayList;
    Context mContext;

    public CitiesListAdapter(ArrayList<String> citiesArrayList, Context context){
        this.mCitiesArrayList = citiesArrayList;
        this.mContext = context;

    }
    @NonNull
    @Override
    public CitiesListAdapter.CitiesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_city, parent, false);
        CitiesListViewHolder citiesListViewHolder = new CitiesListViewHolder(view);
        return citiesListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesListAdapter.CitiesListViewHolder holder, int position) {
        holder.bindCity(mCitiesArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCitiesArrayList.size();
    }

    @SuppressLint("NonConstantResourceId")
    public static class CitiesListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.singleCityNameTextView)
        TextView mSingleCityNameTextView;


        Context mContext;
        public CitiesListViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bindCity(String cityName) {
            System.out.println("City " + cityName);
            mSingleCityNameTextView.setText(cityName);

        }

        @Override
        public void onClick(View v) {
            String cityName = mSingleCityNameTextView.getText().toString();
            Intent intent = new Intent(mContext, CityDetailActivity.class);
            intent.putExtra("cityName", cityName);
            mContext.startActivity(intent);


        }
    }
}

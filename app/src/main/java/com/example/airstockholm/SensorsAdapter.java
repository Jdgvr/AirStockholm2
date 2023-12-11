package com.example.airstockholm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SensorsAdapter extends RecyclerView.Adapter<SensorsAdapter.ViewHolder> {

    private List<SensorsData> sensorDataList;

    public SensorsAdapter(List<SensorsData> sensorDataList) {
        this.sensorDataList = sensorDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sensor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SensorsData sensorData = sensorDataList.get(position);

        holder.attributesTextView.setText(sensorData.getAttributes());
        holder.currentDataTextView.setText(String.valueOf(sensorData.getCurrentData()));
        holder.safeLevelTextView.setText(sensorData.getSafeLevel());
    }

    @Override
    public int getItemCount() {
        return sensorDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView attributesTextView;
        public TextView currentDataTextView;
        public TextView safeLevelTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            attributesTextView = itemView.findViewById(R.id.textAttributes);
            currentDataTextView = itemView.findViewById(R.id.textCurrentData);
            safeLevelTextView = itemView.findViewById(R.id.textSafeLevel);
        }
    }
}

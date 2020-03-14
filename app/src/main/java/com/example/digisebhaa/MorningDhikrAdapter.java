package com.example.digisebhaa;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.digisebhaa.pojo.SebhaModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MorningDhikrAdapter extends RecyclerView.Adapter<MorningDhikrAdapter.MorningHolder> {
    ArrayList<SebhaModel> list = new ArrayList<>();

    @NonNull
    @Override
    public MorningHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MorningHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.morning_dhikr_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MorningHolder holder, int position) {
        holder.text.setText(list.get(position).getText());
        holder.description.setText(list.get(position).getDescription());
        holder.naratedBy.setText(list.get(position).getNarated_by());
        holder.repeated.setText(list.get(position).getRepeats() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void getMorningDhikrList(ArrayList<SebhaModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MorningHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView description;
        TextView naratedBy;
        TextView repeated;
        MaterialButton counter;

        public MorningHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tv_text);
            description = itemView.findViewById(R.id.tv_description);
            naratedBy = itemView.findViewById(R.id.tv_narated_by);
            repeated = itemView.findViewById(R.id.tv_repeats);
            counter = itemView.findViewById(R.id.btn_counter);

        }
    }
}

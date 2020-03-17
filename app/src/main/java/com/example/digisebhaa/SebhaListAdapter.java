package com.example.digisebhaa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digisebhaa.pojo.SebhaModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class SebhaListAdapter extends RecyclerView.Adapter<SebhaListAdapter.SebhaViewHolder> {

    private List<SebhaModel> getList;
    private LayoutInflater layoutInflater;

    SebhaListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SebhaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.morning_dhikr_item, parent, false);
        return new SebhaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SebhaViewHolder holder, int position) {
        if (getList != null) {
            SebhaModel current = getList.get(position);
            holder.text.setText(current.getText());
            holder.description.setText(current.getDescription());
            holder.narated_by.setText(current.getNarated_by());
            holder.repeated.setText(String.valueOf(current.getRepeats()));
            holder.materialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "You Click", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            holder.text.setText("No text");
            holder.description.setText("No text");
            holder.narated_by.setText("No text");
            holder.repeated.setText("0");
            holder.materialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "You Click", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    void setList(List<SebhaModel> model) {
        getList = model;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (getList != null) {
            return getList.size();
        } else {
            return 0;
        }
    }

    public class SebhaViewHolder extends RecyclerView.ViewHolder {
        MaterialButton materialButton;
        TextView text;
        TextView narated_by;
        TextView repeated;
        TextView description;

        public SebhaViewHolder(@NonNull View itemView) {
            super(itemView);
            materialButton = itemView.findViewById(R.id.btn_counter);
            text = itemView.findViewById(R.id.tv_text);
            narated_by = itemView.findViewById(R.id.tv_narated_by);
            repeated = itemView.findViewById(R.id.tv_repeats);
            description = itemView.findViewById(R.id.tv_description);

        }
    }
}

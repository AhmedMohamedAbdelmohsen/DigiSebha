package com.example.digisebhaa;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.TypedValue;
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
    public SharedPreferences sharedPreferences;
    public Typeface almushaf_font;
    public int color;
    public float size;

    SebhaListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        almushaf_font = Typeface.createFromAsset(context.getAssets(), sharedPreferences.getString("font", "almushaf.ttf"));
        color = context.getResources().getColor(sharedPreferences.getInt("color", R.color.black)); //get color from shared preference
        size = context.getResources().getDimension(sharedPreferences.getInt("size", R.dimen.medium)); //get size from shared preference

    }

    @NonNull
    @Override
    public SebhaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.dhikr_item, parent, false);
        return new SebhaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SebhaViewHolder holder, final int position) {
        if (getList != null) {
            final SebhaModel current = getList.get(position);
            if (current.getType() == 2) {
                holder.text.setText(current.getText());
                holder.text.setTypeface(almushaf_font);
                holder.text.setTextColor(color);
                holder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
                holder.narated_by.setText(current.getNarated_by());
                holder.narated_by.setTypeface(holder.almushaf_font);
                holder.repeated.setText(current.getRepeats());
                holder.repeated.setTypeface(holder.almushaf_font);
                holder.description.setVisibility(View.GONE);
                holder.counterButton.setVisibility(View.GONE);
            } else {
                holder.text.setText(current.getText());
                holder.text.setTypeface(almushaf_font);
                holder.text.setTextColor(color);
                holder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
                holder.description.setText(current.getDescription());
                holder.description.setTypeface(holder.quran_font);
                holder.narated_by.setText(current.getNarated_by());
                holder.narated_by.setTypeface(holder.almushaf_font);
                holder.repeated.setText(current.getRepeats());
                holder.repeated.setTypeface(holder.almushaf_font);
                holder.counterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "اضغط علي الشاشة لحساب عدد التكرار", Toast.LENGTH_SHORT).show();
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = Integer.parseInt(holder.counterButton.getText().toString());
                        if (count < current.getCounter()) {
                            holder.counterButton.setText(String.valueOf(count + 1));
                        } else {
                            //holder.itemView.setVisibility(View.GONE);
//                            getList.remove(position);
//                            notifyItemRemoved(position);
//                            notifyItemRangeChanged(position, getList.size());
                            holder.resetlButton.setVisibility(View.VISIBLE);
                            Toast.makeText(v.getContext(), "اضغط علي الزر الأحمر لإعادة العد من جديد", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                holder.resetlButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.counterButton.setText("0");
                        holder.resetlButton.setVisibility(View.GONE);
                    }
                });
            }


        } else {
            holder.text.setText(R.string.no_text);
            holder.description.setText(R.string.no_text);
            holder.narated_by.setText(R.string.no_text);
            holder.repeated.setText("0");
            holder.counterButton.setOnClickListener(new View.OnClickListener() {
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

    public static class SebhaViewHolder extends RecyclerView.ViewHolder {
        final MaterialButton counterButton;
        final MaterialButton resetlButton;
        final TextView text;
        final TextView narated_by;
        final TextView repeated;
        final TextView description;
        final Typeface almushaf_font;
        final Typeface quran_font;

        public SebhaViewHolder(@NonNull View itemView) {
            super(itemView);
            almushaf_font = Typeface.createFromAsset(itemView.getContext().getAssets(), "almushaf.ttf");
            quran_font = Typeface.createFromAsset(itemView.getContext().getAssets(), "Mothanna.ttf");
            //this.text.setTypeface(typeface);
            counterButton = itemView.findViewById(R.id.btn_counter);
            resetlButton = itemView.findViewById(R.id.btn_counter_reset);
            text = itemView.findViewById(R.id.tv_text);
            narated_by = itemView.findViewById(R.id.tv_narated_by);
            repeated = itemView.findViewById(R.id.tv_repeats);
            description = itemView.findViewById(R.id.tv_description);
            counterButton.setText("0");
        }
    }
}

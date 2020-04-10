package com.example.digisebhaa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digisebhaa.pojo.SebhaModel;

import java.util.List;

public class SebhaListAdapter extends RecyclerView.Adapter<SebhaListAdapter.SebhaViewHolder> {

    private List<SebhaModel> getList;
    private LayoutInflater layoutInflater;
    public SharedPreferences sharedPreferences;
    public Typeface almushaf_font;
    public int color;
    public float size;
    private final Boolean vibrationStatus;
    private Vibrator vibrator;
    private Context context;

    SebhaListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        almushaf_font = Typeface.createFromAsset(context.getAssets(), sharedPreferences.getString("font", "quran.ttf"));
        color = context.getResources().getColor(sharedPreferences.getInt("color", R.color.black)); //get color from shared preference
        size = context.getResources().getDimension(sharedPreferences.getInt("size", R.dimen.medium)); //get size from shared preference
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrationStatus = sharedPreferences.getBoolean("vibration", true);
    }

    @NonNull
    @Override
    public SebhaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.dhikr_item, parent, false);
        return new SebhaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SebhaViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        if (getList != null) {
            final SebhaModel current = getList.get(position);
            final String appName = "تطبيق السبحة الإلكترونية";
            if (current.getType() == 2) {
                holder.text.setText(current.getText());
                holder.text.setTypeface(almushaf_font);
                holder.text.setTextColor(color);
                holder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
                holder.repeated.setText(current.getRepeats());
                holder.repeated.setTypeface(holder.almushaf_font);
                holder.description.setVisibility(View.GONE);
                holder.counterButton.setVisibility(View.GONE);
                holder.shareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, holder.text.getText().toString() + "\n \n" + appName);
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "حديث شريف");
                        context.startActivity(Intent.createChooser(intent, "مشاركة الحديث"));
                    }
                });
            } else {
                holder.text.setText(current.getText());
                holder.text.setTypeface(almushaf_font);
                holder.text.setTextColor(color);
                holder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
                holder.description.setText(current.getDescription());
                holder.description.setTypeface(holder.quran_font);
                holder.repeated.setText(current.getRepeats());
                holder.repeated.setTypeface(holder.almushaf_font);
                holder.shareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, holder.text.getText().toString() + "\n \n" + appName);
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "أذكار الصباح والمساء");
                        context.startActivity(Intent.createChooser(intent, "مشاركة الذكر"));
                    }
                });
                holder.counterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "اضغط علي الشاشة لحساب عدد التكرار", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.counterButton.setText(String.valueOf(current.getCounter()));
                holder.resetlButton.setVisibility(View.GONE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (vibrationStatus) {
                            vibrator.vibrate(100);
                        } else {
                            vibrator.cancel();
                        }
                        int count = Integer.parseInt(holder.counterButton.getText().toString());
                        if (count > 1) {
                            holder.counterButton.setText(String.valueOf(count - 1));
                        } else {
                            holder.counterButton.setText("0");
                            vibrator.vibrate(400);
                            holder.resetlButton.setVisibility(View.VISIBLE);
                            Toast.makeText(v.getContext(), "اضغط علي الزر الأحمر لإعادة العد من جديد", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                holder.resetlButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vibrator.vibrate(300);
                        holder.counterButton.setText(String.valueOf(current.getCounter()));
                        holder.resetlButton.setVisibility(View.GONE);
                    }
                });
            }


        } else {
            holder.text.setText(R.string.no_text);
            holder.description.setText(R.string.no_text);
            holder.repeated.setText("0");
            holder.counterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "You Click", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
        final Button counterButton;
        final Button resetlButton;
        final ImageButton shareButton;
        final TextView text;
        final TextView repeated;
        final TextView description;
        final Typeface almushaf_font;
        final Typeface quran_font;

        public SebhaViewHolder(@NonNull View itemView) {
            super(itemView);
            almushaf_font = Typeface.createFromAsset(itemView.getContext().getAssets(), "almushaf.ttf");
            quran_font = Typeface.createFromAsset(itemView.getContext().getAssets(), "Mothanna.ttf");
            counterButton = itemView.findViewById(R.id.btn_counter);
            resetlButton = itemView.findViewById(R.id.btn_counter_reset);
            shareButton = itemView.findViewById(R.id.btn_share);
            text = itemView.findViewById(R.id.tv_text);
            repeated = itemView.findViewById(R.id.tv_repeats);
            description = itemView.findViewById(R.id.tv_description);
            counterButton.setText("0");
        }
    }
}

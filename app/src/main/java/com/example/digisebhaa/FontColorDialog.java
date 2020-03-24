package com.example.digisebhaa;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class FontColorDialog extends Dialog {

    public Activity activity;
    public Dialog d;
    public RadioGroup radioGroup;
    public RadioButton radioButton;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public FontColorDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_change_font_color);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        sharedPreferences = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        radioGroup.check(sharedPreferences.getInt("id_color", R.id.rbtn_first_font));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);
                switch (radioButton.getId()) {
                    case R.id.rbtn_first_font:
                        editor.putInt("id_color", R.id.rbtn_first_font).apply();
                        editor.putInt("color", R.color.black).apply();
                        Toast.makeText(activity, "تم تغيير لون الخط والحفظ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbtn_second_font:
                        editor.putInt("id_color", R.id.rbtn_second_font).apply();
                        editor.putInt("color", R.color.red).apply();
                        Toast.makeText(activity, "تم تغيير لون الخط والحفظ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbtn_third_font:
                        editor.putInt("id_color", R.id.rbtn_third_font).apply();
                        editor.putInt("color", R.color.blue).apply();
                        Toast.makeText(activity, "تم تغيير لون الخط والحفظ", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(activity, "nothing", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });

    }
}

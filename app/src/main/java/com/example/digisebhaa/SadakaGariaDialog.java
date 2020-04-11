package com.example.digisebhaa;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class SadakaGariaDialog extends Dialog {
    public Activity activity;
    public Button whatsButton, faceButton, twitterButton, copyButton;
    public EditText nameEt;

    public SadakaGariaDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_sadaka_garia);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);

        whatsButton = findViewById(R.id.btn_whats_app);
        faceButton = findViewById(R.id.btn_face_book);
        twitterButton = findViewById(R.id.btn_twitter);
        copyButton = findViewById(R.id.btn_copy);
        nameEt = findViewById(R.id.et_person_name);

        whatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ayaQuran = "﴿ فَاذْكُرُونِي أَذْكُرْكُمْ﴾";
                String nameDesc = "اللهم أجعل ثواب هذا  إلى: ";
                String name = nameEt.getText().toString().trim();
                String appName = "تطبيق السبحة الإلكترونية:";
                String url = "https://bit.ly/2UpOykL";
                String text = ayaQuran + "\n" + nameDesc + "\n" + "*" + name + "*" + "\n" + appName + "\n" + url;
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                waIntent.setPackage("com.whatsapp");
                if (waIntent != null) {
                    if (TextUtils.isEmpty(name)) {
                        nameEt.setError("أدخل اسم شخص من فضلك");
                    } else {
                        waIntent.putExtra(Intent.EXTRA_TEXT, text);//
                        activity.startActivity(Intent.createChooser(waIntent, "شارك على واتس اب"));
                    }
                } else {
                    Toast.makeText(activity, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        faceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ayaQuran = "﴿ فَاذْكُرُونِي أَذْكُرْكُمْ﴾";
                String nameDesc = "اللهم أجعل ثواب هذا  إلى: ";
                String name = nameEt.getText().toString().trim();
                String appName = "تطبيق السبحة الإلكترونية:";
                String url = "https://bit.ly/2UpOykL";
                String text = ayaQuran + "\n" + nameDesc + "\n" + name + "\n" + appName + "\n" + url;
                Intent facebookIntent = new Intent(Intent.ACTION_SEND);
                facebookIntent.setType("text/plain");
                facebookIntent.setPackage("com.facebook.katana");
                if (facebookIntent != null) {
                    if (TextUtils.isEmpty(name)) {
                        nameEt.setError("أدخل اسم شخص من فضلك");
                    } else {
                        facebookIntent.putExtra(Intent.EXTRA_TEXT, text);//
                        activity.startActivity(Intent.createChooser(facebookIntent, "شارك على فيسبوك"));
                    }
                } else {
                    Toast.makeText(activity, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ayaQuran = "﴿ فَاذْكُرُونِي أَذْكُرْكُمْ﴾";
                String nameDesc = "اللهم أجعل ثواب هذا  إلى: ";
                String name = nameEt.getText().toString().trim();
                String appName = "تطبيق السبحة الإلكترونية:";
                String url = "https://bit.ly/2UpOykL";
                String text = ayaQuran + "\n" + nameDesc + "\n" + name + "\n" + appName + "\n" + url;
                Intent twitterIntent = new Intent(Intent.ACTION_SEND);
                twitterIntent.setType("text/plain");
                twitterIntent.setPackage("com.twitter.android");
                if (twitterIntent != null) {
                    if (TextUtils.isEmpty(name)) {
                        nameEt.setError("أدخل اسم شخص من فضلك");
                    } else {
                        twitterIntent.putExtra(Intent.EXTRA_TEXT, text);//
                        activity.startActivity(Intent.createChooser(twitterIntent, "شارك على تويتر"));
                    }
                } else {
                    Toast.makeText(activity, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ayaQuran = "﴿ فَاذْكُرُونِي أَذْكُرْكُمْ﴾";
                String nameDesc = "اللهم أجعل ثواب هذا  إلى: ";
                String name = nameEt.getText().toString().trim();
                String appName = "تطبيق السبحة الإلكترونية:";
                String url = "https://bit.ly/2UpOykL";
                String text = ayaQuran + "\n" + nameDesc + "\n" + name + "\n" + appName + "\n" + url;
                if (TextUtils.isEmpty(name)) {
                    nameEt.setError("أدخل اسم شخص من فضلك");
                } else {
                    ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("مشاركة الرابط", text);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(activity, "تم نسخ رابط التطبيق", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

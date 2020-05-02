package com.ahmedabdelmohsen.digisebhaa.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.ahmedabdelmohsen.digisebhaa.R;

public class DialogContactUs extends Dialog {
    public Activity activity;
    public ImageButton whatsButton, faceButton;
    Uri facebookUri, PhoneUri;

    public DialogContactUs(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_contact_us);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);

        setFaceButton();

        setWhatsButton();
    }

    public void setFaceButton() {
        faceButton = findViewById(R.id.btn_contact_facebook);
        faceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                facebookUri = Uri.parse("https://www.facebook.com/AhmedM.AbdElmohsen.pro");
//                Intent in = new Intent(Intent.ACTION_VIEW, facebookUri);
//                activity.startActivity(in);

                String facebookUrl = "https://www.facebook.com/AhmedM.AbdElmohsen.pro";
                try {
                    int versionCode = activity.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                    if (versionCode >= 3002850) {
                        Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    } else {
                        // open the Facebook app using the old method (fb://profile/id or fb://page/id)
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/AhmedM.AbdElmohsen.pro")));
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    // Facebook is not installed. Open the browser
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
                }
            }
        });
    }

    public void setWhatsButton() {
        whatsButton = findViewById(R.id.btn_contact_whats_app);
        whatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneUri = Uri.parse("tel:+201066507721");
                Intent Phone = new Intent(Intent.ACTION_DIAL);
                Phone.setData(PhoneUri);
                activity.startActivity(Phone);
            }
        });
    }
}

package com.example.digisebhaa;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class SadakaAdsDialog extends Dialog {
    public Activity activity;
    public Button videoButton, pageButton;
    private RewardedAd rewardedAd;
    private InterstitialAd mInterstitialAd;

    public SadakaAdsDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_sadka_garia_ads);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
        videoButton = findViewById(R.id.btn_video_ads);
        pageButton = findViewById(R.id.btn_page_ads);
        //ads initialize
        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        // Load the  interstitial ِ Ads
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId("ca-app-pub-6572636131062240/6798856535");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        loadRewardedVidAds();
        sadakaGariaVideoAds();
        SadakaGariaInterstitialAds();
    }

    private void loadRewardedVidAds() {
        rewardedAd = new RewardedAd(activity,
                "ca-app-pub-6572636131062240/9041451464");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
//                Toast.makeText(activity, "اloaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
//                Toast.makeText(activity, "faild", Toast.LENGTH_SHORT).show();
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

    private void sadakaGariaVideoAds() {
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardedAd.isLoaded()) {
                    RewardedAdCallback adCallback = new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                            // Ad opened.
                        }

                        @Override
                        public void onRewardedAdClosed() {
                            loadRewardedVidAds();
                        }

                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem reward) {
                            Toast.makeText(activity, "هنيئاً لك ثواب هذه الصدقة", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onRewardedAdFailedToShow(int errorCode) {
                            loadRewardedVidAds();
                        }
                    };
                    rewardedAd.show(activity, adCallback);
                } else {
                    loadRewardedVidAds();
                    Toast.makeText(activity, "جاري تحميل الإعلان اضغط مرة آخري بعد 5 ثوان", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void SadakaGariaInterstitialAds() {
        pageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    Toast.makeText(activity, "هنيئاً لك ثواب هذه الصدقة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity, "جاري تحميل الإعلان اضغط مرة آخري بعد 5 ثوان", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

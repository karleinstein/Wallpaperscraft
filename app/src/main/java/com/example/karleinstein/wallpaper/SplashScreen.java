package com.example.karleinstein.wallpaper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class SplashScreen extends AppCompatActivity {
    private static final int SIZE = 17;
    private static final String BASE = "8080";
    private int width;
    private int widths[] = {2160, 1440, 1366, 1080, 1024, 960, 800, 800, 720, 540, 480, 480, 360, 320, 320, 240, 240};
    private int heights[] = {3840, 2560, 768, 1920, 600, 544, 1280, 600, 1280, 960, 854, 800, 640, 480, 240, 400, 320};
    private int height;
    private int widthT;
    private int fWidth;
    private int fHeight;
    private int heightT;
    private int temp[] = new int[1000];
    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int time = 0;
                while (time < 2000) {
                    try {
                        Thread.sleep(1000);
                        time += 1000;

                        SharedPreferences preferences = getSharedPreferences(BASE, MODE_PRIVATE);
                        boolean isChecked = preferences.getBoolean("resolution", false);
                        if (isChecked) {
                            fWidth = preferences.getInt("width", 0);
                            fHeight = preferences.getInt("height", 0);
                            Log.d("testz", fWidth + "w");
                            Log.d("testz", fHeight + "h");
                            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("width", fWidth);
                            bundle.putInt("height", fHeight);
                            //Log.d("fuck", "W" + fWidth + ": h" + fHeight);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        }

                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        width = displayMetrics.widthPixels;
                        height = displayMetrics.heightPixels;
                        for (int i = 0; i < widths.length; i++) {
                            widthT = Math.abs(width - widths[i]);
                            temp[index] = widthT;
                            index++;
                        }
                        widthT = 99999;
                        for (int i = 0; i < index; i++) {
                            if (widthT > temp[i]) {
                                widthT = temp[i];
                            }
                        }
                        for (int i = 0; i < widths.length; i++) {
                            if (widthT == Math.abs(width - widths[i])) {
                                fWidth = widths[i];
                            }
                        }
                        temp = new int[1000];
                        index = 0;
                        for (int i = 0; i < heights.length; i++) {
                            heightT = Math.abs(height - heights[i]);
                            temp[index] = heightT;
                            index++;
                        }
                        heightT = 99999;
                        for (int i = 0; i < index; i++) {
                            if (heightT > temp[i]) {
                                heightT = temp[i];
                            }
                        }
                        for (int i = 0; i < heights.length; i++) {
                            if (heightT == Math.abs(height - heights[i])) {
                                fHeight = heights[i];
                            }
                        }
                        if (!isChecked) {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("resolution", true);
                            editor.putInt("width", fWidth);
                            editor.putInt("height", fHeight);
                            editor.apply();
                            Intent intent = new Intent(SplashScreen.this, CheckResolution.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("width", fWidth);
                            bundle.putInt("height", fHeight);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        thread.start();

    }
}

package com.example.karleinstein.wallpaper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class CheckResolution extends AppCompatActivity implements View.OnClickListener {
    private TextView txtReso;
    private TextView intro;
    private Button btnNext;
    private int fWidth;
    private int fHeight;
    private int width;
    private int height;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_check_resolution);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        txtReso = findViewById(R.id.txtReso);
        intro = findViewById(R.id.intro);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/BEYNO.ttf");
        intro.setTypeface(type);
        Intent intent = getIntent();
        fWidth = intent.getIntExtra("width", 0);
        fHeight = intent.getIntExtra("height", 0);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        txtReso.setText(width + "x" + height);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("width", fWidth);
                bundle.putInt("height", fHeight);
                //Log.d("fuck", "W" + fWidth + ": h" + fHeight);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}

package com.example.karleinstein.wallpaper;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Objects;

public class Dialog extends android.app.Dialog {
    private TextView loading;

    Dialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        loading = findViewById(R.id.loading);
        Typeface type = Typeface.createFromAsset(getContext().getAssets(), "fonts/BEYNO.ttf");
        loading.setTypeface(type);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Objects.requireNonNull(getWindow())
                .setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}

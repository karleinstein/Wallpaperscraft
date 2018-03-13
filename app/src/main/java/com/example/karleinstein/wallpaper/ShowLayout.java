package com.example.karleinstein.wallpaper;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.pm.ActivityInfo;
import android.os.StrictMode;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
public class ShowLayout extends AppCompatActivity{
    Elements elements;
    String src="";
    String b;
    String y,g,t,ten,hinh;
    String a;
    ImageView imageView;
    ProgressDialog progressDialog;
    private InterstitialAd mInterstitialAd;
    String url = "https://wall.alphacoders.com/";

    public static final String[] PERMISSION_LIST = {
            android.Manifest.permission.INTERNET,
            Manifest.permission.SET_WALLPAPER,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    } ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);
        mInterstitialAd = new InterstitialAd(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mInterstitialAd.setAdUnitId("ca-app-pub-3739982462015298/4354804253");
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }

            private void showInterstitial() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });
        imageView=findViewById(R.id.imgShow);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait ");
        progressDialog.setMessage("It may take a few seconds");
        progressDialog.setCanceledOnTouchOutside(false);
        FloatingActionButton fab = findViewById(R.id.fab);
        //Intent intent=getIntent();
        //a=intent.getStringExtra("hinhanh");
        //Glide.with(this).load("https://images2.alphacoders.com/606/606275.jpg").into(imageView);
        xuly();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.buildDrawingCache();
                Bitmap bmap = imageView.getDrawingCache();
                WallpaperManager m=WallpaperManager.getInstance(ShowLayout.this);

                try {
                    m.setBitmap(bmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Snackbar.make(v, "Set Wallpaper successfully", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        }

    private void xuly() {
        //new DownloadImageTask((ImageView) findViewById(R.id.imgHinhAnh))
                //.execute("https://images2.alphacoders.com/606/606275.jpg");
        Intent intent=getIntent();
        a=intent.getStringExtra("hinhanh");
        Task task=new Task();
        task.execute("");


        }
        class Task extends AsyncTask<String,Void,Bitmap>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                imageView.setImageBitmap(bitmap);
                progressDialog.dismiss();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected Bitmap doInBackground(String... strings) {
                String urlz=a;
                try {
                    //org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                    org.jsoup.nodes.Document document = Jsoup.connect(urlz).get();
                    if (document != null) {

                        //neu no ton tai gia tri
                        elements = document.select("div.wb_preview");
                        for (Element element : elements) {
                            Element elementten = element.getElementsByTag("a").first();
                            Element elementhinh = element.getElementsByTag("img").first();
                            if (elementten != null) {
                                ten = elementten.attr("href");
                                y="https:";
                                t=y+ten;
                            }
                            if (elementhinh != null) {
                                hinh = elementhinh.attr("src");
                                y="https:";
                                g=y+hinh;
                            }
                            Bitmap bitmap= BitmapFactory.decodeStream((InputStream) new URL(g).getContent());
                            return bitmap;

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

}


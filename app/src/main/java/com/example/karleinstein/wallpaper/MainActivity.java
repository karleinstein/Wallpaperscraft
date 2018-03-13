package com.example.karleinstein.wallpaper;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStructure;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.MobileAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    CustomAdapter customAdapter, zzz;
    TextView tvName,tvCopy;
    ArrayList<HinhAnh> hinhAnhs;
    int k = 0;
    ProgressDialog progressDialog;
    String link="";
    private int index = -1;
    GridView gridView;
    ImageView imgSkrillex;
    String g,y,t;
    int allz;
    String ten = "";
    String hinh = "";
    NavigationView navigationView;
    Elements elements;
    public List<HinhAnh> wallpapersList;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this,"ca-app-pub-3739982462015298~5713431032");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        wallpapersList = new ArrayList<HinhAnh>();
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvName = headerView.findViewById(R.id.tvName);
        tvCopy=headerView.findViewById(R.id.tvCopy);
        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/BEYNO.ttf");
        tvName.setTypeface(typeface1);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/scary_glyphs_and_nice_characters.ttf");
        tvCopy.setTypeface(typeface);
        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        hinhAnhs = new ArrayList<>();
        gridView = findViewById(R.id.imggridView);
        imgSkrillex=findViewById(R.id.skrillex);
        yeah();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        k = 1;
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void yeah() {
        imgSkrillex.setImageResource(R.drawable.lul);
    }

    class Task extends AsyncTask<String, Void, ArrayList<HinhAnh>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<HinhAnh> hinhAnhs) {
            super.onPostExecute(hinhAnhs);
            gridView.setAdapter(customAdapter);
            imgSkrillex.setImageBitmap(null);
            progressDialog.dismiss();
        }

        @Override
        protected ArrayList<HinhAnh> doInBackground(String... strings) {
            if (allz == 1) {
                String url[] = {"https://wallpaperscraft.com/catalog/sport/1080x1920"
                        , "https://wallpaperscraft.com/catalog/sport/1080x1920/page2"
                        , "https://wallpaperscraft.com/catalog/sport/1080x1920/page3"
                        , "https://wallpaperscraft.com/catalog/sport/1080x1920/page4"
                        , "https://wallpaperscraft.com/catalog/sport/1080x1920/page5"};
                for (int i = 0; i < url.length; i++) {
                    try {
                        //org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        if (document != null) {

                            //neu no ton tai gia tri
                            elements = document.select("div.wallpaper_pre");
                            for (Element element : elements) {
                                Element elementten = element.getElementsByTag("a").first();
                                Element elementhinh = element.getElementsByTag("img").first();
                                if (elementten != null) {
                                    ten = elementten.attr("href");
                                    y = "https:";
                                    t = y + ten;
                                }
                                if (elementhinh != null) {
                                    hinh = elementhinh.attr("src");
                                    y = "https:";
                                    g = y + hinh;
                                }
                                hinhAnhs.add(new HinhAnh(t, g));

                            }
                            customAdapter = new CustomAdapter(hinhAnhs, MainActivity.this);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (allz == 2) {
                String url[] = {"https://wallpaperscraft.com/catalog/3d/1080x1920"
                        , "https://wallpaperscraft.com/catalog/3d/1080x1920/page2"};
                for (int i = 0; i < url.length; i++) {
                    try {
                        //org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        if (document != null) {

                            //neu no ton tai gia tri
                            elements = document.select("div.wallpaper_pre");
                            for (Element element : elements) {
                                Element elementten = element.getElementsByTag("a").first();
                                Element elementhinh = element.getElementsByTag("img").first();
                                if (elementten != null) {
                                    ten = elementten.attr("href");
                                    y = "https:";
                                    t = y + ten;
                                }
                                if (elementhinh != null) {
                                    hinh = elementhinh.attr("src");
                                    y = "https:";
                                    g = y + hinh;
                                }
                                hinhAnhs.add(new HinhAnh(t, g));

                            }
                            customAdapter = new CustomAdapter(hinhAnhs, MainActivity.this);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (allz == 3) {
                String url[] = {"https://wallpaperscraft.com/catalog/abstract/1080x1920"
                        , "https://wallpaperscraft.com/catalog/abstract/1080x1920/page2"
                        , "https://wallpaperscraft.com/catalog/abstract/1080x1920/page3"
                        , "https://wallpaperscraft.com/catalog/abstract/1080x1920/page4"
                        , "https://wallpaperscraft.com/catalog/abstract/1080x1920/page5"};
                for (int i = 0; i < url.length; i++) {
                    try {
                        //org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        if (document != null) {

                            //neu no ton tai gia tri
                            elements = document.select("div.wallpaper_pre");
                            for (Element element : elements) {
                                Element elementten = element.getElementsByTag("a").first();
                                Element elementhinh = element.getElementsByTag("img").first();
                                if (elementten != null) {
                                    ten = elementten.attr("href");
                                    y = "https:";
                                    t = y + ten;
                                }
                                if (elementhinh != null) {
                                    hinh = elementhinh.attr("src");
                                    y = "https:";
                                    g = y + hinh;
                                }
                                hinhAnhs.add(new HinhAnh(t, g));

                            }
                            customAdapter = new CustomAdapter(hinhAnhs, MainActivity.this);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (allz == 4) {
                String url[] = {"https://wallpaperscraft.com/catalog/animals/1080x1920"
                        , "https://wallpaperscraft.com/catalog/animals/1080x1920/page2"
                        , "https://wallpaperscraft.com/catalog/animals/1080x1920/page3"
                        , "https://wallpaperscraft.com/catalog/animals/1080x1920/page4"
                        , "https://wallpaperscraft.com/catalog/animals/1080x1920/page5"};
                for (int i = 0; i < url.length; i++) {
                    try {
                        //org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        if (document != null) {

                            //neu no ton tai gia tri
                            elements = document.select("div.wallpaper_pre");
                            for (Element element : elements) {
                                Element elementten = element.getElementsByTag("a").first();
                                Element elementhinh = element.getElementsByTag("img").first();
                                if (elementten != null) {
                                    ten = elementten.attr("href");
                                    y = "https:";
                                    t = y + ten;
                                }
                                if (elementhinh != null) {
                                    hinh = elementhinh.attr("src");
                                    y = "https:";
                                    g = y + hinh;
                                }
                                hinhAnhs.add(new HinhAnh(t, g));

                            }
                            customAdapter = new CustomAdapter(hinhAnhs, MainActivity.this);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
                if (allz == 5) {
                    String url[] = {"https://wallpaperscraft.com/catalog/anime/1080x1920"
                            , "https://wallpaperscraft.com/catalog/anime/1080x1920/page2"
                            , "https://wallpaperscraft.com/catalog/anime/1080x1920/page3"
                            , "https://wallpaperscraft.com/catalog/anime/1080x1920/page4"
                            , "https://wallpaperscraft.com/catalog/anime/1080x1920/page5"};
                    for (int i = 0; i < url.length; i++) {
                        try {
                            //org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                            org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                            if (document != null) {

                                //neu no ton tai gia tri
                                elements = document.select("div.wallpaper_pre");
                                for (Element element : elements) {
                                    Element elementten = element.getElementsByTag("a").first();
                                    Element elementhinh = element.getElementsByTag("img").first();
                                    if (elementten != null) {
                                        ten = elementten.attr("href");
                                        y = "https:";
                                        t = y + ten;
                                    }
                                    if (elementhinh != null) {
                                        hinh = elementhinh.attr("src");
                                        y = "https:";
                                        g = y + hinh;
                                    }
                                    hinhAnhs.add(new HinhAnh(t, g));

                                }
                                customAdapter = new CustomAdapter(hinhAnhs, MainActivity.this);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            if (allz == 6) {
                String url[] = {"https://wallpaperscraft.com/catalog/city/1080x1920"
                        , "https://wallpaperscraft.com/catalog/city/1080x1920/page2"
                        , "https://wallpaperscraft.com/catalog/city/1080x1920/page3"
                        , "https://wallpaperscraft.com/catalog/city/1080x1920/page4"
                        , "https://wallpaperscraft.com/catalog/city/1080x1920/page5"};
                for (int i = 0; i < url.length; i++) {
                    try {
                        //org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        if (document != null) {

                            //neu no ton tai gia tri
                            elements = document.select("div.wallpaper_pre");
                            for (Element element : elements) {
                                Element elementten = element.getElementsByTag("a").first();
                                Element elementhinh = element.getElementsByTag("img").first();
                                if (elementten != null) {
                                    ten = elementten.attr("href");
                                    y = "https:";
                                    t = y + ten;
                                }
                                if (elementhinh != null) {
                                    hinh = elementhinh.attr("src");
                                    y = "https:";
                                    g = y + hinh;
                                }
                                hinhAnhs.add(new HinhAnh(t, g));

                            }
                            customAdapter = new CustomAdapter(hinhAnhs, MainActivity.this);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (allz == 7) {
                String url[] = {"https://wallpaperscraft.com/catalog/fantasy/1080x1920"
                        , "https://wallpaperscraft.com/catalog/fantasy/1080x1920/page2"
                        , "https://wallpaperscraft.com/catalog/fantasy/1080x1920/page3"
                        , "https://wallpaperscraft.com/catalog/fantasy/1080x1920/page4"
                        , "https://wallpaperscraft.com/catalog/fantasy/1080x1920/page5"};
                for (int i = 0; i < url.length; i++) {
                    try {
                        //org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        if (document != null) {

                            //neu no ton tai gia tri
                            elements = document.select("div.wallpaper_pre");
                            for (Element element : elements) {
                                Element elementten = element.getElementsByTag("a").first();
                                Element elementhinh = element.getElementsByTag("img").first();
                                if (elementten != null) {
                                    ten = elementten.attr("href");
                                    y = "https:";
                                    t = y + ten;
                                }
                                if (elementhinh != null) {
                                    hinh = elementhinh.attr("src");
                                    y = "https:";
                                    g = y + hinh;
                                }
                                hinhAnhs.add(new HinhAnh(t, g));

                            }
                            customAdapter = new CustomAdapter(hinhAnhs, MainActivity.this);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (allz == 8) {
                String url[] = {"https://wallpaperscraft.com/catalog/girls/1080x1920"
                        , "https://wallpaperscraft.com/catalog/girls/1080x1920/page2"
                        , "https://wallpaperscraft.com/catalog/girls/1080x1920/page3"
                        , "https://wallpaperscraft.com/catalog/girls/1080x1920/page4"
                        , "https://wallpaperscraft.com/catalog/girls/1080x1920/page5"};
                for (int i = 0; i < url.length; i++) {
                    try {
                        //org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        if (document != null) {

                            //neu no ton tai gia tri
                            elements = document.select("div.wallpaper_pre");
                            for (Element element : elements) {
                                Element elementten = element.getElementsByTag("a").first();
                                Element elementhinh = element.getElementsByTag("img").first();
                                if (elementten != null) {
                                    ten = elementten.attr("href");
                                    y = "https:";
                                    t = y + ten;
                                }
                                if (elementhinh != null) {
                                    hinh = elementhinh.attr("src");
                                    y = "https:";
                                    g = y + hinh;
                                }
                                hinhAnhs.add(new HinhAnh(t, g));

                            }
                            customAdapter = new CustomAdapter(hinhAnhs, MainActivity.this);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (allz == 9) {
                String url[] = {"https://wallpaperscraft.com/catalog/nature/1080x1920"
                        , "https://wallpaperscraft.com/catalog/nature/1080x1920/page2"
                        , "https://wallpaperscraft.com/catalog/nature/1080x1920/page3"
                        , "https://wallpaperscraft.com/catalog/nature/1080x1920/page4"
                        , "https://wallpaperscraft.com/catalog/nature/1080x1920/page5"};
                for (int i = 0; i < url.length; i++) {
                    try {
                        //org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                        if (document != null) {

                            //neu no ton tai gia tri
                            elements = document.select("div.wallpaper_pre");
                            for (Element element : elements) {
                                Element elementten = element.getElementsByTag("a").first();
                                Element elementhinh = element.getElementsByTag("img").first();
                                if (elementten != null) {
                                    ten = elementten.attr("href");
                                    y = "https:";
                                    t = y + ten;
                                }
                                if (elementhinh != null) {
                                    hinh = elementhinh.attr("src");
                                    y = "https:";
                                    g = y + hinh;
                                }
                                hinhAnhs.add(new HinhAnh(t, g));

                            }
                            customAdapter = new CustomAdapter(hinhAnhs, MainActivity.this);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

                return hinhAnhs;
            }
        }

        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement

            return super.onOptionsItemSelected(item);
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            switch (item.getItemId()) {

                case R.id.nav_sport:
                    if (hinhAnhs != null) {
                        hinhAnhs.clear();
                    }
                    allz=1;
                    Task task=new Task();
                    task.execute("");
                    break;
                case R.id.nav_3d:
                    allz=2;
                    if (hinhAnhs != null) {
                        hinhAnhs.clear();
                    }
                    Task task1=new Task();
                    task1.execute("");
                    break;
                case R.id.nav_abtract:
                    allz=3;
                    if (hinhAnhs != null) {
                        hinhAnhs.clear();
                    }
                    Task task2=new Task();
                    task2.execute("");
                    break;
                case R.id.nav_animals:
                    allz=4;
                    if (hinhAnhs != null) {
                        hinhAnhs.clear();
                    }
                    Task task3=new Task();
                    task3.execute("");
                    break;
                case R.id.nav_anime:
                    allz=5;
                    if (hinhAnhs != null) {
                        hinhAnhs.clear();
                    }
                    Task task4=new Task();
                    task4.execute("");
                    break;
                case R.id.nav_city:
                    allz=6;
                    if (hinhAnhs != null) {
                        hinhAnhs.clear();
                    }
                    Task task5=new Task();
                    task5.execute("");
                    break;
                case R.id.nav_fantasy:
                    allz=7;
                    if (hinhAnhs != null) {
                        hinhAnhs.clear();
                    }
                    Task task6=new Task();
                    task6.execute("");
                    break;
                case R.id.nav_girls:
                    allz=8;
                    if (hinhAnhs != null) {
                        hinhAnhs.clear();
                    }
                    Task task7=new Task();
                    task7.execute("");
                    break;
                case R.id.nav_nature:
                    allz=9;
                    if (hinhAnhs != null) {
                        hinhAnhs.clear();
                    }
                    Task task8=new Task();
                    task8.execute("");
                    break;

            }
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HinhAnh hinhAnh1=hinhAnhs.get(position);
                    Intent intent=new Intent(MainActivity.this,ShowLayout.class);
                    intent.putExtra("hinhanh",hinhAnh1.getTenAnh());
                    startActivity(intent);
                }
            });
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }

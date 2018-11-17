package com.example.karleinstein.wallpaper;

import android.annotation.SuppressLint;
import android.app.Fragment;
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
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    CustomAdapter customAdapter;
    TextView tvName, tvCopy;
    ArrayList<HinhAnh> hinhAnhs;
    int k = 0;
    ProgressDialog progressDialog;
    private Dialog dialog;
    public DrawerLayout drawer;
    private int size = 0;
    GridView gridView;
    String g, y;
    StringBuffer t;
    private int width;
    private int height;
    public Task task;
    private String reso;
    private int index;
    public String url;
    private ActionBarDrawerToggle toggle;
    String ten = "";
    String hinh = "";
    NavigationView navigationView;
    Elements elements;
    public List<HinhAnh> wallpapersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        dialog = new Dialog(this);
        task = new Task();
        drawer = findViewById(R.id.drawer_layout);

        width = getIntent().getIntExtra("width", 0);
        height = getIntent().getIntExtra("height", 0);
        Log.d("zzz", width + "w:h" + height);
        reso = width + "x" + height;
        t = new StringBuffer();
        //MobileAds.initialize(this, "ca-app-pub-3739982462015298~5713431032");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        wallpapersList = new ArrayList<>();
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvName = headerView.findViewById(R.id.tvName);
        tvCopy = headerView.findViewById(R.id.tvCopy);
        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/BEYNO.ttf");
        tvName.setTypeface(typeface1);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/scary_glyphs_and_nice_characters.ttf");
        tvCopy.setTypeface(typeface);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        hinhAnhs = new ArrayList<>();
        gridView = findViewById(R.id.imggridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HinhAnh hinhAnh1 = hinhAnhs.get(position);
                Fragment showImage = new ShowLayout();
                getFragmentManager().beginTransaction().add(R.id.drawer_layout, showImage)
                        .addToBackStack(null)
                        .commit();
                Bundle bundle = new Bundle();
                bundle.putString("hinhanh", hinhAnh1.getTenAnh());
                showImage.setArguments(bundle);
                task.cancel(true);
                task = null;
                task = new Task();

            }
        });
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        k = 1;
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        url = "https://wallpaperscraft.com/all/";
        TaskAllPage taskAllPage = new TaskAllPage();
        taskAllPage.execute(url + reso + "/");
        task.execute(url + reso + "/");
        setTitle("All");
    }


    public class Task extends AsyncTask<String, ArrayList<HinhAnh>, ArrayList<HinhAnh>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog.show();
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }

        @Override
        protected void onPostExecute(ArrayList<HinhAnh> hinhAnhs) {
            super.onPostExecute(hinhAnhs);
            //progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(ArrayList<HinhAnh>... values) {
            super.onProgressUpdate(values);
            //progressDialog.dismiss();
            dialog.dismiss();
            if (customAdapter == null) {
                customAdapter = new CustomAdapter(hinhAnhs, MainActivity.this);
                //gridView.setSelection(index);
                gridView.setAdapter(customAdapter);
            } else if (isCancelled()) {

                hinhAnhs.clear();
                customAdapter = null;

            } else {
                customAdapter.notifyDataSetChanged();
            }


        }

        @Override
        protected ArrayList<HinhAnh> doInBackground(String... strings) {
            String url[] = new String[size];
            Log.d("fuck", size + "");
            url[0] = strings[0];
            for (int i = 0; i < size; i++) {
                if (i >= 1) {
                    int in = i + 1;
                    url[i] = strings[0] + "page" + in;
                }
                if (isCancelled()) {
                    hinhAnhs.clear();
                    url = new String[size];
                    break;
                }
                try {
                    Log.d("test", url[i]);
                    //org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                    Document document = Jsoup.connect(url[i]).get();
                    if (document != null) {

                        //neu no ton tai gia tri
                        elements = document.select("li.wallpapers__item");
                        for (Element element : elements) {
                            Element elementten = element.getElementsByTag("a").first();
                            Element elementhinh = element.getElementsByTag("img").first();
                            if (elementten != null) {
                                ten = elementten.attr("href");
                                ten = "https://wallpaperscraft.com" + ten;

                            }
                            if (elementhinh != null) {
                                hinh = elementhinh.attr("src");
                                //y = "https://wallpaperscraft.com";
                                g = hinh;

                            }
                            hinhAnhs.add(new HinhAnh(ten, g));
                            t = new StringBuffer();
                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                publishProgress(hinhAnhs);

            }


            return hinhAnhs;

        }
    }

    class TaskAllPage extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(strings[0]).get();
                if (document != null) {

                    //neu no ton tai gia tri
                    elements = document.select("ul.pager__list");
                    Elements elements2 = elements.select("li.pager__item");
                    Elements elements3 = elements2.select("li.pager__item_last-page");
                    for (Element element : elements3) {
                        Element elementten = element.getElementsByTag("a").first();
                        if (elementten != null) {
                            ten = elementten.attr("href");

                        }
                    }
                    StringBuffer temp = new StringBuffer();
                    for (int i = ten.length() - 1; i >= 0; i--) {
                        if (ten.charAt(i) >= '0' && ten.charAt(i) <= '9') {
                            temp.append(ten.charAt(i));
                        } else {
                            break;
                        }
                    }
                    temp.reverse();
                    size = Integer.parseInt(String.valueOf(temp));


                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return size;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            //progressDialog.dismiss();
            dialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.nav_sport:
                if (hinhAnhs != null) {
                    hinhAnhs.clear();
                }

                url = "https://wallpaperscraft.com/catalog/sport/";
                TaskAllPage taskAllPage = new TaskAllPage();
                taskAllPage.execute(url + reso + "/");
                task.cancel(true);
                task = null;
                task = new Task();
                task.execute(url + reso + "/");
                setTitle("Sport");

                break;
            case R.id.nav_3d:
                if (hinhAnhs != null) {
                    hinhAnhs.clear();
                }
                url = "https://wallpaperscraft.com/catalog/3d/";
                TaskAllPage taskAllPage1 = new TaskAllPage();
                taskAllPage1.execute(url + reso + "/");
                task.cancel(true);
                task = null;
                task = new Task();
                task.execute(url + reso + "/");
                setTitle("3D");
                break;
            case R.id.nav_abtract:
                if (hinhAnhs != null) {
                    hinhAnhs.clear();
                }
                url = "https://wallpaperscraft.com/catalog/abstract/";
                TaskAllPage taskAllPage2 = new TaskAllPage();
                taskAllPage2.execute(url + reso + "/");
                task.cancel(true);
                task = null;
                task = new Task();
                task.execute(url + reso + "/");
                setTitle("Abtract");
                break;
            case R.id.nav_animals:
                if (hinhAnhs != null) {
                    hinhAnhs.clear();
                }
                url = "https://wallpaperscraft.com/catalog/animals/";
                TaskAllPage taskAllPage3 = new TaskAllPage();
                taskAllPage3.execute(url + reso + "/");
                task.cancel(true);
                task = null;
                task = new Task();
                task.execute(url + reso + "/");
                setTitle("Animals");
                break;
            case R.id.nav_anime:
                if (hinhAnhs != null) {
                    hinhAnhs.clear();
                }
                url = "https://wallpaperscraft.com/catalog/anime/";
                TaskAllPage taskAllPage4 = new TaskAllPage();
                taskAllPage4.execute(url + reso + "/");
                task.cancel(true);
                task = null;
                task = new Task();
                task.execute(url + reso + "/");
                setTitle("Anime");
                break;
            case R.id.nav_city:
                if (hinhAnhs != null) {
                    hinhAnhs.clear();
                }
                url = "https://wallpaperscraft.com/catalog/city/";
                TaskAllPage taskAllPage5 = new TaskAllPage();
                taskAllPage5.execute(url + reso + "/");
                task.cancel(true);
                task = null;
                task = new Task();
                task.execute(url + reso + "/");
                setTitle("City");

                break;
            case R.id.nav_fantasy:
                if (hinhAnhs != null) {
                    hinhAnhs.clear();
                }
                url = "https://wallpaperscraft.com/catalog/fantasy/";
                TaskAllPage taskAllPage6 = new TaskAllPage();
                taskAllPage6.execute(url + reso + "/");
                task.cancel(true);
                task = null;
                task = new Task();
                task.execute(url + reso + "/");
                setTitle("Fantasy");
                break;
            case R.id.nav_girls:
                if (hinhAnhs != null) {
                    hinhAnhs.clear();
                    customAdapter = null;
                }
                url = "https://wallpaperscraft.com/catalog/tv-series/";
                TaskAllPage taskAllPage7 = new TaskAllPage();
                taskAllPage7.execute(url + reso + "/");
                task.cancel(true);
                task = null;
                task = new Task();
                task.execute(url + reso + "/");
                setTitle("TV-Series");
                break;
            case R.id.nav_nature:
                if (hinhAnhs != null) {
                    hinhAnhs.clear();
                }
                url = "https://wallpaperscraft.com/catalog/nature/";
                TaskAllPage taskAllPage8 = new TaskAllPage();
                taskAllPage8.execute(url + reso + "/");
                task.cancel(true);
                task = null;
                task = new Task();
                task.execute(url + reso + "/");
                setTitle("Nature");
                break;
            case R.id.nav_all:
                if (hinhAnhs != null) {
                    hinhAnhs.clear();
                }
                url = "https://wallpaperscraft.com/all/";
                TaskAllPage taskAllPage9 = new TaskAllPage();
                taskAllPage9.execute(url + reso + "/");
                task.cancel(true);
                task = null;
                task = new Task();
                task.execute(url + reso + "/");
                setTitle("All");
                break;

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

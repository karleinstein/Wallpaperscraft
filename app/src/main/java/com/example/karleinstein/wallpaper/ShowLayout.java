package com.example.karleinstein.wallpaper;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.pm.ActivityInfo;
import android.graphics.Picture;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.WallpaperManager.ACTION_CROP_AND_SET_WALLPAPER;

public class ShowLayout extends Fragment implements View.OnClickListener {
    Elements elements;
    String src = "";
    String b;
    String y, g, t, ten, hinh;
    String a;
    private boolean isClicked = false;
    Bitmap result;
    ImageView imageView;
    private Dialog dialog;
    ProgressDialog progressDialog;
    private ImageButton fab;
    private ImageButton btnSave;
    private TextView txtSave;
    private TextView txtSetAs;
    private LinearLayout layoutSave;
    private LinearLayout layoutSetAs;
    private static final int REQUESTCODE = 8080;
    private ImageButton btnSetAs;
    MainActivity activity;
   // private InterstitialAd mInterstitialAd;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            mInterstitialAd = new InterstitialAd(getContext());
//        }
//        mInterstitialAd.setAdUnitId("ca-app-pub-3739982462015298/4354804253");
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();

        // Load ads into Interstitial Ads
       // mInterstitialAd.loadAd(adRequest);

//        mInterstitialAd.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                showInterstitial();
//            }
//
//            private void showInterstitial() {
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                }
//            }
//        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dialog = new Dialog(getContext());
        }
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        activity = (MainActivity) getActivity();
        activity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        imageView = getView().findViewById(R.id.imgShow);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            progressDialog = new ProgressDialog(getContext());
        }
        progressDialog.setTitle("Please wait ");
        progressDialog.setMessage("It may take a few seconds");
        progressDialog.setCanceledOnTouchOutside(false);
        fab = getView().findViewById(R.id.fab);
        btnSave = getView().findViewById(R.id.btnSave);
        btnSetAs = getView().findViewById(R.id.btnSetAs);
        txtSave = getView().findViewById(R.id.txtSave);
        txtSetAs = getView().findViewById(R.id.txtSetAs);
        layoutSave = getView().findViewById(R.id.layoutSave);
        layoutSetAs = getView().findViewById(R.id.layoutSetAs);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Typeface type = Typeface.createFromAsset(getContext().getAssets(), "fonts/BEYNO.ttf");
            txtSave.setTypeface(type);
            txtSetAs.setTypeface(type);
        }

        fab.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnSetAs.setOnClickListener(this);
        xuly();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_layout, container, false);
    }

    private void xuly() {
        //new DownloadImageTask((ImageView) findViewById(R.id.imgHinhAnh))
        //.execute("https://images2.alphacoders.com/606/606275.jpg");
        Bundle bundle = getArguments();
        a = bundle.getString("hinhanh");
        Task task = new Task();
        task.execute("");


    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("test", "onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        activity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        if (activity.task.getStatus() == AsyncTask.Status.PENDING) {
            activity.task.execute(activity.url);
        }


        //Log.d("test", "onStop");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                //isClicked = true;
                checkClick();
                break;
            case R.id.btnSave:
                if (checkPermission()) {
                    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                    String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                    File myDir = new File(root);
                    myDir.mkdirs();

                    String fname = "image" + timeStamp + ".png";
                    File file = new File(myDir, fname);
                    if (file.exists()) file.delete();
                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        result.compress(Bitmap.CompressFormat.PNG, 0, out);
                        Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                        out.flush();
                        out.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaScannerConnection.scanFile(getActivity(),
                            new String[]{file.toString()}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("testz", "Scanned " + path + ":");
                                    Log.i("testz", "-> uri=" + uri);
                                }
                            });
                    // Uri imageUri = Uri.parse(file.getAbsolutePath());
                    //Log.d("testz", imageUri.toString());

                }
                break;
            case R.id.btnSetAs:
                TaskTemp taskTemp = new TaskTemp();
                taskTemp.execute(result);
                break;
        }
    }


    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUESTCODE);
//                boolean showRotionale = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                if (!showRotionale) {
//                    Toast.makeText(getActivity(), "Use must allow this permission to use this app", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(getActivity(), "Fuck you I will destroy your phone", Toast.LENGTH_SHORT).show();
//                }
                return false;
            }

        }

        return true;
    }

    private void checkClick() {

        if (layoutSave.getVisibility() == View.GONE && layoutSetAs.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_blink);
            layoutSave.startAnimation(animation);
            Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_sequent);

            layoutSetAs.startAnimation(animation);
            layoutSave.setVisibility(View.VISIBLE);
            layoutSave.startAnimation(animation);
            layoutSetAs.setVisibility(View.VISIBLE);
            fab.startAnimation(animation2);
            fab.setImageResource(R.drawable.cancel);
        } else {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_blink2);
            Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_sequent);
            layoutSave.startAnimation(animation);
            layoutSave.startAnimation(animation);
            layoutSave.setVisibility(View.GONE);
            layoutSetAs.startAnimation(animation);
            layoutSetAs.setVisibility(View.GONE);
            fab.startAnimation(animation2);
            fab.setImageResource(R.drawable.add);
        }
    }

    class TaskTemp extends AsyncTask<Bitmap, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Bitmap... bitmaps) {
            WallpaperManager m = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                m = WallpaperManager.getInstance(getContext());
            }

            try {
                m.setBitmap(bitmaps[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog.show();
            dialog.show();
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            //progressDialog.dismiss();
            dialog.dismiss();
            Snackbar.make(imageView, "Set Wallpaper successfully", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    class Task extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            result = bitmap;
            imageView.setImageBitmap(result);
            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlz = a;
            try {
                //org.jsoup.nodes.Document document = Jsoup.connect(url[i]).get();
                org.jsoup.nodes.Document document = Jsoup.connect(urlz).get();
                if (document != null) {

                    //neu no ton tai gia tri
                    elements = document.select("div.wallpaper__placeholder");
                    for (Element element : elements) {
                        Element elementten = element.getElementsByTag("a").first();
                        Element elementhinh = element.getElementsByTag("img").first();
                        if (elementten != null) {
                            ten = elementten.attr("href");
                            // y="https:";
                            t = ten;
                        }
                        if (elementhinh != null) {
                            hinh = elementhinh.attr("src");
                            // y="https:";
                            g = hinh;
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(g).getContent());
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


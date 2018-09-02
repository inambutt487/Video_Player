package com.example.bigbites.audioplayer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends AppCompatActivity {
    TextView textView ;
    ListView listView ;
    Bitmap[] bmThumbnail;
    ImageView imageView ;
    int currentPosition ;
    Handler handler;
    Uri uri[] ;
    String[] sItems ;
    MyListViewAdapter myListViewAdapter ;

    @RequiresApi(api = 26)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler=new Handler();
        listView = (ListView)findViewById(R.id.musicPlaylist);
        textView = (TextView)findViewById(R.id.textView2);
        imageView = (ImageView)findViewById(R.id.imageView);
        final ArrayList<File> mySongs = findSongs(Environment.getExternalStorageDirectory()) ;
        sItems = new String[mySongs.size()] ;
        uri = new Uri[mySongs.size()] ;
        bmThumbnail = new Bitmap[mySongs.size()] ;
        for (int i = 0 ; i < mySongs.size(); i++){
            sItems[i] =  mySongs.get(i).getName().toString().replace(".mp4","") ;
                textView.setText("Total Videos: "+ mySongs.size());
           uri[i] =  Uri.fromFile(new File(mySongs.get(i).toString()));
        }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    myListViewAdapter = new MyListViewAdapter(MainActivity.this, sItems, uri);
                    listView.setAdapter(myListViewAdapter);
//                    currentPosition = listView.getFirstVisiblePosition();
//                    listView.setSelection(myListViewAdapter.getCount() - 1);
                }
            }).start();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    listView.setSelection(currentPosition);
                    listView.setAdapter(myListViewAdapter);
                }
            }, 1000);
//        }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getApplicationContext(),Player.class).putExtra("pos",i).putExtra("songList",mySongs));
            }
        });

    }

    public ArrayList<File> findSongs(File root) {
        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File singleFile : files) {
            if(singleFile.isDirectory()&&!singleFile.isHidden()){
                al.addAll(findSongs(singleFile));
        }
            else {
                if (singleFile.getName().endsWith(".mp4") || singleFile.getName().endsWith(".flv") || singleFile.getName().endsWith(".mmv") || singleFile.getName().endsWith(".mov") ||singleFile.getName().endsWith(".avi")){
                    al.add(singleFile);
                }
            }
    }
        return al;
    }

    }
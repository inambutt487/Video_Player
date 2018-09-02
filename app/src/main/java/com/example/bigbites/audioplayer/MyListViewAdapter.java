package com.example.bigbites.audioplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by bigbites on 9/13/2017.
 */

public class MyListViewAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private String[] progName;
    private final Uri[] uri;
    private final Integer[] ivImg ;
    private final Bitmap[] bmThumbnail ;
    Drawable[] drawable ;

    public MyListViewAdapter(Activity context, String[] progName, Uri[] uri) {
        super(context, R.layout.songs_list, progName);
        this.context = context;
        this.progName = progName;
        this.uri = uri;
        ivImg = new Integer[progName.length] ;
        bmThumbnail =new Bitmap[progName.length] ;
    }

    @Override
    public int getCount() {
        return progName.length;
    }

    @Override
    public String getItem(int position) {
        return Integer.toString(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


//    Original getView()

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        for (int i = 0; i < progName.length; i++){
            final int finalI = i;

            Glide
                    .with(context)
                    .load(uri[i])
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(40,40) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            bmThumbnail[finalI] = resource ;

                        }
                    });
//          Drawable drawable = new BitmapDrawable(bmThumbnail[i] );
        }
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View layoutView = layoutInflater.inflate(R.layout.songs_list, null, true);
        TextView textView = (TextView) layoutView.findViewById(R.id.textView);
        ImageView imageView = (ImageView) layoutView.findViewById(R.id.imageView);
        textView.setText(progName[position]);

            textView.setTextColor(Color.WHITE);
            textView.setText(progName[position]);
            textView.setBackgroundColor(Color.CYAN);
            int color = Color.argb( 150, 0, 64, 64 );
            textView.setBackgroundColor( color );


        imageView.setBackground(new BitmapDrawable(bmThumbnail[position]));
//        imageView.setImageBitmap(bmThumbnail[position]);
        return layoutView;
    }
}


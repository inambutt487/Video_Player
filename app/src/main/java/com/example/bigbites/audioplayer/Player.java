package com.example.bigbites.audioplayer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity{
    Button btn , shareButton;
    Context context ;
    ArrayList<File> mySongs ;
    int position ;
    Uri uri ;
    int i ,globalValue, j, k, globalBrightnessValue;
    Integer value ;
    VideoView videoView ;
    private AudioManager audioManager = null;
    private HoloCircleSeekBar seekBar, seekBar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player1);
        context  = getApplicationContext() ;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        i = 0 ; j = 0 ; k = 0 ;
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        mySongs = (ArrayList) b.getParcelableArrayList("songList");
        position = b.getInt("pos") ;
        final String title = mySongs.get(position).toString().replace(".mp4", "");
//        uri = Uri.parse(mySongs.get(position).toString());
        uri = Uri.fromFile(new File(mySongs.get(i).toString()));
//        uri[i] =  Uri.fromFile(new File(mySongs.get(i).toString()));

        seekBar = (HoloCircleSeekBar) findViewById(R.id.picker2);
        shareButton = (Button) findViewById(R.id.shareUs);
        btn = (Button) findViewById(R.id.screenrotat);
        seekBar1 = (HoloCircleSeekBar) findViewById(R.id.picker1);
        value = Settings.System.getInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,0);
//        Code to share my Video via diffrent channels

        shareButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

//                        File f = new File("/sdcard/VID_20161201123613.mp4");
//                        Uri uriPath = Uri.parse(f.getPath());

                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, title);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.setType("video/*");
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(shareIntent, "send"));

                    }
                }
        );





//        Audio Player Code


//        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
//        mediaPlayer.start();
//        ********************************************************************************************

//        Video Player Code

        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(uri);



        MediaController mediaController = new MediaController(this);

        mediaController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //next button clicked
                videoView.pause();
                videoView.stopPlayback();
                position = ((position+1)%mySongs.size());
                uri = Uri.parse(mySongs.get(position).toString());
                videoView.setVideoURI(uri);
                videoView.start();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //previous button clicked
                videoView.pause();
                videoView.stopPlayback();
                position = ((position-1)%mySongs.size());
                uri = Uri.parse(mySongs.get(position).toString());
                videoView.setVideoURI(uri);
                videoView.start();
            }
        });

        videoView.setMediaController(mediaController);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // not playVideo
                // playVideo();

                videoView.pause();
                videoView.stopPlayback();
                position = ((position+1)%mySongs.size());
                uri = Uri.parse(mySongs.get(position).toString());
                videoView.setVideoURI(uri);
                videoView.start();
            }
        });
    }

    public void showSeekBar(View view) {
        globalBrightnessValue = Settings.System.getInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,0);
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        seekBar.setValue(globalBrightnessValue);
        if (seekBar1.getVisibility() == View.VISIBLE)
        {
            seekBar1.setVisibility(View.GONE);
            k = 0 ;
            if (j == 0) {
                seekBar.setVisibility(View.VISIBLE);
                j = 1;
            } else {
                seekBar.setVisibility(View.GONE);
                j = 0;
            }
        }
        else {
            if (j == 0) {
                seekBar.setVisibility(View.VISIBLE);
                j = 1;
            } else {
                seekBar.setVisibility(View.GONE);
                j = 0;
            }
        }
        seekBar.setOnSeekBarChangeListener(new HoloCircleSeekBar.OnCircleSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(HoloCircleSeekBar seekBar) {
                // Nothing to do
            }

            @Override
            public void onStopTrackingTouch(HoloCircleSeekBar seekBar) {
                        seekBar.setVisibility(View.GONE);
                j = 0 ;
                // Nothing to do
            }

            @Override
            public void onProgressChanged(HoloCircleSeekBar seekBar, int progress, boolean fromUser) {
//                        Toast.makeText(getBaseContext(),Integer.toString(progress),Toast.LENGTH_SHORT).show();
//                        value.setText(String.valueOf(progress));
                globalBrightnessValue = progress ;
                Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,progress*3);
      }

        });

    }

//    ****************************************************************************************************************************************************
//    function for sound control

    public void volumeControl(View view) {
        if (seekBar.getVisibility() == View.VISIBLE) {
            seekBar.setVisibility(View.GONE);
            j = 0 ;
            if (k == 0) {
                seekBar1.setVisibility(View.VISIBLE);
                k = 1;
            } else {
                seekBar1.setVisibility(View.GONE);
                k = 0;
            }
        }
        else {
            if (k == 0) {
                seekBar1.setVisibility(View.VISIBLE);
                k = 1;
            } else {
                seekBar1.setVisibility(View.GONE);
                k = 0;
            }
        }
        seekBar1.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamVolume(AudioManager.STREAM_MUSIC),0);
        seekBar1.setValue(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        seekBar1.setOnSeekBarChangeListener(new HoloCircleSeekBar.OnCircleSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(HoloCircleSeekBar seekBar) {
                // Nothing to do
            }

            @Override
            public void onStopTrackingTouch(HoloCircleSeekBar seekBar) {
                seekBar1.setVisibility(View.GONE);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        globalValue, 0);
                k = 0 ;
                // Nothing to do
            }

            @Override
            public void onProgressChanged(HoloCircleSeekBar seekBar, int progress, boolean fromUser) {
                globalValue = progress ;
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
            }

        });
    }
//    end of volumeControl function


//    ***********************************************************************
//    onClick function for orientation
    public void display(View view){
        if (i == 0) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            i = 1 ;
        }
        else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            i = 0 ;
        }
    }


//    Function for video sharing is commented below

//    public void shareVideo(final String title, String path) {
//
//        MediaScannerConnection.scanFile(context, new String[] { path },
//
//                null, new MediaScannerConnection.OnScanCompletedListener() {
//                    public void onScanCompleted(String path, Uri uri) {
//                        Intent shareIntent = new Intent(
//                                android.content.Intent.ACTION_SEND);
//                        shareIntent.setType("video/*");
//                        shareIntent.putExtra(
//                                android.content.Intent.EXTRA_SUBJECT, title);
//                        shareIntent.putExtra(
//                                android.content.Intent.EXTRA_TITLE, title);
//                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//                        shareIntent
//                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//                        context.startActivity(Intent.createChooser(shareIntent,
//                                "Share This video"));
//
//                    }
//                });
//    }





//    public void shareVideo(View view) {
//    }
//    end of display function

}

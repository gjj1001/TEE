package com.tee686.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.casit.tee686.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

/**
 * Created by Jason on 13-7-5.
 */
public class MainActivity extends Activity {
    private VideoView vv1 = null;
    private VideoView vv2 = null;
    private VideoView vv3 = null;
    private VideoView vv4 = null;
    private ImageButton ib = null;
    private Dialog dialog;
    private View dialogView = null;
    private VideoView vv =null;
    private final Timer timer = new Timer();

    @Override
    protected void onResume() {
        super.onResume();
//        vv1.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mea1));
        vv1.start();
        vv1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                vv1.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.xinzang3d));
                vv1.start();
            }
        });

//        vv2.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mea1));
        vv2.start();
        vv2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                vv2.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.xinzang3d));
                vv2.start();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //弹出引导动画
        dialogView = getLayoutInflater().inflate(R.layout.demo_index, null);
        if (dialogView != null) {
            vv = (VideoView) dialogView.findViewById(R.id.demo_index_vv);
        }
        vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.demo));
        dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        vv.start();

        dialog.getWindow().setLayout(this.getWindowManager().getDefaultDisplay().getWidth(),this.getWindowManager().getDefaultDisplay().getHeight());
        dialog.show();
        //使dialog弹出后屏幕不变暗
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount =0f;
        window.setAttributes(lp);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                dialog.dismiss();
                timer.cancel();
            }
        },10000);

        vv1 = (VideoView)findViewById(R.id.vv1_mea_aa_lax);
        vv2 = (VideoView)findViewById(R.id.vv2_mea_aa_lax);
        vv3 = (VideoView)findViewById(R.id.vv3_mea_aa_lax);
        vv4 = (VideoView)findViewById(R.id.vv4_mea_aa_lax);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                vv1.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.xinzang3d));
                vv1.start();
                vv1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        vv1.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.xinzang3d));
                        vv1.start();
                    }
                });
            }
        });

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                vv2.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.xinzang3d));
                vv2.start();
                vv2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        vv2.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.xinzang3d));
                        vv2.start();
                    }

                });

            }
        });

        /*Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                vv3.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mea1));
                vv3.start();
                vv3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        vv3.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mea1));
                        vv3.start();
                    }
                });
            }
        });*/

        /*Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                vv4.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mea1));
                vv4.start();
                vv4.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        vv4.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mea1));
                        vv4.start();
                    }

                });

            }
        });*/

        ib = (ImageButton)findViewById(R.id.imgbtn_enter);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Section686Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });



    }


}

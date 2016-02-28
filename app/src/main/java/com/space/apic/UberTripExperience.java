package com.space.apic;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FlipInRightYAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInLeftAnimator;

public class UberTripExperience extends FragmentActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler mHandler = new Handler();
    private ImageView car;
    private ArrayList<ChallengeCardData> data = new ArrayList<ChallengeCardData>();
    private UberTripExperience activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uber_trip_experience);

        Bundle bundle = getIntent().getExtras();
        final String merchantName = bundle.getString("merchantName");
        TextView merchantLabel = (TextView) findViewById(R.id.merchant_name);
        merchantLabel.setText(merchantName);

        VideoView video_player_view = (VideoView) findViewById(R.id.video_player);
        String fileName = "android.resource://"+  getPackageName() + "/raw/ubervideo";
        video_player_view.setVideoURI(Uri.parse(fileName));
        video_player_view.start();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        car = (ImageView) findViewById(R.id.car);
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressStatus++;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            car.setX(car.getX()+9);
                            progressBar.setProgress(progressStatus);
                        }
                    });
                }
                Snackbar snack = Snackbar.make(progressBar, "You have arrived! Start your challenge!", Snackbar.LENGTH_LONG);
                View view = snack.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                snack.setAction("Action", null).show();
            }
        }).start();

        RecyclerView gallery = (RecyclerView) findViewById(R.id.gallery);
        createPlaceholderData();
        UberGalleryCardAdapter adapter = new UberGalleryCardAdapter(data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        gallery.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        final ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        scaleAdapter.setFirstOnly(false);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        gallery.setItemAnimator(new FlipInRightYAnimator());
        gallery.getItemAnimator().setChangeDuration(3000);
        gallery.setAdapter(scaleAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                LayoutInflater inflater = activity.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.chat,null));
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void createPlaceholderData(){
        data.add(new ChallengeCardData("Chocolate Origin", "30 mins left", "Chocolate Origin", "0.4 mi", "Chocolate Origin",String.valueOf(R.drawable.challenge_chocolate_origin)));
        data.add(new ChallengeCardData("The Black Horse", "15 mins left", "The Black Horse", "0.9 mi", "The Black Horse", String.valueOf(R.drawable.challenge_bar)));
        data.add(new ChallengeCardData("Love With Burgers", "27 mins left", "Love With Burgers", "5.4 mi", "Love With Burgers",String.valueOf(R.drawable.challenge_burger)));
        data.add(new ChallengeCardData("Diablo's Wings", "12 days left", "Diablo's Wings", "0.4 mi", "Diablo's Wings",String.valueOf(R.drawable.challenge_wings)));
        data.add(new ChallengeCardData("Sichuan Hotpot", "30 days left", "Sichuan Hotpot", "5.4 mi", "Sichuan Hotpot",String.valueOf(R.drawable.challenge_hotpot)));
        data.add(new ChallengeCardData("Chocolate Origin", "30 mins left", "Chocolate Origin", "0.4 mi", "Chocolate Origin",String.valueOf(R.drawable.challenge_chocolate_origin)));
        data.add(new ChallengeCardData("The Black Horse", "15 mins left", "The Black Horse", "0.9 mi", "The Black Horse", String.valueOf(R.drawable.challenge_bar)));
        data.add(new ChallengeCardData("Love With Burgers", "27 mins left", "Love With Burgers", "5.4 mi", "Love With Burgers",String.valueOf(R.drawable.challenge_burger)));
        data.add(new ChallengeCardData("Diablo's Wings", "12 days left", "Diablo's Wings", "0.4 mi", "Diablo's Wings",String.valueOf(R.drawable.challenge_wings)));
        data.add(new ChallengeCardData("Sichuan Hotpot", "30 days left", "Sichuan Hotpot", "5.4 mi", "Sichuan Hotpot",String.valueOf(R.drawable.challenge_hotpot)));
        data.add(new ChallengeCardData("Chocolate Origin", "30 mins left", "Chocolate Origin", "0.4 mi", "Chocolate Origin",String.valueOf(R.drawable.challenge_chocolate_origin)));
        data.add(new ChallengeCardData("The Black Horse", "15 mins left", "The Black Horse", "0.9 mi", "The Black Horse", String.valueOf(R.drawable.challenge_bar)));
        data.add(new ChallengeCardData("Love With Burgers", "27 mins left", "Love With Burgers", "5.4 mi", "Love With Burgers",String.valueOf(R.drawable.challenge_burger)));
        data.add(new ChallengeCardData("Diablo's Wings", "12 days left", "Diablo's Wings", "0.4 mi", "Diablo's Wings",String.valueOf(R.drawable.challenge_wings)));
        data.add(new ChallengeCardData("Sichuan Hotpot", "30 days left", "Sichuan Hotpot", "5.4 mi", "Sichuan Hotpot",String.valueOf(R.drawable.challenge_hotpot)));
        data.add(new ChallengeCardData("Chocolate Origin", "30 mins left", "Chocolate Origin", "0.4 mi", "Chocolate Origin",String.valueOf(R.drawable.challenge_chocolate_origin)));
        data.add(new ChallengeCardData("The Black Horse", "15 mins left", "The Black Horse", "0.9 mi", "The Black Horse", String.valueOf(R.drawable.challenge_bar)));
        data.add(new ChallengeCardData("Love With Burgers", "27 mins left", "Love With Burgers", "5.4 mi", "Love With Burgers",String.valueOf(R.drawable.challenge_burger)));
        data.add(new ChallengeCardData("Diablo's Wings", "12 days left", "Diablo's Wings", "0.4 mi", "Diablo's Wings",String.valueOf(R.drawable.challenge_wings)));
        data.add(new ChallengeCardData("Sichuan Hotpot", "30 days left", "Sichuan Hotpot", "5.4 mi", "Sichuan Hotpot",String.valueOf(R.drawable.challenge_hotpot)));

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uber_trip_experience, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

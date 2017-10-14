package com.ashraf.mathskilltester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Result extends AppCompatActivity {

    TextView tvSetStatus,tvSetLevel,tvSetTotal;
    Button btnNext,btnHome;

    private String mStatus,mTotal="0",mLevel;
    boolean tryAgain = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initializeAll();
        addIniTialiZe();

        mStatus = getIntent().getStringExtra("status");
        mTotal = getIntent().getStringExtra("total");
        mLevel = getIntent().getStringExtra("level");

        tvSetStatus.setText("Congratulation You Passed");
        tvSetTotal.setText("Total Point : "+mTotal);


        switch (mStatus){

            case "unComplete":
                if (mLevel.equals("1")){
                    tvSetStatus.setText("You Are Too Weak In Mathematics");
                    tvSetLevel.setText("!! Try Again !!");

                }else if (mLevel.equals("2")){
                    tvSetStatus.setText("You Have Good Math Skill You Can Complete It");
                    tvSetLevel.setText("!! Try It Again !!");
                }else if (mLevel.equals("3")){
                    tvSetStatus.setText("Excellent Skill But You Are Not Genius");
                    tvSetLevel.setText("!! Try It Again !!");
                }else{
                    tvSetStatus.setText("You Are Genius You Can Complete It");
                    tvSetLevel.setText("!! Try It Again !!");
                }
                btnNext.setText("Try Again");
                tryAgain = true;
                break;
            case "beginner":
                tvSetLevel.setText("!! Beginner Level !!");
                break;
            case "intermediate":
                tvSetLevel.setText("!! Intermediate Level !!");
                break;
            case "expert":
                tvSetLevel.setText("!! Expert Level !!");
                break;
            case "extraordinary":
                tvSetLevel.setText("!! ExtraOrdinary Level !! \n Your Math Skill Really Awesome");
                btnNext.setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }

    private void addIniTialiZe() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2122789248840144~6880002112");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
    public void initializeAll(){

        tvSetStatus = (TextView) findViewById(R.id.text_status);
        tvSetLevel = (TextView) findViewById(R.id.text_level_complete);
        tvSetTotal = (TextView) findViewById(R.id.text_total);
        btnHome = (Button) findViewById(R.id.button_home);
        btnNext = (Button) findViewById(R.id.button_next);

    }

    public void next(View v){

        Intent intent = new Intent(Result.this,Game.class);
        if (tryAgain){
            tryAgain = false;
            intent.putExtra("level",mLevel);
            intent.putExtra("total","0");
        }else{
            int temp = Integer.parseInt(mLevel);
            mLevel = Integer.toString(temp+1);
            intent.putExtra("level",mLevel);
            intent.putExtra("total",mTotal);
        }
        startActivity(intent);
        finish();
    }
    public void home(View v){
        startActivity(new Intent(Result.this,MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.about,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.menu_about:
                startActivity(new Intent(Result.this,About.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

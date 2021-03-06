package com.ashraf.mathskilltest;


/**
 * App Name : Math Skill Tester(MTS)
 * date : 20.09.16
 * Author : Syed ashraf Ullah
 * Email : ashraf789@diu.edu.bd
 * */
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private String  level = "0";
    private Button btnBeginner,btnIntermediate, btnExpert,btnExtraordinary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        addInitialize();
        initialize();
    }

//    private void addInitialize() {
//        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2122789248840144~6880002112");
//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//    }

    @Override
    public void onClick(View v) {
        if (v == btnBeginner){
            level = "1";
            goLevel(level);
        }else if (v == btnIntermediate){
            level = "2";
            goLevel(level);
        }else if (v == btnExpert){
            level = "3";
            goLevel(level);
        }else if (v == btnExtraordinary){
            level = "4";
            goLevel(level);
        }

    }

    // Go To GameActivity
    public void goLevel(String level){

        Intent intent = new Intent(MainActivity.this,Game.class);
        intent.putExtra("level",level);
        intent.putExtra("total","0");
        startActivity(intent);
    }

    // initialize all
    public void initialize(){
        btnBeginner =  findViewById(R.id.btn_beginner);
        btnIntermediate =  findViewById(R.id.btn_intermediate);
        btnExpert =  findViewById(R.id.btn_expert);
        btnExtraordinary =  findViewById(R.id.btn_extraordinary);

        btnBeginner.setOnClickListener(this);
        btnIntermediate.setOnClickListener(this);
        btnExpert.setOnClickListener(this);
        btnExtraordinary.setOnClickListener(this);
    }

}

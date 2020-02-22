package com.ashraf.mathskilltest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


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

        mStatus = getIntent().getStringExtra("status");
        mTotal = getIntent().getStringExtra("total");
        mLevel = getIntent().getStringExtra("level");

        tvSetStatus.setText("Congratulation You Passed");
        tvSetTotal.setText("Total Point : "+mTotal);

        if (mStatus == null) return;
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

    public void initializeAll(){

        tvSetStatus =  findViewById(R.id.text_status);
        tvSetLevel =  findViewById(R.id.text_level_complete);
        tvSetTotal =  findViewById(R.id.text_total);
        btnHome =  findViewById(R.id.button_home);
        btnNext =  findViewById(R.id.button_next);

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

}

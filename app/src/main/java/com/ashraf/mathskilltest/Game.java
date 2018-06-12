package com.ashraf.mathskilltest;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

public class Game extends AppCompatActivity {


    private TextView tvQuestion;
    private TextView tvChance, tvTotal, tvTime, tvTemporary;
    private EditText editAnswer;
    private Random random = new Random();
    private Button buttonSkip;

    private String userAnswer;
    private int randomNumber1, randomNumber2, randomInstruction;
    private int correctAnswer;
    private int mTotal = 0, mChance = 3, temp = 0;
    private int temporary;

    private long wasteTime;
    private String level = "0";
    private boolean flag;
    private boolean timeOut = false;
    private String status = "";

    private Long previousTime;
    private Long currentTime;
    private Handler handler = new Handler();
    private Toolbar toolbar;


    private MediaPlayer mpRight,mpWrong,mpGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initialize();
        addInitialize();
        setSupportActionBar(toolbar);
        level = getIntent().getStringExtra("level");
        mTotal += Integer.parseInt(getIntent().getStringExtra("total"));
        temporary = mTotal;
        startLevel(level);

    }
    // Initialize all
    public void initialize() {
        tvQuestion =  findViewById(R.id.text_question);
        editAnswer =  findViewById(R.id.edit_answer);
        tvChance =  findViewById(R.id.text_chance);
        tvTotal =  findViewById(R.id.text_total);
        tvTime =  findViewById(R.id.text_time);
        tvTemporary =  findViewById(R.id.text_temp_show);
        buttonSkip =  findViewById(R.id.button_skip);

        mpRight = MediaPlayer.create(this,R.raw.right_02);
        mpWrong = MediaPlayer.create(this,R.raw.wrong_ans_01);
        mpGameOver = MediaPlayer.create(this,R.raw.game_over_01);

        toolbar = findViewById(R.id.toolbar);
    }

    private void addInitialize() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2122789248840144~6880002112");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    //start level
    public void startLevel(String level) {
        switch (level) {

            case "1":
                beginner();
                break;
            case "2":
                intermediate();
                break;
            case "3":
                expert();
                break;
            case "4":
                extraordinary();
                break;
            default:
                break;
        }
    }

    //Beginner Level
    public void beginner() {

        generateInstruction();
        generateRandom(10, 10);
        setQuestion();
        timeRemain(8);
        takeAnswer();
    }

    //Intermediate Level
    public void intermediate() {
        generateInstruction();
        generateRandom(10, 15);
        setQuestion();
        timeRemain(8);
        takeAnswer();
    }

    //Expert Level
    public void expert() {
        generateInstruction();
        generateRandom(15, 20);
        setQuestion();
        timeRemain(8);
        takeAnswer();
    }

    //Extraordinary Level
    public void extraordinary() {
        generateInstruction();
        generateRandom(20, 25);
        setQuestion();
        timeRemain(8);
        takeAnswer();
    }

    //Game Over
    public void gameOver() {

        mpGameOver.start();
        changeFlag();//set flag = false
        flag = true;

        if (mTotal == 0) status = "unComplete";
        Intent intent = new Intent(Game.this, Result.class);
        intent.putExtra("status", status);
        intent.putExtra("total", Integer.toString(mTotal));//convert integer to string
        intent.putExtra("level", level);
        startActivity(intent);
        finish();
    }

    // Generate Random Number
    public void generateRandom(int baseForRandom1, int baseForRandom2) {

        // used do while loop  because if random number == 0 then it generate again
        do {
            randomNumber1 = random.nextInt(baseForRandom1);
        } while (randomNumber1 == 0);

        do {
            randomNumber2 = random.nextInt(baseForRandom2);
        } while (randomNumber2 == 0);

        if (randomNumber1 < randomNumber2 && randomInstruction == 2) { // if 20 - 10 then after swap 10-20
            swapRandomNumber(randomNumber1, randomNumber2);
        } else if (randomNumber1 > randomNumber2 && randomInstruction == 4) { // if 20/10 then after swap 10/20
            swapRandomNumber(randomNumber1, randomNumber2);
            if ((randomNumber1 / randomNumber2) != 0) {

            }
        }
    }

    // Generate Random Instruction
    public void generateInstruction() {

        randomInstruction = random.nextInt(4);

        switch (randomInstruction) {
            case 0:
                randomInstruction = 1;
                break;
            case 1:
                randomInstruction = 2;
                break;
            case 2:
                randomInstruction = 2;
                break;
            case 3:
                randomInstruction = 3;
                break;
            case 4:
                randomInstruction = 4;
                break;
            default:
                randomInstruction = 1;
                break;
        }
    }

    // Set Question
    public void setQuestion() {

        switch (randomInstruction) {
            case 1:
                correctAnswer = randomNumber1 + randomNumber2;
                tvQuestion.setText(randomNumber1 + " + " + randomNumber2);
                break;
            case 2:
                correctAnswer = randomNumber1 - randomNumber2;
                tvQuestion.setText(randomNumber1 + " - " + randomNumber2);
                break;
            case 3:
                correctAnswer = randomNumber1 * randomNumber2;
                tvQuestion.setText(randomNumber1 + " * " + randomNumber2);
                break;
            case 4:
                if ((randomNumber1 % randomNumber2) != 0) {
                    randomNumber1 = lcm(randomNumber1, randomNumber2);
                }
                correctAnswer = randomNumber1 / randomNumber2;
                tvQuestion.setText(randomNumber1 + " / " + randomNumber2);

                break;

            default:
                break;
        }
        editAnswer.setText("");
    }

    private int lcm(int a, int b) {
        int num1 = a;
        int num2 = b;
        while (num2 > 0) {
            temp = num2;
            num2 = num1 % num2;
            num1 = temp;
        }
        int gcd = num1;

        return (a * b) / gcd;
    }

    public void takeAnswer() {

        editAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvTemporary.setVisibility(View.VISIBLE);
                tvTemporary.setText(charSequence);
                editAnswer.setBackgroundColor(Color.parseColor("#f4ff81"));
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        editAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                tvTemporary.setVisibility(View.GONE);
                userAnswer = editAnswer.getText().toString();
                tvTemporary.setVisibility(View.GONE);
                if (userAnswer.equals("")) {
                    Toast.makeText(Game.this, "Empty", Toast.LENGTH_SHORT).show();
                    takeAnswer();
                } else {
                    changeFlag(); // set flag == false so timeRemain Thread will be stop
                    checkAnswer();
                }

                return false;
            }
        });

    }

    // Change total,chance etc status
    public void checkAnswer() {

        userAnswer = editAnswer.getText().toString();
        long ans = Integer.parseInt(userAnswer);

        if (ans == correctAnswer) {

            switch (randomInstruction) {
                case 1:
                    mTotal += 1;
                    break;
                case 2:
                    mTotal += 2;
                    break;
                case 3:
                    mTotal += 3;
                    break;
                case 4:
                    mTotal += 4;
                    break;
                default:
                    break;
            }

            Toast.makeText(Game.this, "Right Answer", Toast.LENGTH_LONG).show();
            mpRight.start();
            sleep(1, "#76ff03"); // before next action it will be take 1 second
            tvTotal.setText("Total = " + mTotal);


        } else {

            mpWrong.start();
            Toast.makeText(Game.this, "Wrong Answer", Toast.LENGTH_LONG).show();
            sleep(1, "#ff4081"); // before next action it will be take 3 second

            mChance -= 1;
            tvChance.setText("Chance = " + mChance);
            if (mChance < 0) {
                status = "unComplete";
                tvChance.setText("Game Over");
                gameOver();
            }
        }

        levelChanger();
        startLevel(level);
    }

    // Swap Between Two Number
    public void swapRandomNumber(int num1, int num2) {
        randomNumber1 = num2;
        randomNumber2 = num1;
    }

    public void skip(View v) {
        changeFlag();
        mChance--;
        if (mChance < 0) {
            status = "unComplete";
            Toast.makeText(Game.this, "Game Over: ", Toast.LENGTH_SHORT).show();
            gameOver();
        } else if (timeOut) {//it work if timeRemain is disable and user press next(skip) button
            editAnswer.setEnabled(true);
            editAnswer.setFocusable(true);
            editAnswer.setFocusableInTouchMode(true);
            buttonSkip.setText("Skip");
            timeOut = false;
        }

        if (mChance > -1) {

            Toast.makeText(Game.this, "Chance Left : " + mChance, Toast.LENGTH_SHORT).show();
            tvChance.setText("Chance : " + mChance);
        }

        startLevel(level);

    }
    // if need sleep
    public void sleep(final int time, final String colorCode) {

        previousTime = SystemClock.uptimeMillis();

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        editAnswer.setBackgroundColor(Color.parseColor(colorCode));
                        currentTime = SystemClock.uptimeMillis();
                        wasteTime = (currentTime - previousTime) / 1000;

                        if (wasteTime > time) {

                            editAnswer.setBackgroundColor(Color.parseColor("#f4ff81"));// default edit background

                            Thread.currentThread().interrupt();
                            return;
                        }

                        handler.postDelayed(this, 0);

                    }

                });
                return;
            }
        }).start();
    }

    // Time Change /Time Left
    public void timeRemain(final long time) {


        previousTime = SystemClock.uptimeMillis();
        flag = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        currentTime = SystemClock.uptimeMillis();
                        wasteTime = (currentTime - previousTime) / 1000;

                        tvTime.setText("Time Left : " + (time - wasteTime) + " Sec");


                        if (wasteTime > time) {
                            tvTime.setText("Time Out");

                            buttonSkip.setText("Next");
                            editAnswer.setFocusable(false);// set user input = disable
                            editAnswer.setEnabled(false);
                            tvTemporary.setVisibility(View.GONE);
                            Toast.makeText(Game.this, "Time Out", Toast.LENGTH_SHORT).show();
                            timeOut = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Log.d("Stop Thread", "Thread Stop--------------");
                            Thread.currentThread().interrupt();
                            return;


                        } else if (flag == false) {
                            Thread.currentThread().interrupt();
                            return;
                        }

                        Log.d("Thread Alive", "run:---------------------- ");
                        handler.postDelayed(this, 0);
                    }
                });

                return;
            }
        }).start();
    }

    // change level if level is completed
    public void levelChanger() {

        int levelTotal = mTotal - temporary;//present level total point

        if (levelTotal >= 50 && level.equals("1")) {
            status = "beginner";
            gameOver();
        } else if (levelTotal >= 70 && level.equals("2")) {

            status = "intermediate";
            gameOver();
        } else if (levelTotal >= 90 && level.equals("2")) {

            status = "expert";
            gameOver();
        } else if (levelTotal >= 150) {
            status = "extraordinary";
            gameOver();
        } else
            status = "unComplete";


    }

    public void changeFlag() {
        flag = false;
    }


    @Override
    protected void onPause() {
        //changeFlag();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

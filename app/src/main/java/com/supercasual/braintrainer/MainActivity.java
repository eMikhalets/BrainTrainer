package com.supercasual.braintrainer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int SIGN_ADD = 0;
    private static final int SIGN_SUBS = 1;

    private CountDownTimer timer;
    private List<Button> btnList;

    private TextView textAnswers;
    private TextView textTimer;
    private TextView textExample;
    private Button btnAnswer1;
    private Button btnAnswer2;
    private Button btnAnswer3;
    private Button btnAnswer4;

    private int rightCount;
    private int wrongCount;
    private int rightAnswer;
    private int difficult = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textAnswers = findViewById(R.id.main_text_answers);
        textTimer = findViewById(R.id.main_text_timer);
        textExample = findViewById(R.id.main_text_example);
        btnAnswer1 = findViewById(R.id.btn_main_answer1);
        btnAnswer2 = findViewById(R.id.btn_main_answer2);
        btnAnswer3 = findViewById(R.id.btn_main_answer3);
        btnAnswer4 = findViewById(R.id.btn_main_answer4);

        startGame();
    }

    @Override
    protected void onResume() {
        super.onResume();

        startGame();
    }

    public void onBtnClick(View view) {
        Button btn = (Button) view;
        if (btn.getText().equals(String.valueOf(rightAnswer))) {
            rightCount++;
            Toast.makeText(this, "Верно", Toast.LENGTH_SHORT).show();
        } else {
            wrongCount++;
            Toast.makeText(this, "Неверно", Toast.LENGTH_SHORT).show();
        }
        textAnswers.setText(rightCount + " / " + wrongCount);

        setExample();
    }

    private void startGame() {
        rightCount = 0;
        wrongCount = 0;

        startTimer();
        setExample();
    }

    private void startTimer() {
        timer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                if (seconds < 10) {
                    textTimer.setText("00:0" + seconds);
                } else {
                    textTimer.setText("00:" + seconds);
                }
            }

            @Override
            public void onFinish() {
                timer.cancel();
                Intent intent = new Intent(MainActivity.this, EndGameActivity.class);
                intent.putExtra("currentScore", rightCount);
                startActivity(intent);
            }
        }.start();
    }

    private void setExample() {
        btnList = new ArrayList<>();
        btnList.add(btnAnswer1);
        btnList.add(btnAnswer2);
        btnList.add(btnAnswer3);
        btnList.add(btnAnswer4);

        Random random = new Random();
        rightAnswer = getRightAnswer();
        int btnListSize = btnList.size();

        for (int i = 0; i < btnListSize - 1; i++) {
            int randomBtn = random.nextInt(btnList.size());
            int wrongAnswer = getWrongAnswer();
            if (wrongAnswer == rightAnswer) {
                wrongAnswer += random.nextInt(5);
            }
            btnList.get(randomBtn).setText(String.valueOf(wrongAnswer));
            btnList.remove(randomBtn);
        }

        btnList.get(0).setText(String.valueOf(rightAnswer));
    }

    private int getRightAnswer() {
        Random random = new Random();
        int randomAnswer = 0;
        int random1 = random.nextInt(difficult) + 1;
        int random2 = random.nextInt(difficult) + 1;
        int randomSign = random.nextInt(2);

        String sign = "";
        switch (randomSign) {
            case SIGN_ADD:
                randomAnswer = random1 + random2;
                sign = " + ";
                break;
            case SIGN_SUBS:
                randomAnswer = random1 - random2;
                sign = " - ";
                break;
        }

        textExample.setText(random1 + sign + random2);
        return randomAnswer;
    }

    private int getWrongAnswer() {
        Random random = new Random();
        int randomAnswer = 0;
        int random1 = random.nextInt(difficult) + 1;
        int random2 = random.nextInt(difficult) + 1;
        int randomSign = random.nextInt(2);

        String sign = "";
        switch (randomSign) {
            case SIGN_ADD:
                randomAnswer = random1 + random2;
                break;
            case SIGN_SUBS:
                randomAnswer = random1 - random2;
                break;
        }

        return randomAnswer;
    }
}

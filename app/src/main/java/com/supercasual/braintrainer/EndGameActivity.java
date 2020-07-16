package com.supercasual.braintrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EndGameActivity extends AppCompatActivity {

    private TextView textCurrentScore;
    private TextView textMaxScore;
    private Button btnRestart;

    private int currentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        textCurrentScore = findViewById(R.id.text_endGame_currentScore);
        textMaxScore = findViewById(R.id.text_endGame_maxScore);
        btnRestart = findViewById(R.id.btn_endGame_restart);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        currentScore = intent.getIntExtra("currentScore", 0);
        textCurrentScore.setText("Текущий результат: " + currentScore);

        updateScore();
    }

    private void updateScore() {
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (sp.contains("bestScore")) {
            int bestScore = sp.getInt("bestScore", 0);

            if (bestScore < currentScore) {
                editor.putInt("bestScore", currentScore);
                editor.apply();
                bestScore = currentScore;
            }

            textMaxScore.setText("Лучший результат: " + bestScore);
        } else {
            editor.putInt("bestScore", currentScore);
            editor.apply();
        }
    }
}

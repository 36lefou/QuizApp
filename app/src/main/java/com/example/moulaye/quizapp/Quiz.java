package com.example.moulaye.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Quiz extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ = 1;

    public static final String KEY_HIGHSCORE = "keyHighscore";


    public int highscore;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });


    }
    public void startQuiz() {
        Intent intent = new Intent(Quiz.this, QuizActivity.class);
        intent.putExtra("highscore", highscore);

        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    public void Back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void highScoreQuiz(View view) {
        Intent intent = new Intent(this, HScoreQuiz.class);
        startActivity(intent);

    }
}

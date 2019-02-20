package com.example.moulaye.quizapp;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.text.SimpleDateFormat;

public class HScoreGeoQuiz extends AppCompatActivity {
    public int highscore;
    public String userName;
    TextView textHighScore;TextView textHSTime; TextView textUserHS;    private static final int REQUEST_CODE_QUIZ = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hscore_geo_quiz);
        textHighScore = (TextView) findViewById(R.id.highscoreGeo);
        textHSTime = (TextView) findViewById(R.id.timeHighScoreGeo);
        textUserHS =(TextView) findViewById((R.id.userHighScoreGeo));
        // recupere le high score
        SharedPreferences prefs = getSharedPreferences("geo_quizz_score", MODE_PRIVATE);
        highscore = prefs.getInt("score", 0);
        textHighScore.setText("Meilleur score: " + highscore);
        long dateLong = prefs.getLong("Date ", System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        textHSTime.setText("Date :"+ sdf.format(dateLong));
        userName = prefs.getString("user", null);
        textUserHS.setText("Par : "+userName);
    }

}




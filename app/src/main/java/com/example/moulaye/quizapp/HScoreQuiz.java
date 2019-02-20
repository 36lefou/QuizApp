package com.example.moulaye.quizapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import static android.support.constraint.Constraints.TAG;

public class HScoreQuiz extends AppCompatActivity {
    public int highscore;
    public String userName;
    TextView textHighScore;TextView textHSTime; TextView textUserHS;   private static final int REQUEST_CODE_QUIZ = 1;

  //  ExampleDialog ex=new ExampleDialog();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hscore_quiz);
        textHighScore = (TextView) findViewById(R.id.highscoreTxt);
        textHSTime = (TextView) findViewById(R.id.timeHighScoreTxt);
        textUserHS = (TextView) findViewById(R.id.userHighScoreTxt);
        // recupere le high score
        SharedPreferences prefs = getSharedPreferences("quizz_score", MODE_PRIVATE);
        highscore = prefs.getInt("score", 0);
        textHighScore.setText("Meilleur score : " + highscore);
        long dateLong = prefs.getLong("date", System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        textHSTime.setText("Date :"+ sdf.format(dateLong));
        userName = prefs.getString("user","");
        textUserHS.setText("Par: "+userName);


      //  String username = ex.userN();


        //System.out.println("yop"+username);
    }


}

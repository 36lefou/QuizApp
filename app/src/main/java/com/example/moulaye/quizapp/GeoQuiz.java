package com.example.moulaye.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GeoQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_quiz);
    }

    public void geoGame(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

    public void highScore(View view) {
        Intent intent = new Intent(this, HScoreGeoQuiz.class);
        startActivity(intent);
    }

    public void Back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}



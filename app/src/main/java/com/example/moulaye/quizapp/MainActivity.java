package com.example.moulaye.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button buttonGame1;
    Button closeAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGame1 = (Button) findViewById(R.id.buttonG1);
        closeAppButton = (Button) findViewById(R.id.closeApp);

        closeAppButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                System.exit(0);
            }
        });

    }

      public void geoQuiz(View v) {
            Intent intent = new Intent(this, GeoQuiz.class);
            startActivity(intent);
    }


    public void game(View view) {
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
    }
    public void closeApplication(View view) {
        finish();
        moveTaskToBack(true);
    }


}

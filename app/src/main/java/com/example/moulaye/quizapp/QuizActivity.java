package com.example.moulaye.quizapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class QuizActivity extends AppCompatActivity implements ExDialog.OnInputListener {
    private TextView textViewQuestion;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button buttonConfirmNext;
    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private int score;
    String username;

    @Override
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: got the input: " + input);

        //  mInputDisplay.setText(input);
        username = input;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        loadCSV2();
        //Lancement du fragment Username
        ExDialog exampleDialog = new ExDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");

        textViewQuestion = findViewById(R.id.text_view_question);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);



        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {

                        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
                        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

                        if (answerNr == Integer.parseInt(currentQuestion.getAnswerNr())) {
                            score++;


                            Toast.makeText(QuizActivity.this, "Bonne réponse!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(QuizActivity.this, "Mauvaise réponse", Toast.LENGTH_SHORT).show();
                        }

                        showNextQuestion();
                    }

            }
        });
}

    public void loadCSV2() {

        QuizDbHelper dbHelper = new QuizDbHelper(this);

        String mCSVfile = "datafile.csv";

        AssetManager manager = this.getAssets();
        InputStream inStream = null;
        try {
            Log.i(TAG, "loadCSV2: ");
            inStream = manager.open(mCSVfile);
        } catch (IOException e) {
            Log.i(TAG, "loadCSV2:  ERROR");
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line;
        try {
            while ((line = buffer.readLine()) != null) {
                String[] colums = line.split(",");

                Log.i("CSVParser", "Skipping Bad CSV Row");
                Log.i("CSVParser", "" + colums[1]);
                Question question = new Question();

                String pays = colums[0];
                String option1 = colums[1];
                String option2 = colums[2];
                String option3 = colums[3];
                String answer_nr = colums[4];
                String lat = colums[5];
                String lon = colums[6];

                question.setQuestion(pays);
                question.setOption1(option1);
                question.setOption2(option2);
                question.setOption3(option3);
                question.setAnswerNr(answer_nr);
                question.setLat(lat);
                question.setLon(lon);
                dbHelper.addQuestion(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showNextQuestion() {


        if (questionCounter < questionCountTotal) {
            System.out.println("count total : "+questionCountTotal);
            System.out.println("count"+questionCounter);
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;

            buttonConfirmNext.setText("Confirmer");


        } else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        SharedPreferences prefs = getSharedPreferences("quizz_score", MODE_PRIVATE);
        int highscore = prefs.getInt("score",0);
        if (score >= highscore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong("date",System.currentTimeMillis());
            editor.putInt("score", score);
            editor.putString("user",username);
            editor.commit();
        }

        finish();
    }
}

@SuppressLint("ValidFragment")
class ExDialog extends AppCompatDialogFragment {
    private EditText editTextUser;
    public  String username;
    public interface OnInputListener{
        void sendInput(String input);
    }
    public OnInputListener mOnInputList;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_diag, null);

        builder.setView(view)
                .setTitle("Entrer votre pseudonyme")
                .setPositiveButton("Jouer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialInterface, int i) {
                        username = editTextUser.getText().toString();
                        if(username != "") {
                            mOnInputList.sendInput(username);
                            dismiss();
                        }
                        else {
                            builder.setTitle("Entrer un Pseudo avant de jouer");

                        }

                    }
                });

        editTextUser = view.findViewById(R.id.edit_usernameQuiz);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputList = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }

}
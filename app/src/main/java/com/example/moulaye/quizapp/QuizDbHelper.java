package com.example.moulaye.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyQuiz.db";
    private static final int DATABASE_VERSION = 16;
    static SQLiteDatabase db;


    public QuizDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = ("CREATE TABLE 'quiz_table' ('ID'  INTEGER PRIMARY KEY AUTOINCREMENT, 'pays' TEXT, " +
                "'capitale' TEXT, 'lat' TEXT, 'lon' TEXT)");


        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS 'quiz_table'");

        onCreate(db);
    }



    public void addQuestion(Question question) {
        db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("pays", question.getQuestion());
        cv.put("capitale", question.getCapitale());
        cv.put("lat", question.getLat());
        cv.put("lon", question.getLon());
        db.insert("quiz_table", null, cv);

        db.close();

    }
    public ArrayList getlatLon(int id) {
        db = getReadableDatabase();
        ArrayList tab = new ArrayList();
        int ID = id;

        String query = "SELECT * FROM quiz_table where ID = ?";
        Cursor c1 = QuizDbHelper.db.rawQuery(query, new String[]{ID + ""});
        String lat = "";
        String lon = "";
        String nom = "";
        while (c1.moveToNext()) {
            lat = c1.getString(3);
            lon = c1.getString(4);
            nom = c1.getString(1);

            Log.i(TAG, "showNextQuestion: lat" + lat);


        }
        tab.add(0, lat+"");
        tab.add(1, lon+"");
        tab.add(2, nom+"");


        return tab;

    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM quiz_table", null);

        if (c.moveToFirst()) {
            do {
                if (questionList.size()<5) {
                    Question question = new Question();
                    question.setQuestion("C'est quoi la capitale de " + c.getString(c.getColumnIndex("pays")));
                    question.setCapitale(c.getString(c.getColumnIndex("capitale")));
                    questionList.add(question);
                    System.out.println(c.getString(c.getColumnIndex("pays")));

                }
                System.out.println("size : "+ questionList.size());
                System.out.println("quetion: "+c.getColumnIndex("question")+"capitale: "+ c.getColumnIndex("capitale"));
                System.out.println("quetion ta7t : "+c.getString(c.getColumnIndex("pays"))+"capitale: "+ c.getString(c.getColumnIndex("capitale")));
            } while (c.moveToNext());
        }

        c.close();

        return questionList;
    }


}
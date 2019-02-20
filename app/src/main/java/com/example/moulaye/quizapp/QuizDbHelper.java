package com.example.moulaye.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyQuiz.db";
    private static final int DATABASE_VERSION = 10;
    private SQLiteDatabase db;

    private static final String TABLE_PAYS = "table_pays";
    private static final String COL_ID = "ID";
    private static final String COL_pays = "PAYS";
    private static final String COL_CAPITALE = "CAPITALE";
    private static final String COL_MONNAIE = "MONNAIE";


    public QuizDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_PAYS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_pays + " TEXT, "
            + COL_CAPITALE + " TEXT, " + COL_MONNAIE + " TEXT);";


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = ("CREATE TABLE 'quiz_questions' ('ID'  INTEGER PRIMARY KEY AUTOINCREMENT, 'pays' TEXT, " +
                "'option1' TEXT, 'option2' TEXT, 'option3' TEXT, 'answer_nr' TEXT, 'lat' TEXT, 'lon' TEXT)");

        final String SQL_CREATE_TABLE = ("CREATE TABLE 'quiz_table' ('ID'  INTEGER PRIMARY KEY AUTOINCREMENT, 'pays' TEXT, " +
                "'capitale' TEXT, 'monnaie' TEXT)");
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(CREATE_BDD);

      //  fillQuestionsTable();
        fillQuizTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS 'quiz_questions'");
        db.execSQL("DROP TABLE IF EXISTS 'quiz_table'");
        db.execSQL("DROP TABLE " + TABLE_PAYS + ";");
        onCreate(db);
    }

   /* private void fillQuestionsTable() {
        Question q1 = new Question(" Belgique ?", "Paris", " Londres", "\n" +
                " Bruxelles", 3);
        addQuestion(q1);
        Question q2 = new Question(" Maroc ?", "Rabat", "Paris", "Madrid", 1);
        addQuestion(q2);
        Question q3 = new Question("Chine ?", "Tokyo", "Dakar", "Pekin", 3);
        addQuestion(q3);
        Question q4 = new Question("Angleterre ?", " Londres", "Genève", "Moscou", 1);
        addQuestion(q4);
        Question q5 = new Question("Mauritanie?", " Ankara", "Nouakchott", "Teheran", 2);
        addQuestion(q5);

    }*/
    private void fillQuizTable() {
        DataQuiz d1=  new DataQuiz(2, "France", "Paris", "Euro");
        DataQuiz d2=  new DataQuiz(3,"Espagne", "Madrid", "Euro");
        DataQuiz d3=  new DataQuiz(4,"Mauritanie", "NKC", "Ouguiya");
        DataQuiz d4=  new DataQuiz(5,"Japon", "Tokyo", "yen");

    }

    public void addQuestion(Question question) {
      db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("pays", question.getQuestion());
        cv.put("option1", question.getOption1());
        cv.put("option2", question.getOption2());
        cv.put("option3", question.getOption3());
        cv.put("answer_nr", question.getAnswerNr());
        cv.put("lat", question.getLat());
        cv.put("lon", question.getLon());
        db.insert("quiz_questions", null, cv);

        db.close();

    }
public double getlatLon(){
    db = getReadableDatabase();
    Cursor c = db.rawQuery("SELECT * FROM quiz_questions", null);
        return getlatLon();

    }
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM quiz_questions", null);
     //   Cursor c1 = db.rawQuery("SELECT * FROM quiz_table", null);

        if (c.moveToFirst()) {
            do {
                if (questionList.size()<5) {
                    Question question = new Question();
                    question.setQuestion("C'est quoi la capitale de " + c.getString(c.getColumnIndex("pays")));
                    question.setOption1(c.getString(c.getColumnIndex("option1")));
                    question.setOption2(c.getString(c.getColumnIndex("option2")));
                    question.setOption3(c.getString(c.getColumnIndex("option3")));
                    question.setAnswerNr(c.getString(c.getColumnIndex("answer_nr")));
                    /*question.setLat(c.getString(c.getColumnIndex("lat")));
                      question.setLon(c.getString(c.getColumnIndex("lon")));*/
                    questionList.add(question);
                }
                System.out.println("size : "+ questionList.size());
                System.out.println("quetion: "+c.getColumnIndex("question")+"option1: "+ c.getColumnIndex("option1"));
               System.out.println("quetion ta7t : "+c.getString(c.getColumnIndex("pays"))+"option1: "+ c.getString(c.getColumnIndex("option1")));
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}

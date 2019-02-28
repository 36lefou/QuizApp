package com.example.moulaye.quizapp;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ExampleDialog.OnInputListener {
    float [] dist = new float[10];
    float disf;

    int score;
    LatLng pt ;
    private GoogleMap mMap;
    double latSyd, lonSyd;
    int random=0;
    public LatLng sydney = new LatLng(latSyd, lonSyd);
    String username;
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: got the input: " + input);

        //  mInputDisplay.setText(input);
        username = input;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");

    }


    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                //clear the map
                mMap.clear();
                //adding new marker
                mMap.addMarker(new MarkerOptions().position(point));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                pt=point;

            }
        });




    }


    public void addMarkerButt(View v) throws InterruptedException {
        if (random==0){

            final Button butt =(Button) findViewById(R.id.buttoAdd);
            butt.setText("Start");

            showNextQuestion();
        }
        if(random>0){

            //Check if map was clicked
            if (pt == null) {
                Toast.makeText(MapsActivity.this, "Entrer une réponse svp !", Toast.LENGTH_SHORT).show();
            } else {
                // calculation of the distance between points
                Location.distanceBetween(latSyd, lonSyd, pt.latitude, pt.longitude, dist);
                disf = dist[0] / 1000;
                System.out.println("la distance1 est: " + disf);

                if (random < 6) {
                    try {
                        Geocoder geocoder = new Geocoder(this);
                        List<Address> addresses = null;
                        List<Address> addresses1 = null;
                        addresses = geocoder.getFromLocation(latSyd, lonSyd, 1);
                        addresses1 = geocoder.getFromLocation(pt.latitude, pt.longitude, 1);
                        String City1="";
                        String City = addresses.get(0).getCountryName();
                            if (addresses1 != null && addresses1.size() > 0){
                                City1 = addresses1.get(0).getCountryName();
                            System.out.println("citoh :" + City + "City 1: " + City1);}
                            else {
                                System.out.println("ce n'est pas un pays");
                            }


                        if (City.equals(City1)) {
                            score++;
                            Toast.makeText(MapsActivity.this, "Bonne réponse!", Toast.LENGTH_SHORT).show();
                            System.out.println("city :" + City + "City 1: " + City1);
                            final TextView simpleTextView = (TextView) findViewById(R.id.Reponse);
                            simpleTextView.setText("Bonne Réponse");
                        } else {
                            Toast.makeText(MapsActivity.this, "Mauvaise réponse!", Toast.LENGTH_SHORT).show();
                            final TextView simpleTextView = (TextView) findViewById(R.id.Reponse);
                            simpleTextView.setText("La distance est: " + disf);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("4e maged yasla7 ye le7mar ");

                    }
                }
                showNextQuestion();

                System.out.println("random :" + random);

            }
        }
    }

    private void showNextQuestion() throws InterruptedException {

        if(random ==0) random=1;
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        if(random<6 && random>0) {

            String la1, lon1, nom;
            la1=dbHelper.getlatLon(random).get(0).toString();
            lon1=dbHelper.getlatLon(random).get(1).toString();
            nom=dbHelper.getlatLon(random).get(2).toString();
            latSyd = Double.parseDouble(la1);
            lonSyd = Double.parseDouble(lon1);
            final TextView simpleText = (TextView) findViewById(R.id.Question);
            simpleText.setText("Où est "+ nom+"?");
            final Button butt =(Button) findViewById(R.id.buttoAdd);
            butt.setText("valid");
            System.out.println("lat houn"+lonSyd+" lon "+latSyd+ "nom :"+nom);

        }
        else if(random==6){
            final Button butt =(Button) findViewById(R.id.buttoAdd);
            butt.setText("Finish");

        }
        if(random == 7){
            Toast.makeText(MapsActivity.this, "votre score est : "+score, Toast.LENGTH_SHORT).show();

            finishQuiz();

        }

        random++;

    }
    private void finishQuiz() {
        SharedPreferences pref = getSharedPreferences("geo_quizz_score", MODE_PRIVATE);
        int highscore = pref.getInt("score",0);
        if (score > highscore) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putLong("date",System.currentTimeMillis());
            editor.putInt("score", score);
            editor.putString("user",username);
            editor.commit();
        }

        finish();
    }




}

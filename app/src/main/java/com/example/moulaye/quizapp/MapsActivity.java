package com.example.moulaye.quizapp;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
        // Add a marker in Sydney and move the camera
    //    MyInfoWindowAdapter infoWindowAdap = new MyInfoWindowAdapter();
      //  mMap.setInfoWindowAdapter(infoWindowAdap);


       // Marker mm = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
/*
        // Add a marker in Sydney and move the camera
        LatLng Nkc = new LatLng(18.07275691, -375.98510742);
        mMap.addMarker(new MarkerOptions().position(Nkc).title("Nouakchott"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Nkc));
*/
        //Add Marker when click on the map
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
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        //dbHelper.
        if (random==0){
            latSyd= 35.861660 ; lonSyd= 104.195396;//chine
            final TextView simpleText = (TextView) findViewById(R.id.Question);
            simpleText.setText("Où est la Chine ?");
            random++;
        }
    }

    public void addMarkerButt(View v) throws InterruptedException {

            //Check if map was clicked
            if (pt == null) {
                Toast.makeText(MapsActivity.this, "Entrer une réponse svp !", Toast.LENGTH_SHORT).show();
            } else {
                // calculation of the distance between points
                Location.distanceBetween(latSyd, lonSyd, pt.latitude, pt.longitude, dist);
                disf = dist[0] / 1000;
                System.out.println("la distance1 est: " + disf);
                //    System.out.println("la distance2 est: " + dist[0] * 0.000621371192f);
                // Sending result to TextView


                /*if (disf < 50) {
                    score++;
                    Toast.makeText(MapsActivity.this, "Bonne réponse!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MapsActivity.this, "Mauvaise réponse!", Toast.LENGTH_SHORT).show();
                }*/
                if (random <5) {
                try {
                    Geocoder geocoder = new Geocoder(this);
                    List<Address> addresses = null;
                    List<Address> addresses1 = null;
                    addresses = geocoder.getFromLocation(latSyd, lonSyd, 1);
                    addresses1 = geocoder.getFromLocation(pt.latitude, pt.longitude, 1);
                    /*String Country = addresses.get(0).getCountryName();*/
                    String City = addresses.get(0).getCountryName();
                    String City1 = addresses1.get(0).getCountryName();
                    System.out.println("city :" + City + "City 1: " + City1);

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

    private void showNextQuestion() throws InterruptedException {
//= ThreadLocalRandom.current().nextInt(0, 2+1);
        if(random==1) {
            latSyd = 40.46775;
            lonSyd = -3.74970;//madrid
            final TextView simpleText = (TextView) findViewById(R.id.Question);
            simpleText.setText("Où est l'Espagne ?");
        }
        else if(random==2){
            latSyd = 21.007891;
            lonSyd = -10.940835;//NKC
            final TextView simpleText = (TextView) findViewById(R.id.Question);
            simpleText.setText("Où est la Mauritanie ?");

        }
        else if(random==3){
            latSyd = 31.791702;
            lonSyd = -7.092620;//maroc
            final TextView simpleText = (TextView) findViewById(R.id.Question);
            simpleText.setText("Où est le Maroc ?");

        }
        else if(random==4){
            final Button butt =(Button) findViewById(R.id.buttoAdd);
            butt.setText("Finish");

        }
         if(random == 5){
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

@SuppressLint("ValidFragment")
class ExampleDialog extends AppCompatDialogFragment {
    private EditText editTextUsername;
    public  String username;
    public interface OnInputListener{
        void sendInput(String input);
    }
    public OnInputListener mOnInputListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Entrer votre pseudonyme")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        username = editTextUsername.getText().toString();
                        if(username != "") {
                            mOnInputListener.sendInput(username);
                            dismiss();
                            System.out.println("hihi"+username);
                        }
                        else {
                            builder.setTitle("Entrer un Pseudo avant de jouer");

                        }

                    }
                });

        editTextUsername = view.findViewById(R.id.edit_username);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }

}
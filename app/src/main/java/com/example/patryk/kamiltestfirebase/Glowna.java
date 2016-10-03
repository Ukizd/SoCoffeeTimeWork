package com.example.patryk.kamiltestfirebase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Glowna extends AppCompatActivity {

    private TextView todayDate;
    private Spinner spinnerOD;
    private Spinner spinnerDO;
    private TextView zalogowanyJako;
    private Button wyloguj;

    public Firebase myFirebaseRef;

    public FirebaseAuth firebaseAuth;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glowna);
        Firebase.setAndroidContext(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));

        }

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        final String dateToday;
        todayDate = (TextView) findViewById(R.id.Data);
        spinnerOD = (Spinner) findViewById(R.id.spinnerOD);
        spinnerDO = (Spinner) findViewById(R.id.spinnerDO);
        zalogowanyJako = (TextView) findViewById(R.id.textViewZalogowanyJako);
        wyloguj = (Button) findViewById(R.id.buttonWyloguj);
        Button buttonSave = (Button) findViewById(R.id.Save);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.godziny, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_item);

        spinnerOD.setAdapter(adapter);
        spinnerDO.setAdapter(adapter);

        wyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });





        Date dNow = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat ft = new SimpleDateFormat("dd_MM_yyyy");
        dateToday = ft.format(dNow);
        todayDate.setText(dateToday);
        zalogowanyJako.setText(user.getEmail());




        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hourFrom = spinnerOD.getSelectedItem().toString();
                String hourTo = spinnerDO.getSelectedItem().toString();


                myFirebaseRef = new Firebase("https://socoffee-2ec22.firebaseio.com/");

                SimpleDateFormat simpleDateFormathourFrom = new SimpleDateFormat("HH:mm");
                try {
                    Date godzinapoczatek = simpleDateFormathourFrom.parse(hourFrom);
                    Date godzinakoniec = simpleDateFormathourFrom.parse(hourTo);
                    Toast.makeText(getApplicationContext(), "OD:" + hourFrom + " \nDO:" + hourTo + "\n" + printDifference(godzinapoczatek, godzinakoniec), Toast.LENGTH_LONG).show();

                    Dzien nowyDzien = new Dzien(hourFrom, hourTo, printDifference(godzinapoczatek, godzinakoniec));

                    String stringUserEmail=user.getEmail();

                    String[] stringUserEmailWithout = stringUserEmail.split("@");


                    myFirebaseRef.child("Pracownicy").child(stringUserEmailWithout[0]).child(dateToday).setValue(nowyDzien);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public static String printDifference(Date startDate, Date endDate) {
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        if (elapsedMinutes == 0) {
            return elapsedHours + ":" + elapsedMinutes+"0";
        }
        return elapsedHours + ":" + elapsedMinutes;

        //System.out.println( "Godziny: "+elapsedHours + "Minuty" + elapsedMinutes);
        //Toast.makeText(App.this, "Godziny: "+elapsedHours + "Minuty" + elapsedMinutes, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Glowna Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.patryk.kamiltestfirebase/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Glowna Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.patryk.kamiltestfirebase/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}





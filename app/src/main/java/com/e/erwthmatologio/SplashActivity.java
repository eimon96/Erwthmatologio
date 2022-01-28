package com.e.erwthmatologio;

import Erwthmatologio.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
//import com.google.android.gms.ads.AdView;
import java.io.File;

/**
 * Κλάση SplashActivity: περιλαμβάνει το όνομα της εφαρμογής και τα ονόματα των φοιτητών
 * Ελέγχει αν υπάρχει ήδη η βάση δεδομένων με τις ερωτήσεις, αν όχι την δημιουργεί
 */
public class SplashActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button BtNext;
    public static Context co;
//    private AdView adView;

    /**
     * Αρχικοποίηση αρχικής οθόνης
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        BtNext = findViewById(R.id.BtNext);
        BtNext.setOnClickListener(this);

        co = getApplicationContext();

//        adView = findViewById(R.id.adView);
//        Ad.showAd(co, adView);

        CheckDB();
    }

    /**
     * onClick listener για το κουμπί BtNext που πάει στο επόμενο activity
     * @param v: view
     */
    @Override
    public void onClick(View v)
    {
        if (v == BtNext)
        {
            Intent intent = new Intent(this, EisagwghStoixeiwnActivity.class);
            startActivity(intent);

            // Για να μην μπορεί ο χρήστης να επιστρέφει στο προηγούμενο activity
            // Τον αποτρέπει από το να κάνει cheat στο τεστ
            // (removes activity from back stack)
            finish();
        }
    }

    /**
     * Ελέγχει αν υπάρχει ήδη η βάση δεδομένων με τις ερωτήσεις, αν όχι την δημιουργεί σε ξεχωριστό thread (runnable)
     */
    private void CheckDB()
    {
        if (!FileExists("Erwthseis.db"))
        {
            DBThread dbt = new DBThread();
            new Thread(dbt).start();
        }
        else
        {
            DBThread.helper = new DBHelper(co);
            DBThread.db = DBThread.helper.getReadableDatabase();
        }
    }

    /**
     * Ελέγχει την ύπαρξη ενός αρχείου db
     * @param Fn: δοθέν αρχείο προς έλεγχο
     * @return true αν υπάρχει, false αλλιώς
     */
    private boolean FileExists(String Fn)
    {
        File file = co.getDatabasePath(Fn);
        return file.exists();
    }
}


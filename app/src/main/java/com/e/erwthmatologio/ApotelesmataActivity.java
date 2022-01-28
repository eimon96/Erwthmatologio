package com.e.erwthmatologio;

import Erwthmatologio.R;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
//import com.google.android.gms.ads.AdView;

/**
 * ApotelesmataActivity: Προβάλει τα αποτελέσματα και την ώρα διεξαγωγής του τεστ μαζί με τα στοιχεία του Φοιτητή.
 * Εμφανίζεται επίσης, αν ο χρήστης δεν πρόλαβε να ολοκληρώσει το τεστ λόγω χρόνου, σχετικό μήνυμα (toast)
 */
//  (Το toast είναι από το προηγούμενο activity)
public class ApotelesmataActivity extends AppCompatActivity
{
    private TextView TvStoixeia;
    private TextView TvScore;
    private TextView TvDate;
    private final Foithths foithths = EisagwghStoixeiwnActivity.foithths;
    public static Context co;
//    private AdView adView;

    /**
     * onCreate: Αρχικοποίηση Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apotelesmata_layout);

        TvStoixeia = findViewById(R.id.TvStoixeiaFoithth);
        TvScore = findViewById(R.id.TvScoreNumber);
        TvDate = findViewById(R.id.TvDate);

        PrintStoixeia();

        co = getApplicationContext();
//        adView = findViewById(R.id.adView);
//        Ad.showAd(co, adView);
    }

    /**
     * Εκτυπώνει στα κατάλληλα textviews τα στοιχεία του Φοιτητή, το Σκορ και την ημερομηνία/ώρα
     */
    private void PrintStoixeia()
    {
        TvStoixeia.setText(foithths.toString());
        TvDate.setText(foithths.getTime());
        TvScore.setText(Integer.toString(foithths.getVathmologia()) + "/" + Erwthmatologio.GetInstance(this).GetNoQuestions());
    }

    /**
     * onBackPressed διαχειρίζεται το κλικ του "πίσω".
     * Δεν θέλουμε ο χρήστης να πηγαίνει πίσω γιατί μιλάμε για τεστ.
     * Έχει γίνει override και έχει αφαιρεθεί η super
     */
    @Override
    public void onBackPressed()
    {
        // Κενό για να μην μπορεί να πατηθεί το back button και αρχίσουμε να κολυμπάμε στα bugs
    }

}

package com.e.erwthmatologio;

import Erwthmatologio.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
//import com.google.android.gms.ads.AdView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Κλάση EisagwghStoixeiwnActivity: Ζητά και καταγράφει τα στοιχεία του εξεταζόμενου
 * Όνομα Επώνυμο και ΑΜ
 */
public class EisagwghStoixeiwnActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText EtOnoma;
    private EditText EtEponumo;
    private EditText EtAM;
    private TextView TvInfo;
    private Button BtEkkinhsh;
    public static Foithths foithths;
    public static Context co;
//    private AdView adView;
    /**
     * Αρχικοποίηση Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eisagwgh_stoixeiwn_layout);

        initViews();

        co = getApplicationContext();
//        adView = findViewById(R.id.adView);
//        Ad.showAd(co, adView);
    }

    /**
     * Συνδέει τις μεταβλητές με τα views και τους κατάλληλους listeners
     */
    private void initViews()
    {
        EtOnoma = findViewById(R.id.EtOnoma);
        EtEponumo = findViewById(R.id.EtEponumo);
        EtAM = findViewById(R.id.EtAM);
        BtEkkinhsh = findViewById(R.id.BtEkkinhsh);
        BtEkkinhsh.setOnClickListener(this);
        TvInfo = findViewById(R.id.TvInfo);
        TvInfo.setText("Έχεις " + ErwthshActivity.TestDuration + " δευτερόλεπτα διαθέσιμα! \nΈτοιμος;");
    }


    /**
     * onClick listener για το κουμπί BtEkkinhsh που πάει στο activity των ερωτήσεων
     * Ελέγχει αν τα δοθέντα στοιχεία είναι σωστά,
     * αν είναι προχωράει στο επόμενο activity, διαφορετικά μένει στο ίδιο
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        int creationCode = createFoithths();
        if (v == BtEkkinhsh && creationCode == 0)
        {
            Intent intent = new Intent(this, ErwthshActivity.class);
            startActivity(intent);

            // Για να μην μπορεί ο χρήστης να επιστρέφει στο προηγούμενο activity
            // Τον αποτρέπει από το να κάνει cheat στο τεστ
            // (removes activity from back stack)
            finish();
        }
    }

    /**
     * Δημιουργεί ένα καινούργιο αντικείμενο Foithths με τα εισαγώμενα στοιχεία
     * @return 0 αν όλα πήγαν καλά, -1 αλλιώς
     */
    private int createFoithths()
    {
        String onoma = EtOnoma.getText().toString();
        String eponumo = EtEponumo.getText().toString();
        String time = wraDieksagwghs();

        if (onoma.isEmpty() || eponumo.isEmpty())
        {
            toastKenoString();
            return -1;
        }

        int AM = checkAM();
        if (AM == -1)
        {
            return -1;
        }

        foithths = Foithths.GetInstance(this, onoma, eponumo, String.valueOf(AM), time);
        return 0;
    }

    /**
     * Ελέγχει τον Αριθμό Μητρώου
     * @return -1 αν υπάρχει κάποιο σφάλμα, αλλιώς επιστρέφει τον ΑΜ
     */
    private int checkAM()
    {
        int AM;
        // Στην περίπτωση που πατήσει float στον ΑΜ
        try
        {
            AM = Integer.parseInt(EtAM.getText().toString());
        }
        catch (Exception e)
        {
            toastAM();
            return -1;
        }

        // ο ΑΜ πρέπει να έχει μήκος 5
        if (EtAM.length() != 5)
        {
            toastAM();
            return -1;
        }

        return AM;
    }

    /**
     * Τυπώνει toast στην περίπτωση που ο ΑΜ είναι λάθος
     */
    private void toastAM()
    {
        Toast myToast = Toast.makeText(getApplicationContext(), "Τσέκαρε τον ΑΜ άλλη μία", Toast.LENGTH_LONG);
        myToast.show();
    }

    /**
     * Τυπώνει toast στην περίπτωση που κάποιο στοιχείο λείπει
     */
    private void toastKenoString()
    {
        Toast myToast = Toast.makeText(getApplicationContext(), "Πρέπει να συμπληρώσεις όλα τα στοιχεία!", Toast.LENGTH_LONG);
        myToast.show();
    }

    /**
     * @return ημερομηνία και ώρα εκκίνησης του Τεστ
     */
    private String wraDieksagwghs()
    {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return dateFormat.format(date);
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

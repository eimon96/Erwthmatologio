package com.e.erwthmatologio;

import Erwthmatologio.R;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Κλάση ErwthshActivity: Προβάλει και διαχειρίζεται τις ερωτήσεις
 */
public class ErwthshActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView TvTiTleQ;          // Αριθμός Ερώτησης
    private TextView TvQuestion;        // Εκφώνηση Ερώτησης
    private TextView[] TvAnswers;       // Πίνακας απαντήσεων
    private Button BtPrev;              // Κουμπί Προηγούμενο
    private Button BtOK;                // Κουμπί Καταχώρησεις
    private Button BtNext;              // Κουμπί Επόμενο
    private Erwthmatologio AllQuests;   // Erwthmatologio (instance)
    private Erwthsh CurQ;               // Τωρινή Ερώτηση προς απάντηση (τύπου Erwthsh)
    private int CurQNum;                // Αριθμός Τωρινής Ερώτησης προς απάντηση
    private int SelAns;                 // Αριθμός επιλεγμένης απάντησης
    private Drawable BackDraw;          // Background Χρώμα για όταν επιλεγεί μια απάντηση
    private ImageView Img;              // Εικόνα Ερώτησης
    private final Foithths foithths = EisagwghStoixeiwnActivity.foithths;   // Αντικείμενο Φοιτητής
    public static final int TestDuration = 30;       // Διάρκεια τεστ (σε δευτερόλεπτα)
    private TextView TvTimer;           // Textview Αντίστροφη μέτρηση
    private CountDownTimer cdt;         // CountDownTimer Αντίστροφη Μέτρηση

    /**
     * onCreate: Αρχικοποίηση Activity, συνδέει τις μεταβλητές με τα views και τους κατάλληλους listeners
     * Ξεκινάει την αντίστροφη μέτρηση
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.erwthsh_layout);

        TvTiTleQ = findViewById(R.id.TvTitleQ);
        TvQuestion = findViewById(R.id.TvQuestion);
        TvAnswers = new TextView[4];
        TvAnswers[0] = findViewById(R.id.TvAnswer1);
        TvAnswers[1] = findViewById(R.id.TvAnswer2);
        TvAnswers[2] = findViewById(R.id.TvAnswer3);
        TvAnswers[3] = findViewById(R.id.TvAnswer4);
        for (int i = 0; i < 4; i++)
            TvAnswers[i].setOnClickListener(this);
        BtPrev = findViewById(R.id.BtPrevious);
        BtOK = findViewById(R.id.BtOK);
        BtNext = findViewById(R.id.BtNext);
        BtPrev.setOnClickListener(this);
        BtOK.setOnClickListener(this);
        BtNext.setOnClickListener(this);
        BackDraw = TvAnswers[0].getBackground();
        Img = findViewById(R.id.ImEikona);
        Img.setVisibility(View.GONE);
        TvTimer = findViewById(R.id.TvTimer);

        // παίρνουμε το instance (την αναφορά) του Ερωτηματολογίου
        AllQuests = Erwthmatologio.GetInstance(this);

        DoNext();
        startTimer();
    }

    /**
     * onClick: Διαχειρίζεται τα κλικ στα κουμπία '<<', 'Καταχώρηση', '>>'
     * καθώς και τα κλικ στις απαντήσεις
     * @param v: view το οποίο πατήθηκε
     */
    @Override
    public void onClick(View v)
    {
        if (v == BtPrev)
        {
            AllQuests.GoPrevUnAns();
            AllQuests.setCurQuest(AllQuests.getCurQuest() - 1);
            DoNext();
        }

        if (v == BtOK)
        {
            if (SelAns == -1)
            {
                Toast tost = Toast.makeText(getApplicationContext(), "Επέλεξε κάτι!", Toast.LENGTH_SHORT);
                tost.setGravity(Gravity.CENTER_VERTICAL, 0, 400);
                tost.show();
                return;
            }
            CurQ.setUserAns(SelAns + 1);     // +1 γιατί ξεκινάει από το 0 αλλά θέλουμε να ξεκινάει από το 1
            doScore();
            Toast tost = Toast.makeText(getApplicationContext(), "Καταχωρήθηκε!", Toast.LENGTH_SHORT);
            tost.setGravity(Gravity.CENTER_VERTICAL, 0, 400);
            tost.show();
            DoNext();
        }

        if (v == BtNext)
        {
            DoNext();
        }

        for (int i = 0; i < 4; i++)
        {
            if (v == TvAnswers[i])
                SelAns = i;
        }

        DoChangeAnswer();
    }

    /**
     * Εμφανίζει την επόμενη ερώτηση αν υπάρχει, αλλιώς εμφανίζει σχετικό μήνυμα
     */
    private void DoNext()
    {
        CurQNum = AllQuests.GoNextUnAns();

        // Τέλος ερωτηματολογίου == όλες οι ερωτήσεις απαντημένες
        // -> ακύρωση αντίστροφης μέτρησης -> αποτελέσματα
        if (CurQNum == -1)
        {
            Toast tost = Toast.makeText(getApplicationContext(), "ΤΕΛΟΣ ΕΡΩΤΗΜΑΤΟΛΟΓΙΟΥ...", Toast.LENGTH_SHORT);
            tost.setGravity(Gravity.CENTER_VERTICAL, 0, 400);
            tost.show();
            cdt.cancel();
            GoToResults();
        }
        else
        {
            CurQ = AllQuests.GetQuestion();
            for (int i = 0; i < 4; i++)
            {
                TvAnswers[i].setEnabled(true);
            }
            TvTiTleQ.setText("ΕΡΩΤΗΣΗ " + (CurQNum + 1));
            TvQuestion.setText(CurQ.getQueText());
            for (int i = 0; i < CurQ.GetNoAnswers(); i++)
            {
                TvAnswers[i].setText(CurQ.getAnswer(i));
            }
            for (int i = CurQ.GetNoAnswers(); i < 4; i++)
            {
                TvAnswers[i].setEnabled(false);
                TvAnswers[i].setText("");
            }
            if (CurQ.getImg() != 0)
            {
                Img.setImageResource(CurQ.getImg());
                Img.setVisibility(View.VISIBLE);
            }
            else
            {
                Img.setVisibility(View.GONE);
            }
        }
        SelAns = -1;
    }

    /**
     * Πηγαίνει στο activity των αποτελεσμάτων ApotelesmataActivity
     */
    private void GoToResults()
    {
        Intent intent = new Intent(this, ApotelesmataActivity.class);
        startActivity(intent);

        // Για να μην μπορεί ο χρήστης να επιστρέφει στο προηγούμενο activity
        // Τον αποτρέπει από το να κάνει cheat στο τεστ
        // (removes activity from back stack)
        finish();
    }

    /**
     * Μορφοποιεί τα χρώματα του κειμένου και του background στην επιλεγμένη απάντηση
     */
    private void DoChangeAnswer()
    {
        int i;
        for (i = 0; i < 4; i++)
        {
            TvAnswers[i].setBackground(BackDraw);

            // Ας μην κερδίσουμε τα καλλιστεία ομορφότερης εφαρμογής φέτος χαλάλι
            try
            {
                TvAnswers[i].setTextColor((getResources().getColor(R.color.white_ec)));
                if (i == SelAns)
                {
                    TvAnswers[i].setBackgroundColor((getResources().getColor(R.color.black_c)));
                    TvAnswers[i].setTextColor((getResources().getColor(R.color.yellow_db)));
                }
            }
            catch(Exception e)
            {
                System.err.println("Problem with color resources.");
            }
        }
    }

    /**
     * Ελέγχει αν η απάντηση του χρήστη είναι σωστή και αν είναι αυξάνει το σκορ.
     */
    private void doScore()
    {
        if (CurQ.isCorrect())
        {
            foithths.increaseScore();
        }
    }

    /**
     * Ξεκινάει την αντίστροφη μέτρηση
     *
     * Αν τελειώσει ο χρόνος πριν προλάβει ο χρήστης να ολοκληρώσει το τεστ βγάζει σχετικό μήνυμα
     * και πηγαίνει στο activity με τα αποτελέσματα μετρώντας σκορ στις απαντημένες ερωτήσεις
     *
     * Ο χρόνος ΔΕΝ σταματάει αν ο χρήστης ελαχιστοποιήσει την εφαρμογή ώστε να αποφευχθούν οι κλεψιές
     */
    // Θα μπορούσαμε να του μηδενίζουμε το σκορ αλλά είμαστε καλοί άνθρωποι
    private void startTimer()
    {
        Long millisUntilFinished = TestDuration * 1000L;

        cdt = new CountDownTimer(millisUntilFinished, 1000)
        {

            public void onTick(long millisUntilFinished)
            {
                TvTimer.setText("*" + millisUntilFinished / 1000 + "*");
            }

            public void onFinish()
            {
                Toast tost = Toast.makeText(getApplicationContext(), "ΤΕΛΟΣ ΧΡΟΝΟΥ!!!", Toast.LENGTH_LONG);
                tost.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                tost.show();

                GoToResults();
            }
        };
        cdt.start();
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

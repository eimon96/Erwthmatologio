package com.e.erwthmatologio;

import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Αντικείμενο Ερωτηματολόγιο, αποτελείται από ερωτήσεις.
 * Είναι κλάση Singleton
 */
public class Erwthmatologio
{
    private ArrayList<Erwthsh> Questions;                   // Πίνακας Ερωτήσεων τύπου Erwthsh
    private int CurQuest;                                   // Αριθμός Τρέχουσας Ερώτησης
    private static Erwthmatologio Instance = null;          // Instance (Αναφορά) τύπου Ερωτηματολόγιο
    private static Context Cont;                            // Το Context του Activity

    /**
     * @param Co: Context Activity
     * @return Instance τύπου Ερωτηματολόγιο
     */
    public static Erwthmatologio GetInstance(Context Co)
    {
        /*
            Η μέθοδος αυτή Πρέπει να είναι public και static για να μπορεί να κληθεί από άλλες κλάσεις
            χωρίς να υπάρχει αντικείμενο Erwthmatologio σε αυτές
            (ο cunstructor είναι private μπορεί να κληθεί μόνο από εδώ δλδ κάνουμε new Erwthmatologio ΜΟΝΟ ΕΔΩ)
            Το instance που επιστρέφεται είναι μια αναφορά στο (μοναδικό) αντικείμενο της κλάσης
            γιαυτό η κλάση αποκαλείται singleton

            Υπάρχει και εναλλακτικός τρόπος, bundle με objects που υλοποιεί interface passable
            (αν πληρεί συγκεκριμένες προδιαγραφές)
         */

        Cont = Co;
        if (Instance == null)
            Instance = new Erwthmatologio();
        return Instance;
    }

    /**
     * @return τον αριθμό της τρέχουσας ερώτησης
     */
    public int getCurQuest()
    {
        return CurQuest;
    }

    /**
     * Θέτει την τρέχουσα ερώτηση
     * @param curQuest: τρέχουσα ερώτηση
     */
    public void setCurQuest(int curQuest)
    {
        CurQuest = curQuest;
    }

    /**
     * Constructor
     * Δημιουργεί ένα νέο αντικείμενο Ερωτηματολόγιο
     */
    private Erwthmatologio()
    {
        Questions = new ArrayList();
        CurQuest = -1 ;
        LoadSQLiteDatabase();
    }

    /**
     * Φόρτωση βάσης δεδομένων ερωτήσεων από το Erwthseis.db
     */
    private void LoadSQLiteDatabase()
    {
        try
        {
            Cursor cursor = DBThread.db.rawQuery("SELECT * FROM ERWTHSEIS", null);
            if(cursor.moveToFirst())
            {
                do
                {
                    Erwthsh Q = new Erwthsh();
                    Q.setQueText(cursor.getString(1));
                    Q.setCorrectAns(cursor.getInt(2));
                    for (int j = 3; j <= 6; j++)
                    {
                        if (!cursor.getString(j).isEmpty())
                        {
                            Q.AddAnswer(cursor.getString(j));
                        }
                    }
                    if (cursor.getInt(7) != 0)
                    {
                        Q.setImg(cursor.getInt(7));
                    }
                    Questions.add(Q);
                    Collections.shuffle(Questions);
                }
                while(cursor.moveToNext());
            }
            cursor.close();
        }
        catch (Exception e)
        {
            System.err.println("***Oh My God.... Exception in Reading Database");
            e.printStackTrace();
        }
    }

    /**
     * @return τον αριθμό των ερωτήσεων
     */
    public int GetNoQuestions()
    {
        return Questions.size();
    }

    /**
     * @return τον αριθμό των απαντημένων ερωτήσεων
     */
    public int GetNoAnsweredQuestions()
    {
        int Count;
        int i;
        for (Count = 0, i = 0; i < GetNoQuestions(); i++)
            if (GetQuestion(i).getUserAns() != -1)
                Count ++;
        return Count;
    }

    /**
     * @return τον αριθμό των μη-απαντημένων ερωτήσεων
     */
    public int GetNoUnAnsweredQuestions()
    {
        int Count;
        int i;
        for (Count = 0, i = 0; i < GetNoQuestions(); i++)
            if (GetQuestion(i).getUserAns() == -1)
                Count ++;
        return Count;
    }

    /**
     * @param QN: αριθμός ερώτησης
     * @return το αντικείμενο Ερώτηση που αντιστοιχεί στον δοθέν ακέραιο αριθμό ερώτησης
     */
    public Erwthsh GetQuestion(int QN)
    {
        return Questions.get(QN);
    }

    /**
     * @return το αντικείμενο Ερώτηση του τρέχοντος αριθμού ερώτησης
     */
    public Erwthsh GetQuestion()
    {
        return Questions.get(CurQuest);
    }

    /**
     * Πάει στην επόμενη αναπάντητη ερώτηση
     * @return τον αριθμό της επόμενης αναπάντητης ερώτησης (ως τρέχουσα ερώτηση)
     * Αν δεν υπάρχει άλλη διαθέσιμη αναπάντητη ερώτηση επιστρέφει -1
     */
    public int GoNextUnAns()
    {
        if (GetNoUnAnsweredQuestions() == 0)
            return -1;
        do
        {
            CurQuest++;
            if (CurQuest == GetNoQuestions())
                CurQuest = 0;
        }
        while (GetQuestion(CurQuest).isAnswered());
        return CurQuest;
    }

    /**
     * Πάει στην προηγούμενη αναπάντητη ερώτηση
     * @return τον αριθμό της προηγούμενης αναπάντητης ερώτησης (ως τρέχουσα ερώτηση)
     * Αν δεν υπάρχει άλλη διαθέσιμη αναπάντητη ερώτηση επιστρέφει -1
     */
    public int GoPrevUnAns()
    {
        if (GetNoUnAnsweredQuestions() == 0)
            return -1;
        do
        {
            CurQuest--;
            if (CurQuest == -1)
                CurQuest = GetNoQuestions() - 1;
        }
        while (GetQuestion(CurQuest).isAnswered());
        return CurQuest;
    }
}

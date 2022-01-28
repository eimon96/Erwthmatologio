package com.e.erwthmatologio;

import android.content.Context;

/**
 * Αντικείμενο Φοιτητής
 * Είναι κλάση Singleton
 */
public class Foithths
{
    private String onoma;
    private String eponumo;
    private String am;
    private String time;
    private int vathmologia;
    private static Foithths Instance = null;
    private static Context Cont;


    /**
     * Επιστρέφει την αναφορά στο αντικείμενο Φοιτητής
     * @param Co: context
     * @param onoma: όνομα φοιτητή
     * @param eponumo: επώνυμο φοιτητή
     * @param am: αριθμός μητρώου φοιτητή (5 ψηφία)
     * @param time: χρόνος(ημερομηνία/ώρα) έναρξης τεστ
     * @return αναφορά στο αντικείμενο φοιτητή
     */
    public static Foithths GetInstance(Context Co, String onoma, String eponumo, String am, String time)
    {
        Cont = Co;
        if (Instance == null)
            Instance = new Foithths(onoma, eponumo, am, time);
        return Instance;
    }

    /**
     * Δημιουργεί ένα νέο αντικείμενο Φοιτητή με τα παρακάτω στοιχεία:
     *
     * @param onoma: Όνομα
     * @param eponumo: Επώνυμο
     * @param am: Αριθμός μητρώου (5 ψηφία)
     * @param time: ημερομηνία και ώρα διεξαγωγής του τεστ (format dd:MM:yyyy HH:mm:ss)
     *
     * Αρχικοποιεί το σκορ ως 0.
     */
    private Foithths(String onoma, String eponumo, String am, String time)
    {
        this.onoma = onoma;
        this.eponumo = eponumo;
        this.am = am;
        this.time = time;
        this.vathmologia = 0;
    }

    // Getters και Setters
    public String getOnoma()
    {
        return onoma;
    }

    public void setOnoma(String onoma)
    {
        this.onoma = onoma;
    }

    public String getEponumo()
    {
        return eponumo;
    }

    public void setEponumo(String eponumo)
    {
        this.eponumo = eponumo;
    }

    public String getAm()
    {
        return am;
    }

    public void setAm(String am)
    {
        this.am = am;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public int getVathmologia()
    {
        return vathmologia;
    }

    public void setVathmologia(int vathmologia)
    {
        this.vathmologia = vathmologia;
    }

    /**
     * Αυξάνει τη βαθμολογία κατά 1
     */
    public void increaseScore()
    {
        vathmologia++;
    }

    /**
     * @return το όνομσ, το επώνυμο και τον ΑΜ του φοιτητή διαχωρισμένα με κενό
     */
    @Override
    public String toString()
    {
        return (this.getOnoma() + " " + this.getEponumo() + " " + this.getAm());
    }
}

package com.e.erwthmatologio;

import Erwthmatologio.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteHelper για τη δημιουργία της βάσης δεδομένων με τις ερωτήσεις και τα στοιχεία των διαγωνιζόμενων
 */
class DBHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "Erwthseis.db";
    private static final int DB_VERSION = 1;

    /**
     * Constructor
     * @param context
     */
    DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * onCreate
     * @param db: database
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    /**
     * onUpgrade
     * @param db: database
     * @param oldVersion: παλιά έκδοση
     * @param newVersion: νέα έκδοση
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    /**
     * onDowngrade
     * @param db: database
     * @param oldVersion: παλιά έκδοση
     * @param newVersion: νέα έκδοση
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        super.onDowngrade(db, oldVersion, newVersion);
        // τίποτα, αλλά ας υπάρχει μπας και γλυτώσουμε κανά bug
    }

    /**
     * updateMyDatabase: Δημιουργία πινάκων Ερωτήσεων και Διαγωνιζόμενων (Φοιτητών)
     * Προσθήκη ερωτήσεων
     * @param db: database
     * @param oldVersion: παλιά έκδοση
     * @param newVersion: νέα έκδοση
     */
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("CREATE TABLE iF NOT EXISTS ERWTHSEIS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "EKFWNHSH TEXT, "
                + "INDEXSWSTOU INTEGER, "
                + "APANTHSH1 TEXT ,"
                + "APANTHSH2 TEXT ,"
                + "APANTHSH3 TEXT ,"
                + "APANTHSH4 TEXT ,"
                + "IMAGE_RESOURCE_ID INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS FOITHTES (ONOMA TEXT, "
                + "EPONUMO TEXT, "
                + "AM TEXT PRIMARY KEY, "
                + "TIME TEXT, "
                + "SCORE INTEGER);");

//        insertQ(db, "Ποιά είναι η απεικονιζόμενη ηθοποιός;", 1, "Milla Jovovich", "Julia Roberts", "Η θεια μου η χορεύτρια", "", R.drawable.resident_evil);
        insertQ(db, "Ποιό είναι το παραπάνω Πόκεμον;",2,"Charizard","Dragonite","Dragonair","", R.drawable.dragonite);
        insertQ(db,"Ποιό είναι το παραπάνω Πόκεμον;",1, "Cubone", "Alakazam", "Gengar", "Marowak", R.drawable.cubone);
        insertQ(db, "Ποιό είναι το παραπάνω Πόκεμον;", 4, "Typhlosion", "Spinarak", "Larvitar", "Tyranitar", R.drawable.tyranitar);
        insertQ(db, "Ποιό είναι το παραπάνω Πόκεμον;", 3, "Squirtle", "Donphan", "Totodile", "", R.drawable.totodile);
        insertQ(db, "Ποιό είναι το παραπάνω Πόκεμον;", 3, "Articuno", "Moltres", "Zapdos", "Lugia", R.drawable.zapdos);
    }

    /**
     * Insert ερωτήσεων
     * @param db: database
     * @param ekfwnhsh: κείμενο εκφώνησης ερώτησης
     * @param indexSwstou: index σωστής απάντησης
     * @param apanthsh1: κείμενο 1ης πιθανής απάντησης
     * @param apanthsh2: κείμενο 2ης πιθανής απάντησης
     * @param apanthsh3: κείμενο 3ης πιθανής απάντησης
     * @param apanthsh4: κείμενο 4ης πιθανής απάντησης
     * Αν δεν υπάρχει κάποια πιθανή απάντηση είναι κενό string ""
     * @param eikona: resourse id εικόνας - αν δεν έχει είναι 0
     */
    // Πρώτα φτιάχνεται η βάση και μετά το αντικείμενο Ερώτηση
    private static void insertQ(SQLiteDatabase db, String ekfwnhsh, int indexSwstou, String apanthsh1,
                                String apanthsh2,  String apanthsh3,  String apanthsh4, int eikona)
    {
        ContentValues qValues = new ContentValues();

        qValues.put("EKFWNHSH", ekfwnhsh);
        qValues.put("INDEXSWSTOU", indexSwstou);
        qValues.put("APANTHSH1", apanthsh1);
        qValues.put("APANTHSH2", apanthsh2);
        qValues.put("APANTHSH3", apanthsh3);
        qValues.put("APANTHSH4", apanthsh4);
        qValues.put("IMAGE_RESOURCE_ID", eikona);

        db.insert("ERWTHSEIS", null, qValues);
    }

    /**
     * Insert φοιτητή στη βάση Foithtes
     * @param db: database
     * @param foithths: αντικείμενο Φοιτητής
     */
    // Θα χρησιμοποιηθεί αν βάλουμε στα αποτελέσματα πίνακα high score
    // Πρώτα φτιάχνεται το αντικέιμενο Φοιτητής και μετά αποθηκεύεται στη βάση
    // διότι η βάση θα χρησιμοποιηθεί ΜΟΝΟ στο high score
    public void insertF(SQLiteDatabase db, Foithths foithths)
    {
        ContentValues fValues = new ContentValues();

        fValues.put("ONOMA", foithths.getOnoma());
        fValues.put("EPONUMO", foithths.getEponumo());
        fValues.put("AM", foithths.getAm());
        fValues.put("TIME", foithths.getTime());
        fValues.put("SCORE", foithths.getVathmologia());

        db.insert("FOITHTES", null, fValues);
    }
}
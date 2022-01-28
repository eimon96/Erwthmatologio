package com.e.erwthmatologio;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Δημιουργία του Database σε ξεχωριστό thread (runnable)
 *
 */
public class DBThread implements Runnable
{
    public static SQLiteOpenHelper helper;
    public static SQLiteDatabase db;

    /**
     * O κώδικας που θα τρέξει στο thread
     */
    @Override
    public void run()
    {

        InitializeDB();
    }

    /**
     * Δημιουργία SQLiteDB
     */
    private void InitializeDB()
    {
        try
        {
            helper = new DBHelper(SplashActivity.co);
            db = helper.getWritableDatabase();
        }
        catch(Exception e)
        {
            System.err.println("***OMG.... Exception in Creating Database");
            System.err.println("GL debugging that");
            e.printStackTrace();
        }
    }
}

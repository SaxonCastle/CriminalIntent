package com.bignerdranch.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.database.CrimeBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bignerdranch.android.database.CrimeCursorWrapper;
import com.bignerdranch.android.database.CrimeDBSchema.CrimeTable;

public class CrimeLab {
    private List<Crime> mCrimes; //Need this to delete crimes

    private static CrimeLab sCrimeLab; //s naming convention = static variable
    //Instance variables for SQLiteDatabase
    private Context mContext;
    private SQLiteDatabase mDatabase;



    static CrimeLab get(Context context) { //context used in Ch 14.

        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    /**
     * private so other classes will not be able to create a CrimeLab
     * @param context   //context used in Ch 14.
     */
    private CrimeLab(Context context) {

        //For applying application context to the database
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                //.getWritableDatabase() calls the CrimeBaseHelper methods depending on a few key
                // factors explained in CrimeBaseHelper
                .getWritableDatabase();
    }

    /**
     * This method is called when a user pressed the "new crime" menu option in the CrimeListFragment
     * @param c is the crime to be added
     */
    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();
        /*
        Database cursors are called cursors because they always have their finger on a particular place in a query.
        Move it to the first element by calling moveToFirst() and then read in row data.
        Advance to new row with moveToNext()
        until finally isAfterLast() tells you that pointer is off the end of the dataset.
         */

        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close(); //important to close the cursor
        }
        return crimes;
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
                );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    //A method to update rows in the database
    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        //pass the table name you want to update and the ContentValues you want to assign
        // to each row you update.
        mDatabase.update(CrimeTable.NAME, values,
                //specify which rows get updated
                CrimeTable.Cols.UUID + "= ?",
                new String[] {uuidString});
    }

    public void deleteCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID + " = ?", new String[] {uuidString});
    }

    public static ContentValues getContentValues(Crime crime) {
        //The Cols.NAMES  specify which column to insert or update data in.
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        return values;
    }


    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        /*
         * all a cursor does is give raw column values and needs to be called everytime a value is requested.
         * It is used in the CrimeCursorWrapper class
         */
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, // columms - null selects all columns
                whereClause,
                whereArgs,
                null, //groupBy
                null, //having
                null //orderBy
        );
        return new CrimeCursorWrapper(cursor);
    }
}

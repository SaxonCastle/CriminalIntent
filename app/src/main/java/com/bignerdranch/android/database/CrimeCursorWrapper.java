package com.bignerdranch.android.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.Crime;
import com.bignerdranch.android.database.CrimeDBSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

/**
 * Create a 'thin' wrapper around a Cursor.
 * Makes it possible to add new methods that operate on the underlying Cursor.
 */
public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /*
    * Mentioned add new method
    * Pull out relevant column data.
     */
    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);

        return crime;
    }
}

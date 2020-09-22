package com.bignerdranch.android;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private List<Crime> mCrimes;

    private static CrimeLab sCrimeLab; //s naming convention = static variable

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
        mCrimes = new ArrayList<>();
    }

    /**
     * This method is called when a user pressed the "new crime" menu option in the CrimeListFragment
     * @param c is the crime to be added
     */
    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }

        return null;
    }
}

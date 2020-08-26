package com.bignerdranch.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

/**
 * This Class holds the onCreate Bundle for anytime a new fragment is called.
 * It sets the content view to the corresponding layout file
 * and checks if there is not a fragment that already exists.
 *
 * It also now returns the CrimeFragment's new instance with crimeId as a 'put' to carry
 * information over multiple fragments.
 */
public class CrimeActivity extends SingleFragmentActivity {

    public static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeID) {
        //Create an explicit intent
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        //pass in a string key and the value the key maps to (crimeID).
        //In this case it's putExtra(String, Serializable) (UUID is a serializable object).
        intent.putExtra(EXTRA_CRIME_ID, crimeID);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}
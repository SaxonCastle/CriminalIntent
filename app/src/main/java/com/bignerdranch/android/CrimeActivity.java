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
 */
public class CrimeActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        //Fragment transactions are used to add, remove, attach, detach and replace fragments in the fragment list.
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new CrimeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment) //Notice this fragment is a new CrimeFragment() object
                    .commit(); }
    }
}
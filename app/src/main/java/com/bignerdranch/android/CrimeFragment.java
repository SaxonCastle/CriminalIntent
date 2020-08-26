package com.bignerdranch.android;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import java.util.UUID;

import static android.widget.CompoundButton.*;


public class CrimeFragment extends Fragment {

    //Initialise an object of the Crime class
    private Crime mCrime;

    //Initialise the EditTexts
    private EditText mTitleField;

    //Initialise the Buttons
    private Button mDateButton;

    //Initialise the CheckBoxes
    private CheckBox mSolvedCheckBox;

    /**
     * Notice this is public because they will be called by whatever activity is hosting the fragment
     * @param savedInstanceState Similar to an activity, a fragment can bundle to which it saves and retrieves its state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This getActivity.getIntent() method grabs the extra from CrimeActivity
        //The get intent method returns the Intent that was used to start CrimeActivity
        //You call getSerializableExtra(String) on the intent to pull the UUID out into a variable
        //This needs to be casted to UUID otherwise it produces a serializable crimeId.
        //After you have retrieved the ID, you use it to getch the Crime from CrimeLab.
        UUID crimeId = (UUID) getActivity().getIntent()
                .getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }


    /**
     * A method to inflate the fragment in the activity that wants to host it.
     * After the view is inflated, get a reference to the needed Views and add listeners to necessary members.
     *
     * @param inflater The activities method to call and host this fragment and points to its layout file.
     * @param container the views parent, needed to configure the widgets properly.
     * @param savedInstanceState to save and retrieve states.
     * @return the inflated views with its arguments.
     *
     * attachToRoot: tells the layout inflater whether to add the inflated view to the view's parent (false because we'll add the view in the activity's code)
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        //Start mTitleField EditText
        //The fragment class does not have a corresponding convenience method, so you have to call the real thing.
        mTitleField = v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //this space is intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //This is intentionally left blank
            }
        });

        //Start mDateButton Button
        mDateButton = v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        //Disabling the button ensures that it will not respond
        mDateButton.setEnabled(false);

        //Start mSolvedCheckBox CheckBox
        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }
}

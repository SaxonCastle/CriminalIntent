package com.bignerdranch.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class CrimeFragment extends Fragment {

    private Crime mCrime;

    /**
     * Notice this is public because they will be called by whatever activity is hosting the fragment
     * @param savedInstanceState Similar to an activity, a fragment can bundle to which it saves and retrieves its state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    /**
     * A method to inflate the fragment in the activity that wants to host it.
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
        return v;
    }
}

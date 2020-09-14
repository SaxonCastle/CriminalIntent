package com.bignerdranch.android;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    //Challenge Efficient RecylcerView
    private int mLastUpdatedPosition = -1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            if (mLastUpdatedPosition > -1) {
                mAdapter.notifyItemChanged(mLastUpdatedPosition);
                mLastUpdatedPosition = -1;
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     *
     * Define the viewholder that it will inflate and own your layout
     * defined as an inner class.
     */
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;
        private ImageView mSolvedImageView;


        public CrimeHolder (LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);
            mSolvedImageView = itemView.findViewById(R.id.crime_solved);

        }

        public void bind(Crime crime) {
            mCrime=crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(new SimpleDateFormat("EEEE, MMM d, yyyy", Locale.getDefault()).format(crime.getDate()));
            mSolvedImageView.setVisibility(crime.isSolved()?View.VISIBLE:View.GONE);
        }


        /**
         * You can tell CrimeFragment which Crime to display by passing the crime ID as an Intent extra when CrimeActivity is started.         *
         * @param view The view that the click was pressed
         */

        @Override
        public void onClick(View view) {
            //Makes an explicit intent call to CrimeActivity,
            //getActivity() is a function from the Fragment inbuilt class
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            mLastUpdatedPosition = this.getAdapterPosition(); //Challenge Efficient RecyclerCode
            startActivity(intent);
        }
    }




    /**
     * When the recycler view needs to display a new ViewHolder or connect a Crime object to an existing
     * ViewHolder, it will ask this adapter for help by calling a method on it.
     */

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        /**
         *
         * onCreateViewHolder is called by the RecyclerView
         *  when it needs a new ViewHolder to display an item with.
         *
         *  Adapter must override an onBindViewHolder
         *
         * @param parent CrimeAdapter
         * @param viewType set by the above Recycler View
         * @return a CrimeHolder view object
         */
        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

}

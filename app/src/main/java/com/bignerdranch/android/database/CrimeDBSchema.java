package com.bignerdranch.android.database;

public class CrimeDBSchema {
    /*
    The CrimeTable class only exists to define the String constants needed to describe the moving pieces of your table definition.
     */
    public static final class CrimeTable {
        public static final String NAME = "crimes";

        //Describe the columns
        //Can call these with CrimeTable.Cols.TITLE for example
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}

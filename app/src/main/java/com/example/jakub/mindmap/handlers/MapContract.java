package com.example.jakub.mindmap.handlers;

import android.provider.BaseColumns;

/**
 * Created by Jakub on 2015-08-06.
 */
public class MapContract {
    public static final String SQL_CREATE_ENTRIES =               //zmienic
            "CREATE TABLE " + NodeEntry.TABLE_NAME + " (" +
                    NodeEntry._ID + " INTEGER PRIMARY KEY, " +
                    NodeEntry.COLUMN_TEXT + " TEXT " + "," +
                    NodeEntry.COLUMN_X + " INTEGER " + "," +
                    NodeEntry.COLUMN_Y + " INTEGER " + "," +
                    NodeEntry.COLUMN_PARENT + " INTEGER " +
            " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NodeEntry.TABLE_NAME;

    public MapContract() {
    }

    public static abstract class NodeEntry implements BaseColumns {
        public static final String TABLE_NAME = "nodes";
        public static final String COLUMN_TEXT = "inputText";
        public static final String COLUMN_X = "x";
        public static final String COLUMN_Y= "y";
        public static final String COLUMN_PARENT= "parent";

    }

    public static abstract class MapEntry implements  BaseColumns{
        public static final String TABLE_NAME = "maps";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MOTIVE = "motive";
        public static final String COLUMN_ARRANGEMENT = "arrangement";


    }
}

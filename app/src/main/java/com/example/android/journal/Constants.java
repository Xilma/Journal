package com.example.android.journal;

import android.provider.BaseColumns;

public interface Constants extends BaseColumns {

    String TABLE_NAME = "Journals";

    //Columns in the Events database
    String TITLE = "title";
    String DESCRIPTION = "description";
}

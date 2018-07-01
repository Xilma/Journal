package com.example.android.journal.view;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.journal.R;
import com.example.android.journal.model.JournalData;
import com.example.android.journal.presenters.Journal;

import static com.example.android.journal.model.Constants.DESCRIPTION;
import static com.example.android.journal.model.Constants.TABLE_NAME;
import static com.example.android.journal.model.Constants.TITLE;

public class AddJournalActivity extends AppCompatActivity {

    private EditText editTitle, journalText;
    private JournalData journalData;
    private Journal journal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);

        journalData = new JournalData(this);

        editTitle = findViewById(R.id.journal_title);
        journalText = findViewById(R.id.journal_paragraph);
        Button saveButton = findViewById(R.id.save_button);
        Button discardButton = findViewById(R.id.discard_button);

        discardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(AddJournalActivity.this, R.string.dismissed, Toast.LENGTH_SHORT).show();
                Intent main = new Intent (AddJournalActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addJournal();
            }
        });

        journal = new Journal();
    }

    public boolean addJournal() {
            SQLiteDatabase db = journalData.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(TITLE, editTitle.getText().toString());
            values.put(DESCRIPTION, journalText.getText().toString());

            Long result = db.insertOrThrow(TABLE_NAME, null, values);
            if (result == -1) {
                Toast.makeText( AddJournalActivity.this, "Data NOT Inserted", Toast.LENGTH_LONG ).show();
                return false;
            } else{
                Toast.makeText( AddJournalActivity.this, "Data Inserted", Toast.LENGTH_LONG ).show();
                Intent main = new Intent(AddJournalActivity.this, MainActivity.class);
                startActivity(main);
                finish();
                return true;
            }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        AddJournalActivity.this.finish();
    }
}

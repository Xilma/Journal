package com.example.android.journal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.android.journal.Constants.DESCRIPTION;
import static com.example.android.journal.Constants.TABLE_NAME;
import static com.example.android.journal.Constants.TITLE;

public class EditActivity extends AppCompatActivity {

    private EditText editTitle, journalText;
    private JournalData journalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        journalData = new JournalData(this);

        editTitle = findViewById(R.id.journal_title);
        journalText = findViewById(R.id.journal_paragraph);
        Button saveButton = findViewById(R.id.save_button);
        Button discardButton = findViewById(R.id.discard_button);

        Intent editIntent = getIntent();
        String editHeading = editIntent.getStringExtra("Title");
        String editParagraph = editIntent.getStringExtra("Description");

        editTitle.setText(editHeading);
        journalText.setText(editParagraph);

        discardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(EditActivity.this, R.string.dismissed, Toast.LENGTH_SHORT).show();
                Intent main = new Intent (EditActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    //method to update data to Journal database
    public void updateData(){
        SQLiteDatabase db = journalData.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, editTitle.getText().toString() );
        values.put(DESCRIPTION, journalText.getText().toString() );

        String[] params = new String[]{editTitle.getText().toString()};
        try{
            db.update(TABLE_NAME, values, TITLE + "= ?", params);
            Toast.makeText( EditActivity.this, "Journal Updated", Toast.LENGTH_LONG ).show();
            Intent main = new Intent (EditActivity.this, MainActivity.class);
            startActivity(main);
            finish();
        }catch(SQLException e){
            String message = e.getMessage();
            Toast.makeText( EditActivity.this, message, Toast.LENGTH_LONG ).show();
        }finally{
            journalData.close();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        EditActivity.this.finish();
    }
}

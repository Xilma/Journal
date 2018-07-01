package com.example.android.journal.view;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.journal.R;
import com.example.android.journal.model.JournalData;

import static com.example.android.journal.model.Constants.DESCRIPTION;
import static com.example.android.journal.model.Constants.TABLE_NAME;
import static com.example.android.journal.model.Constants.TITLE;

public class ViewActivity extends AppCompatActivity {

    private EditText editTitle, journalText;
    private JournalData journalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        journalData = new JournalData(this);

        editTitle = findViewById(R.id.journal_title);
        journalText = findViewById(R.id.journal_paragraph);
        Button editButton = findViewById(R.id.edit_button);
        Button deleteButton = findViewById(R.id.discard_button);

        Intent editIntent = getIntent();
        String editHeading = editIntent.getStringExtra("Title");
        String editParagraph = editIntent.getStringExtra("Description");

        editTitle.setText(editHeading);
        journalText.setText(editParagraph);

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                deleteData();
                Toast.makeText(ViewActivity.this, R.string.dismissed, Toast.LENGTH_LONG).show();
                Intent main = new Intent (ViewActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                CharSequence titleEdit = editTitle.getText();
                CharSequence descriptionEdit = journalText.getText();

                Intent editJournal = new Intent (ViewActivity.this, EditActivity.class);
                editJournal.putExtra("Title", titleEdit);
                editJournal.putExtra("Description", descriptionEdit);
                startActivity(editJournal);
                finish();
            }
        });
    }

    //method to delete data to Journal database
    public void deleteData(){
        SQLiteDatabase db = journalData.getWritableDatabase();
        String[] params = new String[]{editTitle.getText().toString()};
        String[] params_one = new String[]{journalText.getText().toString()};

        try{
            db.delete(TABLE_NAME, TITLE + "= ?", params);
            db.delete(TABLE_NAME, DESCRIPTION + "= ?", params_one);

            Toast.makeText( ViewActivity.this, "Journal Deleted", Toast.LENGTH_LONG ).show();
            Intent main = new Intent (ViewActivity.this, MainActivity.class);
            startActivity(main);
            finish();
        }catch(SQLException e){
            String message = e.getMessage();
            Toast.makeText( ViewActivity.this, message, Toast.LENGTH_LONG ).show();
        }finally{
            journalData.close();
        }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        ViewActivity.this.finish();
    }
}

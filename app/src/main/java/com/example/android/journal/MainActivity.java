package com.example.android.journal;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.journal.Constants.TABLE_NAME;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<RecyclerItem> listItems;
    private JournalData journalData;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        journalData = new JournalData(this);
        cursor = null;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNewJournal = new Intent(MainActivity.this, AddJournalActivity.class);
                startActivity(addNewJournal);
                finish();
            }
        });

        populateViews();

    }

    public void populateViews(){
        /*Perform a managed query. The Activity will handle closing and
        re-querying the cursor when needed.*/
        SQLiteDatabase db = journalData.getReadableDatabase();

        //Use of raw query to collect data
        try{
            cursor = db.rawQuery(" select * from " + TABLE_NAME, null);
            showJournals(cursor);
        }catch(SQLException e){
            String message = e.getMessage();
            Toast.makeText( MainActivity.this, message, Toast.LENGTH_LONG ).show();
        }finally{
            cursor.close();
            journalData.close();
        }
    }

    //Displaying the query results
    private void showJournals(Cursor cursor){
        while(cursor.moveToNext()){
            String title = cursor.getString(0);
            String description = cursor.getString(1);

            listItems.add(new RecyclerItem(title, description));
        }

        //Set adapter
        JournalAdapter adapter = new JournalAdapter(listItems, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SplashActivity.class));
            finish();
            return true;
        }

        if (id == R.id.action_settings) {
            Intent logout = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(logout);
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}

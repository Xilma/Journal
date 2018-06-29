package com.example.android.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private JournalAdapter mJournalAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNewJournal = new Intent(MainActivity.this, AddJournalActivity.class);
                startActivity(addNewJournal);
                finish();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_journal);

        /*
         * LinearLayoutManager can support HORIZONTAL or VERTICAL orientations. The reverse layout
         * parameter is useful mostly for HORIZONTAL layouts that should reverse for right to left
         * languages.
         */
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //Set the layout manager on the recycler view
        mRecyclerView.setLayoutManager(layoutManager);

        //Use setHasFixedSize(true) on mRecyclerView to designate that all items in the list will have the same size
        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        //Set mJournalAdapter equal to a new JournalAdapter
        /*
         * The JournalAdapter is responsible for linking the journal data with the Views that
         * will end up displaying the journal data.
         */
        mJournalAdapter = new JournalAdapter();

        //Use mRecyclerView.setAdapter and pass in mJournalAdapter
        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mJournalAdapter);

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

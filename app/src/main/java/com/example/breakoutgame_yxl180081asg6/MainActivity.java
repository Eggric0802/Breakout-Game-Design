/*=============================================================================
 |   Assignment:  CS6326 Project 6 Breakout Game Design
 |       Author:  YuChuan Lin
 |     Language:  Android
 |    File Name:  MainActivity.java
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  A breakout game play button , a Ranking page button
 |
 |  File Purpose: Contains the main options of the game
 *===========================================================================*/

package com.example.breakoutgame_yxl180081asg6;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import android.os.Parcelable;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button play_bt, rank_bt, setting_bt;
    private EditText playername;
//    private String date = new SimpleDateFormat("mm/dd/yyyy", Locale.getDefault()).format(new Date());
//    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
//    private static final int FIRST_ACTIVITY_REQUEST_CODE = 1;
//    String thisPlayerName, thisScoreToString;
//    int thisScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        playername = findViewById(R.id.playerName_input);
        play_bt = findViewById(R.id.Play_button);


            play_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playername.getText().toString().isEmpty()) {
                        Context context = getApplicationContext();
                        CharSequence text = "Please Enter Player Name";
                        int dur = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, dur);
                        toast.show();
                        return;
                    }
                    Intent intent = new Intent(v.getContext(), GameMain.class);
                    String name = playername.getText().toString();
                    intent.putExtra("Name", name);
                    startActivity(intent);
                }
            });

        rank_bt = findViewById(R.id.Rank_button);
        rank_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToRank = new Intent(v.getContext(), Ranking.class);
                startActivity(intentToRank);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {     //Return the result from second page
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // check that it is the SecondActivity with an OK result
//        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
//                Intent intentToRank = new Intent(this, Ranking.class);
//                String thisPlayName = getIntent().getStringExtra("Name");
//                Int thisScore = getIntent().getIntExtra("Score",0);
//                thisScoreToString = Integer.toString(thisScore);
//            }
//        }
//    }

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

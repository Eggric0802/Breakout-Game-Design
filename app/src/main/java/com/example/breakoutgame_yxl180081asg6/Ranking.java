/*=============================================================================
 |   Assignment:  CS6326 Project 6 Breakout Game Design
 |       Author:  YuChuan Lin
 |     Language:  Android
 |    File Name:  Ranking.java
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  Display the rank list view and sort it by its score
 |
 *===========================================================================*/
package com.example.breakoutgame_yxl180081asg6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Collections;
import java.util.List;

public class Ranking extends AppCompatActivity {

    private Adapter adapter;
    private FileManager fileManager = new FileManager();
    private ListView listView;
    private List<PlayerInfo> player;
//    private static final int FIRST_ACTIVITY_REQUEST_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        listView = findViewById(R.id.rankListView);
        player = fileManager.fileReader(this);
        adapter = new Adapter(getApplicationContext(), R.layout.listview, player);

        if(listView != null) {
            listView.setAdapter(adapter);
        }
        updateListView();

        }

        private void updateListView(){
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String score = intent.getStringExtra("Score");
        if (name != null){
            PlayerInfo record = new PlayerInfo(name, score);
            player.add(record);
//            Collections.sort(player);
            if (player.size() > 8){
                player.remove(player.size() - 1);}
                fileManager.fileWriter(player, this);
                adapter = new Adapter(getApplicationContext(), R.layout.listview, player);
                adapter.notifyDataSetChanged();


        }
        }



//        String playerName = data.getStringExtra("Name");
//        String score = data.getStringExtra("Score");
////                String Date = data.getStringExtra("Date");
//        PlayerInfo playerInfo = new PlayerInfo(playerName,score);
//        player.add(playerInfo);
//        fileManager.fileWriter(player,this);
//        adapter.notifyDataSetChanged();

//    }

//    @Override
//    public void onBackPressed(){
//        super.onBackPressed();
//        finish();
//    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {     //Return the result from second page
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // check that it is the SecondActivity with an OK result
//        if (requestCode == FIRST_ACTIVITY_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
//                String playerName = getIntent().getStringExtra("Name");
//                String score = getIntent().getStringExtra("Score");
//                //                String Date = data.getStringExtra("Date");
//                PlayerInfo playerInfo = new PlayerInfo(playerName, score);
//                player.add(playerInfo);
//                fileManager.fileWriter(player, this);
//                player = fileManager.fileReader(this);
//                adapter = new Adapter(getApplicationContext(), R.layout.listview, player);
//                adapter.notifyDataSetChanged();
//            }
//        }
//    }
}

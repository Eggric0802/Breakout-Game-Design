package com.example.breakoutgame_yxl180081asg6;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;


//Infos about the player
public class PlayerInfo implements Comparable<PlayerInfo> {
    private String playerName;
    private String score;
//    private String date;

    public PlayerInfo(String PlayerName, String Score){
        playerName = PlayerName;
        score = Score;
//        date = Date;
    }

    public String getPlayerName(){return playerName;}

    public String getScore(){return score;}

//    public  String  getDate(){return date;}

    public int compareTo(PlayerInfo player){
//        if (Integer.parseInt(this.getScore()) == Integer.parseInt(player.getScore())) {
//            DateFormat format = new SimpleDateFormat("mm/dd/yyyy");
//            Date date1 = format.parse(this.getDate(), new ParsePosition(0));
//            Date date2 = format.parse(player.getDate(), new ParsePosition(0));
//            return date2.compareTo(date1);
//        }
//        else {
            return Integer.parseInt(player.getScore()) - Integer.parseInt(this.getScore());
//        }

    }







}

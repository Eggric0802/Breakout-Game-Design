package com.example.breakoutgame_yxl180081asg6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//Adapter for displaying the data and information
public class Adapter extends ArrayAdapter<PlayerInfo>{
    Context context;
    int layout_id;
    List<PlayerInfo> data = new ArrayList<>();

    public Adapter(Context context, int id, List<PlayerInfo> data){
        super(context, id, data);
        this.context = context;
        this.layout_id = id;
        this.data = data;
    }

    @Override
    public PlayerInfo getItem(int position) {return super.getItem(position);}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //inflate the layout for a single row
        View row = convertView;
        LayoutInflater inflater = LayoutInflater.from(context);
        row = inflater.inflate(layout_id,parent,false);

        //get a reference to the different view elements we wish to update
        TextView name_V = (TextView) row.findViewById(R.id.Name);
        TextView score_V = (TextView) row.findViewById(R.id.Score);
//        TextView date_V = (TextView) row.findViewById(R.id.Date);

        //get the data from data array
        PlayerInfo player = data.get(position);

        //Setting the view to reflect the data we need to display
        name_V.setText(player.getPlayerName());
        score_V.setText(player.getScore());
//        date_V.setText(player.getDate());

        return row;

    }




}

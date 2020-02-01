package com.example.breakoutgame_yxl180081asg6;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Take care of the file reading/writing
public class FileManager {
    private String filePath = "ScoreRanking.txt";

    public void fileWriter (List<PlayerInfo> playerInfos, Context context) {
        File file = new File(context.getFilesDir(), filePath);
        if (file.exists()){
            file.delete();
            Collections.sort(playerInfos);
            writeIntoFile(playerInfos, context);
            }
         else {
            Collections.sort(playerInfos);
            writeIntoFile(playerInfos, context);
            }
        }

        public List<PlayerInfo> fileReader(Context context)
        {
            List<PlayerInfo> list = new ArrayList<>();

            try{
                InputStream input = context.openFileInput(filePath);
                if (input != null){
                    InputStreamReader inputReader = new InputStreamReader(input);
                    BufferedReader bufferedReader = new BufferedReader(inputReader);
                    String line;
                    while ( (line = bufferedReader.readLine()) != null){
                            list.add(new PlayerInfo(line.split("\t")[0],line.split("\t")[1]));
                    }
                    input.close();
                }
            }
            catch (FileNotFoundException  e){
                Log.e("FileNotFoundException", "Failed finding:" + e.toString());
            }
            catch (IOException e){
                Log.e("IOException", "Failed reading:" + e.toString());
            }
            return list;
        }

        private void writeIntoFile(List<PlayerInfo> playerInfos, Context context){
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filePath, Context.MODE_PRIVATE));
                for (PlayerInfo object : playerInfos) {
                    String info = object.getPlayerName() + "\t" + object.getScore() + "\n";
                    outputStreamWriter.write(info);
                }
                outputStreamWriter.close();
            }
            catch (IOException e) {
                Log.e("IOException", "Failed writing:" + e.toString());
            }
        }

}

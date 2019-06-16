package com.example.gamecenterasg.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamecenterasg.DatabaseHelper;
import com.example.gamecenterasg.Model.MyGames;
import com.example.gamecenterasg.R;

import java.util.ArrayList;
import java.util.Random;

public class MyGamesAdapter extends BaseAdapter {
    private Context ctx;
    public ArrayList<MyGames> myGamesList;
    public DatabaseHelper gameCenterDB;

    public MyGamesAdapter(Context ctx, ArrayList<MyGames> myGamesList) {
        this.ctx = ctx;
        this.myGamesList = myGamesList;
        this.gameCenterDB = new DatabaseHelper(ctx);
    }

    @Override
    public int getCount() {
        return myGamesList.size();
    }

    @Override
    public Object getItem(int i) {
        return myGamesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(ctx, R.layout.mygames_list, null);
        ImageView gameImg = v.findViewById(R.id.gameImg);
        TextView gameName = v.findViewById(R.id.gameName);
        TextView playHourLabel = v.findViewById(R.id.playHourLabel);
        final TextView gamePlayHour = v.findViewById(R.id.gamePlayHour);
        TextView gameGenre = v.findViewById(R.id.gameGenre);
        TextView gameDesc = v.findViewById(R.id.gameDesc);
        TextView playBtn = v.findViewById(R.id.playBtn);

        Typeface robotoLight = Typeface.createFromAsset(ctx.getAssets(), "fonts/roboto/Roboto-Light.ttf");
        Typeface robotoThin = Typeface.createFromAsset(ctx.getAssets(), "fonts/roboto/Roboto-Thin.ttf");
        Typeface robotoRegular = Typeface.createFromAsset(ctx.getAssets(), "fonts/roboto/Roboto-Regular.ttf");

        playHourLabel.setTypeface(robotoLight);
        gameGenre.setTypeface(robotoLight);
        gameDesc.setTypeface(robotoLight);
        playBtn.setTypeface(robotoLight);

        gameName.setText(myGamesList.get(i).getGameName());
        gamePlayHour.setText(myGamesList.get(i).getPlayingHour());
        gameGenre.setText(myGamesList.get(i).getGameGenre());
        gameDesc.setText(myGamesList.get(i).getGameDesc().substring(0,60) + "...");
        gameImg.setImageResource(myGamesList.get(i).getGameImages());

        playBtn.setTag(i);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer)view.getTag();
                String playingHour = genPlayHour(myGamesList.get(position).getPlayingHour());
                gameCenterDB.updatePlayingHour(myGamesList.get(position).getMyGameID(), playingHour);
                myGamesList.get(position).setPlayingHour(playingHour);
                MyGamesAdapter.this.notifyDataSetChanged();
            }
        });

        return v;
    }

    private String genPlayHour(String currPlayHour){
        int currHour = Integer.parseInt(currPlayHour.split(":")[0]);
        int currMinute = Integer.parseInt(currPlayHour.split(":")[1]);
        int currSecond = Integer.parseInt(currPlayHour.split(":")[2]);
        Random rand = new Random();
        int r1 = (int)rand.nextInt(10)+1;
        int r2 = (int)rand.nextInt(10)+1;
        int r3 = (int)rand.nextInt(10)+1;
        String num1 = (currHour+r1)<10?"0"+(currHour+r1):String.valueOf(currHour+r1);
        String num2 = (currMinute+r2)<10?"0"+(currMinute+r2):String.valueOf(currMinute+r2);
        String num3 = (currSecond+r3)<10?"0"+(currSecond+r3):String.valueOf(currSecond+r3);

        return num1 + ":" + num2 + ":" + num3;
    }
}

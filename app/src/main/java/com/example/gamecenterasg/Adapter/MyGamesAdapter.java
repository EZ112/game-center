package com.example.gamecenterasg.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamecenterasg.Model.MyGames;
import com.example.gamecenterasg.Model.Users;
import com.example.gamecenterasg.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyGamesAdapter extends BaseAdapter {
    private Context ctx;
    public ArrayList<MyGames> allMyGamesList = new ArrayList<>();
    public ArrayList<MyGames> myGamesList;

    SharedPreferences appSP;
    SharedPreferences.Editor prefEditor;
    Gson gson = new Gson();
    String json;

    public MyGamesAdapter(Context ctx, ArrayList<MyGames> myGamesList) {
        this.ctx = ctx;
        this.myGamesList = myGamesList;
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

        gameName.setText(myGamesList.get(i).getGames().getGameName());
        gamePlayHour.setText(myGamesList.get(i).getPlayingHour());
        gameGenre.setText(myGamesList.get(i).getGames().getGameGenre());
        gameDesc.setText(myGamesList.get(i).getGames().getGameDesc().substring(0,60) + "...");
        gameImg.setImageResource(myGamesList.get(i).getGames().getGameImages());

        playBtn.setTag(i);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appSP = PreferenceManager.getDefaultSharedPreferences(ctx);
                prefEditor = appSP.edit();
                json = appSP.getString("myGameListSP","");
                if(appSP.contains("myGameListSP"))
                    allMyGamesList = gson.fromJson(json, new TypeToken<ArrayList<MyGames>>(){}.getType());

                int position = (Integer)view.getTag();
                String playHour = genPlayHour();
                String userID = myGamesList.get(position).getUsers().getUserID();
                int myGameID = myGamesList.get(position).getMyGameID();

                for(MyGames myGames : allMyGamesList){
                    if(myGames.getUsers().getUserID().equals(userID) && myGames.getMyGameID() == myGameID)
                        myGames.setPlayingHour(playHour);
                }

                myGamesList.get(position).setPlayingHour(playHour);

                json = gson.toJson(allMyGamesList);
//                Log.i("PlayHour",json);
                prefEditor.putString("myGameListSP", json);
                prefEditor.apply();

                MyGamesAdapter.this.notifyDataSetChanged();
            }
        });

        return v;
    }

    private String genPlayHour(){
        Random rand = new Random();
        int r1 = (int)rand.nextInt(10)+1;
        int r2 = (int)rand.nextInt(10)+1;
        int r3 = (int)rand.nextInt(10)+1;
        String num1 = r1<10?"0"+r1:String.valueOf(r1);
        String num2 = r2<10?"0"+r2:String.valueOf(r2);
        String num3 = r3<10?"0"+r3:String.valueOf(r3);

        return num1 + ":" + num2 + ":" + num3;
    }
}

package com.example.gamecenter.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gamecenter.Model.Games;
import com.example.gamecenter.R;

import java.util.List;

public class GamesAdapter extends BaseAdapter {
    private Context ctx;
    public List<Games> gamesList;

    public GamesAdapter(Context ctx, List<Games> gamesList) {
        this.ctx = ctx;
        this.gamesList = gamesList;
    }

    @Override
    public int getCount() {
        return gamesList.size();
    }

    @Override
    public Object getItem(int i) {
        return gamesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(ctx, R.layout.games_list, null);
        ImageView gameImg = v.findViewById(R.id.gameImg);
        TextView gameName = v.findViewById(R.id.gameName);
        RatingBar gameRating = v.findViewById(R.id.gameRating);
        TextView gameGenre = v.findViewById(R.id.gameGenre);
        TextView gameDesc = v.findViewById(R.id.gameDesc);

        Typeface robotoLight = Typeface.createFromAsset(ctx.getAssets(), "fonts/roboto/Roboto-Light.ttf");
        Typeface robotoThin = Typeface.createFromAsset(ctx.getAssets(), "fonts/roboto/Roboto-Thin.ttf");
        Typeface robotoRegular = Typeface.createFromAsset(ctx.getAssets(), "fonts/roboto/Roboto-Regular.ttf");

        gameName.setTypeface(robotoRegular);
        gameGenre.setTypeface(robotoLight);
        gameDesc.setTypeface(robotoLight);


        gameName.setText(gamesList.get(i).getGameName());
        gameRating.setRating((float) gamesList.get(i).getGameRating());
        gameGenre.setText(gamesList.get(i).getGameGenre());
        gameDesc.setText(gamesList.get(i).getGameDesc().substring(0,85) + "...");
        gameImg.setImageResource(gamesList.get(i).getGameImages());

        return v;
    }
}

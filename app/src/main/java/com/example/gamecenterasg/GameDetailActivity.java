package com.example.gamecenterasg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamecenterasg.Model.Games;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GameDetailActivity extends AppCompatActivity {

    ImageView gameImg;
    TextView gameName, stockLabel, gameStock, priceLabel, gamePrice, gameGenre, gameDesc, buyBtn;
    RatingBar gameRating;

    Games game;

    SharedPreferences appSP;
    SharedPreferences.Editor prefEditor;
    Gson gson = new Gson();
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Light.ttf");

        SpannableString s = new SpannableString(getString(R.string.title_games_detail));
        s.setSpan(new TypefaceSpan(this,"Roboto-Thin"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);

        appSP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefEditor = appSP.edit();

        json = appSP.getString("currGameSP","");

        if(appSP.contains("currGameSP"))
            game = gson.fromJson(json, Games.class);

        gameImg = findViewById(R.id.gameImg);
        gameName = findViewById(R.id.gameName);
        stockLabel = findViewById(R.id.stockLabel);
        gameStock = findViewById(R.id.gameStock);
        priceLabel = findViewById(R.id.priceLabel);
        gamePrice = findViewById(R.id.gamePrice);
        gameGenre = findViewById(R.id.gameGenre);
        gameDesc = findViewById(R.id.gameDesc);
        gameRating = findViewById(R.id.gameRating);
        buyBtn = findViewById(R.id.buyBtn);

        stockLabel.setTypeface(robotoLight);
        priceLabel.setTypeface(robotoLight);
        gameGenre.setTypeface(robotoLight);
        gameDesc.setTypeface(robotoLight);

        String moneyFormat = NumberFormat.getNumberInstance(Locale.US).format(game.getGamePrice());

        gameImg.setImageResource(game.getGameImages());
        gameName.setText(game.getGameName());
        gameStock.setText(String.valueOf(game.getGameStock()));
        gamePrice.setText("Rp " + moneyFormat + ".00");
        gameGenre.setText(game.getGameGenre());
        gameDesc.setText(game.getGameDesc());
        gameRating.setRating(game.getGameRating());

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameDetailActivity.this, PaymentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}

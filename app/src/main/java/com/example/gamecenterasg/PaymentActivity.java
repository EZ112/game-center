package com.example.gamecenterasg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamecenterasg.Model.Games;
import com.example.gamecenterasg.Model.MyGames;
import com.example.gamecenterasg.Model.Users;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    ImageView gameImg;
    TextView paymentHead, gameName, gameStock, gamePrice, gameGenre, payBtn;
    EditText moneyIn;
    Users user;
    Games game;

    ArrayList<MyGames> myGamesList = new ArrayList<>();

    SharedPreferences appSP;
    SharedPreferences.Editor prefEditor;
    Gson gson = new Gson();
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Thin.ttf");

        SpannableString s = new SpannableString(getString(R.string.app_name));
        s.setSpan(new TypefaceSpan(this,"Roboto-Thin"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);

        appSP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefEditor = appSP.edit();

        json = appSP.getString("loggedInUser","");
        if(appSP.contains("loggedInUser"))
            user = gson.fromJson(json, Users.class);

        json = appSP.getString("currGameSP","");
        if(appSP.contains("currGameSP"))
            game = gson.fromJson(json, Games.class);

        json = appSP.getString("myGameListSP","");
        if(appSP.contains("myGameListSP"))
            myGamesList = gson.fromJson(json, new TypeToken<ArrayList<MyGames>>(){}.getType());

        paymentHead = findViewById(R.id.paymentHead);

        paymentHead.setTypeface(robotoThin);

        gameImg = findViewById(R.id.gameImg);
        gameName = findViewById(R.id.gameName);
        gameStock = findViewById(R.id.gameStock);
        gamePrice = findViewById(R.id.gamePrice);
        gameGenre = findViewById(R.id.gameGenre);
        payBtn = findViewById(R.id.payBtn);

        String moneyFormat = NumberFormat.getNumberInstance(Locale.US).format(game.getGamePrice());

        gameImg.setImageResource(game.getGameImages());
        gameName.setText(game.getGameName());
        gameStock.setText(String.valueOf(game.getGameStock()));
        gamePrice.setText("Rp " + moneyFormat + ".00");
        gameGenre.setText(game.getGameGenre());

        moneyIn = findViewById(R.id.moneyIn);

        moneyIn.setText("Rp ");
        Selection.setSelection(moneyIn.getText(), moneyIn.getText().length());

        moneyIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().startsWith("Rp ")){
                    moneyIn.setText("Rp ");
                    Selection.setSelection(moneyIn.getText(), moneyIn.getText().length());
                }
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = moneyIn.getText().toString();
                if(!num.equals("Rp ")){
                    long change;
                    long in = Long.parseLong(num.substring(3, num.length()));
                    long price = game.getGamePrice();
                    change = in - price;

                    if(change >= 0){
                        myGamesList.add(new MyGames(myGamesList.size()+1, "00:00:00", game, user));

                        json = gson.toJson(myGamesList);
                        prefEditor.putString("myGameListSP", json);
                        prefEditor.apply();
//                        Log.i("myGameList : ", json);

                        Toast.makeText(PaymentActivity.this, "Your change is Rp "+String.valueOf(change),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else
                        Toast.makeText(PaymentActivity.this, "Insufficient Money", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(PaymentActivity.this, "You must input the money",Toast.LENGTH_LONG).show();
            }
        });

    }
}

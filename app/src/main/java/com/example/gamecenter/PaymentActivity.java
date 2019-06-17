package com.example.gamecenter;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamecenter.Model.Games;
import com.example.gamecenter.Model.Users;
import com.example.gamecenter.R;

import java.text.NumberFormat;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    DatabaseHelper gameCenterDB;
    ImageView gameImg;
    TextView paymentHead, gameName, gameStock, gamePrice, gameGenre, payBtn;
    RatingBar gameRating;
    EditText moneyIn;
    Users user;
    Games game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Thin.ttf");

        SpannableString s = new SpannableString(getString(R.string.app_name));
        s.setSpan(new TypefaceSpan(this,"Roboto-Thin"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);

        user = HomeActivity.user;
        game = GamesActivity.game;

        gameCenterDB = new DatabaseHelper(this);

        paymentHead = findViewById(R.id.paymentHead);

        paymentHead.setTypeface(robotoThin);

        gameImg = findViewById(R.id.gameImg);
        gameName = findViewById(R.id.gameName);
        gameStock = findViewById(R.id.gameStock);
        gamePrice = findViewById(R.id.gamePrice);
        gameGenre = findViewById(R.id.gameGenre);
        gameRating = findViewById(R.id.gameRating);
        payBtn = findViewById(R.id.payBtn);

        String moneyFormat = NumberFormat.getNumberInstance(Locale.US).format(game.getGamePrice());

        gameImg.setImageResource(game.getGameImages());
        gameName.setText(game.getGameName());
        gameStock.setText(String.valueOf(game.getGameStock()));
        gamePrice.setText("Rp " + moneyFormat + ".00");
        gameGenre.setText(game.getGameGenre());
        gameRating.setRating((float) game.getGameRating());

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
                          gameCenterDB.addMyGame("00:00:00", game.getGameID(), user.getUserID());

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

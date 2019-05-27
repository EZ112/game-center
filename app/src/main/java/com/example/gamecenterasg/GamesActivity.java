package com.example.gamecenterasg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamecenterasg.Adapter.GamesAdapter;
import com.example.gamecenterasg.Model.Games;
import com.example.gamecenterasg.Model.Users;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class GamesActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener{

    ListView gamesView;
    GamesAdapter gamesAdapter;
    ArrayList<Games> gamesList = new ArrayList<>();
    Intent intent;

    Users user;

    ArrayList<String> gameNames, gameDescs, gameGenres;
    ArrayList<Float> gameRatings;
    ArrayList<Integer> gameStocks, gamePrices, gameImages;

    SharedPreferences appSP;
    SharedPreferences.Editor prefEditor;
    Gson gson = new Gson();
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        SpannableString s = new SpannableString(getString(R.string.title_games_list));
        s.setSpan(new TypefaceSpan(this,"Roboto-Thin"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(s);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        appSP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefEditor = appSP.edit();

        json = appSP.getString("loggedInUser","");
        user = gson.fromJson(json, Users.class);

        View headerView = navigationView.getHeaderView(0);
        TextView navUser = headerView.findViewById(R.id.navUser);
        TextView navEmail = headerView.findViewById(R.id.navEmail);

        navUser.setText(user.getUserName());
        navEmail.setText(user.getUserEmail());

        json = appSP.getString("gamesSP","");

//        Log.i("games",json);

        if(appSP.contains("gamesSP"))
            gamesList = gson.fromJson(json, new TypeToken<ArrayList<Games>>(){}.getType());
        else {
            gameNames = new ArrayList<>();
            gameDescs = new ArrayList<>();
            gameGenres = new ArrayList<>();
            gameRatings = new ArrayList<>();
            gameStocks = new ArrayList<>();
            gamePrices = new ArrayList<>();
            gameImages = new ArrayList<>();

            gameNames.add("Nier: Automata");
            gameDescs.add("Lorem ipsum dolor sit amet consectetur adipisicing elit. Inventore, tempore aliquam? Veritatis, vitae reiciendis consectetur, corrupti laudantium inventore.");
            gameGenres.add("Action, JRPG");
            gameRatings.add(4.5f);
            gameStocks.add(92);
            gamePrices.add(350000);
            gameImages.add(R.drawable.game1);

            gameNames.add("Final Fantasy XV");
            gameDescs.add("Lorem ipsum dolor sit amet consectetur adipisicing elit. Inventore, tempore aliquam? Veritatis, vitae reiciendis consectetur, corrupti laudantium inventore.");
            gameGenres.add("Action, JRPG");
            gameRatings.add(3.5f);
            gameStocks.add(84);
            gamePrices.add(250000);
            gameImages.add(R.drawable.game2);

            gameNames.add("Steins; Gate 0");
            gameDescs.add("Lorem ipsum dolor sit amet consectetur adipisicing elit. Inventore, tempore aliquam? Veritatis, vitae reiciendis consectetur, corrupti laudantium inventore.");
            gameGenres.add("Light Novel");
            gameRatings.add(5.0f);
            gameStocks.add(73);
            gamePrices.add(250000);
            gameImages.add(R.drawable.game3);

            gameNames.add("Devil May Cry 5");
            gameDescs.add("Lorem ipsum dolor sit amet consectetur adipisicing elit. Inventore, tempore aliquam? Veritatis, vitae reiciendis consectetur, corrupti laudantium inventore.");
            gameGenres.add("Action, JRPG");
            gameRatings.add(4.0f);
            gameStocks.add(36);
            gamePrices.add(250000);
            gameImages.add(R.drawable.game4);

            gameNames.add("Battlefield V");
            gameDescs.add("Lorem ipsum dolor sit amet consectetur adipisicing elit. Inventore, tempore aliquam? Veritatis, vitae reiciendis consectetur, corrupti laudantium inventore.");
            gameGenres.add("Action, FPS");
            gameRatings.add(4.5f);
            gameStocks.add(36);
            gamePrices.add(550000);
            gameImages.add(R.drawable.game5);

            gameNames.add("Assassins Creed Rogue");
            gameDescs.add("Lorem ipsum dolor sit amet consectetur adipisicing elit. Inventore, tempore aliquam? Veritatis, vitae reiciendis consectetur, corrupti laudantium inventore.");
            gameGenres.add("Action, RPG");
            gameRatings.add(3.5f);
            gameStocks.add(41);
            gamePrices.add(620000);
            gameImages.add(R.drawable.game6);

            gameNames.add("Anthem");
            gameDescs.add("Lorem ipsum dolor sit amet consectetur adipisicing elit. Inventore, tempore aliquam? Veritatis, vitae reiciendis consectetur, corrupti laudantium inventore.");
            gameGenres.add("Action, TPS");
            gameRatings.add(5f);
            gameStocks.add(50);
            gamePrices.add(700000);
            gameImages.add(R.drawable.game7);

            gameNames.add("Civilization VI");
            gameDescs.add("Lorem ipsum dolor sit amet consectetur adipisicing elit. Inventore, tempore aliquam? Veritatis, vitae reiciendis consectetur, corrupti laudantium inventore.");
            gameGenres.add("Strategy");
            gameRatings.add(4.5f);
            gameStocks.add(89);
            gamePrices.add(320000);
            gameImages.add(R.drawable.game8);

            gameNames.add("Call of Cthulhu");
            gameDescs.add("Lorem ipsum dolor sit amet consectetur adipisicing elit. Inventore, tempore aliquam? Veritatis, vitae reiciendis consectetur, corrupti laudantium inventore.");
            gameGenres.add("Thriller");
            gameRatings.add(5f);
            gameStocks.add(43);
            gamePrices.add(620000);
            gameImages.add(R.drawable.game9);

            gameNames.add("Star Wars Battlefront II");
            gameDescs.add("Lorem ipsum dolor sit amet consectetur adipisicing elit. Inventore, tempore aliquam? Veritatis, vitae reiciendis consectetur, corrupti laudantium inventore.");
            gameGenres.add("Action, FPS");
            gameRatings.add(3.5f);
            gameStocks.add(54);
            gamePrices.add(280000);
            gameImages.add(R.drawable.game10);

            for (int i = 0; i < gameNames.size(); i++) {
                gamesList.add(new Games(i+1, gameNames.get(i), gameDescs.get(i), gameGenres.get(i), gameRatings.get(i), gameStocks.get(i), gamePrices.get(i), gameImages.get(i), null));
            }

            json = gson.toJson(gamesList);

//            Log.i("Game Init : ",json);
            prefEditor.putString("gamesSP", json);
            prefEditor.apply();
        }

        gamesAdapter = new GamesAdapter(getApplicationContext(), gamesList);

        gamesView = findViewById(R.id.gamesView);
        gamesView.setAdapter(gamesAdapter);
        gamesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                json = gson.toJson(gamesList.get(i));
                prefEditor.putString("currGameSP", json);
                prefEditor.apply();

                Intent intent = new Intent(GamesActivity.this, GameDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_us) {
            intent = new Intent(GamesActivity.this, AboutActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.navHome:
                intent = new Intent(GamesActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.navGames:
                break;
            case R.id.navLogout:
                prefEditor.remove("gamesSP");
                prefEditor.remove("currGameSP");
                prefEditor.remove("loggedInUser");
                prefEditor.apply();
                intent = new Intent(GamesActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

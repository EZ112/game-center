package com.example.gamecenter;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.gamecenter.Adapter.GamesAdapter;
import com.example.gamecenter.Model.Games;
import com.example.gamecenter.Model.MyGames;
import com.example.gamecenter.Model.Users;
import com.example.gamecenter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GamesActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener{

    DatabaseHelper gameCenterDB;
    ListView gamesView;
    GamesAdapter gamesAdapter;
    ArrayList<Games> gamesList = new ArrayList<>();
    ArrayList<Integer> gameImages = new ArrayList<>();
    Intent intent;

    Users user;
    static Games game;
    ArrayList<MyGames> myGamesList = new ArrayList<>();

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

        user = HomeActivity.user;
        myGamesList = HomeActivity.myGamesList;

        View headerView = navigationView.getHeaderView(0);
        TextView navUser = headerView.findViewById(R.id.navUser);
        TextView navEmail = headerView.findViewById(R.id.navEmail);

        navUser.setText(user.getUserName());
        navEmail.setText(user.getUserEmail());

        gameCenterDB = new DatabaseHelper(this);
        gamesList = gameCenterDB.viewGame();
        gamesView = findViewById(R.id.gamesView);

        if(gamesList.isEmpty()){
            gameImages.add(R.drawable.game1);
            gameImages.add(R.drawable.game2);
            gameImages.add(R.drawable.game3);
            gameImages.add(R.drawable.game4);
            gameImages.add(R.drawable.game5);
            gameImages.add(R.drawable.game6);
            gameImages.add(R.drawable.game7);
            gameImages.add(R.drawable.game8);
            gameImages.add(R.drawable.game9);
            gameImages.add(R.drawable.game10);

            RequestQueue reqQue = Volley.newRequestQueue(this);
            final String jsonUrl = "https://api.myjson.com/bins/15cfg8";

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, jsonUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String name = jsonObject.getString("name");
                            long price = jsonObject.getLong("price");
                            int stock = jsonObject.getInt("stock");
                            double rating = jsonObject.getDouble("rating");
                            String genre = jsonObject.getString("genre");
                            String desc = jsonObject.getString("description");

                            gameCenterDB.addGame(id, name, price, stock, rating, genre, desc, gameImages.get(i));
                            gamesList = gameCenterDB.viewGame();
                            gamesAdapter = new GamesAdapter(getApplicationContext(), gamesList);
                            gamesView.setAdapter(gamesAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            reqQue.add(jsonArrayRequest);

        }
        else{
            gamesAdapter = new GamesAdapter(getApplicationContext(), gamesList);
            gamesView.setAdapter(gamesAdapter);
        }

        gamesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                boolean isPurchased = false;

                for(MyGames myGame : myGamesList){
                    if(myGame.getGameID().equals(gamesList.get(i).getGameID())){
                        isPurchased = true;
                    }
                }
                if(isPurchased){
                    Toast.makeText(getApplicationContext(), "This game is already been purchased", Toast.LENGTH_LONG).show();
                }
                else{
                    game = gamesList.get(i);
                    Intent intent = new Intent(GamesActivity.this, GameDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
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

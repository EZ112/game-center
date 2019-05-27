package com.example.gamecenterasg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamecenterasg.Adapter.MyGamesAdapter;
import com.example.gamecenterasg.Model.Games;
import com.example.gamecenterasg.Model.MyGames;
import com.example.gamecenterasg.Model.Users;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    TextView profileHead, username, email, phone, myGamesHead, myGamesEmpty;
    MyGamesAdapter myGamesAdapter;
    ListView myGamesView;
    Intent intent;

    Users user;

    ArrayList<MyGames> allMyGamesList = new ArrayList<>();
    ArrayList<MyGames> myGamesList = new ArrayList<>();

    SharedPreferences appSP;
    SharedPreferences.Editor prefEditor;
    Gson gson = new Gson();
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Light.ttf");
        Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Thin.ttf");

        SpannableString s = new SpannableString(getString(R.string.app_name));
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

        json = appSP.getString("myGameListSP","");
        if(appSP.contains("myGameListSP"))
            allMyGamesList = gson.fromJson(json, new TypeToken<ArrayList<MyGames>>(){}.getType());

        View headerView = navigationView.getHeaderView(0);
        TextView navUser = headerView.findViewById(R.id.navUser);
        TextView navEmail = headerView.findViewById(R.id.navEmail);

        navUser.setText(user.getUserName());
        navEmail.setText(user.getUserEmail());

        profileHead = findViewById(R.id.profileHead);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        myGamesHead = findViewById(R.id.myGamesHead);
        myGamesEmpty = findViewById(R.id.myGamesEmpty);
        myGamesView = findViewById(R.id.myGamesView);

        profileHead.setTypeface(robotoThin);
        email.setTypeface(robotoLight);
        phone.setTypeface(robotoLight);


        username.setText(user.getUserName());
        email.setText(user.getUserEmail());
        phone.setText(user.getUserPhone());

        myGamesHead.setTypeface(robotoThin);

        if(!allMyGamesList.isEmpty()){

            for(MyGames myGames : allMyGamesList){
                if(myGames.getUsers().getUserID().equals(user.getUserID()))
                    myGamesList.add(myGames);
            }

            if(!myGamesList.isEmpty()){
                myGamesEmpty.setVisibility(View.GONE);
                myGamesView.setVisibility(View.VISIBLE);

                myGamesAdapter = new MyGamesAdapter(getApplicationContext(), myGamesList);

                myGamesView = findViewById(R.id.myGamesView);
                myGamesView.setAdapter(myGamesAdapter);

                myGamesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(HomeActivity.this, myGamesList.get(i).getGames().getGameName(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }


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
            intent = new Intent(HomeActivity.this, AboutActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.navHome:
                break;
            case R.id.navGames:
                intent = new Intent(HomeActivity.this, GamesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.navLogout:
                prefEditor.remove("gamesSP");
                prefEditor.remove("currGameSP");
                prefEditor.remove("loggedInUser");
                prefEditor.apply();
                intent = new Intent(HomeActivity.this, LoginActivity.class);
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

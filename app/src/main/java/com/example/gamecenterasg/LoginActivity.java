package com.example.gamecenterasg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamecenterasg.Model.Users;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView appName, emailIn, passIn, loginBtn, regText, regLink;

    ArrayList<Users> users = new ArrayList<>();

    SharedPreferences appSP;
    SharedPreferences.Editor prefEditor;
    Gson gson = new Gson();
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.LoginTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appSP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefEditor = appSP.edit();
        json = appSP.getString("usersSP","");
        users = gson.fromJson(json, new TypeToken<ArrayList<Users>>(){}.getType());

        Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Light.ttf");
        Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Thin.ttf");

        appName = findViewById(R.id.appName);
        emailIn = findViewById(R.id.emailIn);
        passIn = findViewById(R.id.passIn);
        loginBtn = findViewById(R.id.loginBtn);
        regText = findViewById(R.id.regText);
        regLink = findViewById(R.id.regLink);

        appName.setTypeface(robotoThin);
        emailIn.setTypeface(robotoLight);
        passIn.setTypeface(robotoLight);
        loginBtn.setTypeface(robotoLight);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty())
                    Toast.makeText(LoginActivity.this, "Username and Password Cannot be Empty", Toast.LENGTH_LONG).show();
                else{
                    if(validation()!=null){
                        Users user = validation();

                        json = gson.toJson(user);
                        prefEditor.putString("loggedInUser", json);
                        prefEditor.apply();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else
                        Toast.makeText(LoginActivity.this, "Wrong Username or Password", Toast.LENGTH_LONG).show();
                }
            }
        });

        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private boolean isEmpty(){
        if(emailIn.getText().toString().equals("") || passIn.getText().toString().equals(""))
            return true;
        else
            return false;
    }

    private Users validation(){
        if(users != null){
            for(Users user : users){
                String emailInStr = emailIn.getText().toString();
                String passInStr = passIn.getText().toString();
                if(user.getUserEmail().equals(emailInStr) && user.getUserPassword().equals(passInStr)){
                    return user;
                }
            }
        }
        return null;
    }
}

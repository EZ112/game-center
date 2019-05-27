package com.example.gamecenterasg;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView aboutHead, companyLocationHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Thin.ttf");

        SpannableString s = new SpannableString(getString(R.string.app_name));
        s.setSpan(new TypefaceSpan(this,"Roboto-Thin"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);

        aboutHead = findViewById(R.id.aboutHead);
        companyLocationHead = findViewById(R.id.companyLocationHead);

        aboutHead.setTypeface(robotoThin);
        companyLocationHead.setTypeface(robotoThin);

    }
}

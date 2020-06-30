package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView textview;
    String suspect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        suspect = getIntent().getStringExtra("suspect");
        textview = findViewById(R.id.result);

        if(suspect.equals("Oui"))
        {
            String res ="Votre situation peut relever d'un COVID 19 .\nVos symptomes nécessitent une prise en charge rapide !";
            SpannableString content = new SpannableString(res);
            content.setSpan(new UnderlineSpan(),45,res.length(),0);
            textview.setText(content);
        }

        else
        {
            textview.setText("Votre état ne semble pas préopcupant ou ne relève probablement pas du COVID 19 .");
        }

    }

    public void openHome(View view) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
}

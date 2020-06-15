package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    public static TextView death;
    public static TextView recovered;
    public static TextView active;
    public static TextView confirmed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        death=findViewById(R.id.death);
        recovered=findViewById(R.id.cured);
        active=findViewById(R.id.active);
        confirmed=findViewById(R.id.confirmed);

        FetchData process=new FetchData();
        process.execute();
    }

    public void OpenMap(View view) {
    }

    public void OpenForm(View view) {
        Intent intent = new Intent(getApplicationContext(), FormulaireActivity.class);
        startActivity(intent);
    }
}

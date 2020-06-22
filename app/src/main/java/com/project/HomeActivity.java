package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.project.models.Notification;
import com.project.sync.ReminderUtilities;
import com.project.util.Util;

public class HomeActivity extends AppCompatActivity {
    public static TextView death;
    public static TextView recovered;
    public static TextView active;
    public static TextView confirmed;
    private static final String REMINDER_JOB_TAG = "scan_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        death=findViewById(R.id.death);
        recovered=findViewById(R.id.cured);
        active=findViewById(R.id.active);
        confirmed=findViewById(R.id.confirmed);
        ReminderUtilities.scheduleReminder(this, REMINDER_JOB_TAG);

        FetchData process=new FetchData();
        process.execute();


    }

    public void OpenMap(View view) {

        LatLng latLng=new LatLng(33.453427,-7.6477861);
        Util.openMap(getApplicationContext(),latLng,1590344742907L,1590344917062L);
    }

    public void OpenForm(View view) {
        Intent intent = new Intent(getApplicationContext(), FormulaireActivity.class);
        startActivity(intent);
    }

    public void OpenNotif(View view) {
        Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
        startActivity(intent);
    }
}

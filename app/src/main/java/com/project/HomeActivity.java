package com.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.models.Notification;
import com.project.sync.ReminderUtilities;
import com.project.util.Util;

public class HomeActivity extends AppCompatActivity {
    public static TextView death;
    public static TextView recovered;
    public static TextView active;
    public static TextView confirmed;
    private static final String REMINDER_JOB_TAG = "scan_tag";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        death=findViewById(R.id.death);
        recovered=findViewById(R.id.cured);
        active=findViewById(R.id.active);
        confirmed=findViewById(R.id.confirmed);

        getInfected();

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

    public void getInfected(){
        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        db.collection("ContactedPersons")
                .document(Util.getMacAddr()).collection("Contacts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("pezones", document.getId() + " => " + document.getData());
                                long last=Long.parseLong(document.get("lastAppearance").toString());
                                long first=Long.parseLong(document.get("firstAppearance").toString());
                                long difference=(last-first)/60;
                                if(difference>=10) {
                                    String macAddress = document.get("macAddress").toString();
                                    Log.d("pezones", " " + difference);
                                    db.collection("notifications").document(macAddress)
                                            .update("notified", true);
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    db.collection("notifications").document(macAddress)
                                            .update("infectors", FieldValue.arrayUnion(user.getUid()));
                                }
                            }
                        } else {
                            Log.d("pezones", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}

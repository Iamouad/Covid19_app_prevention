package com.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.repositories.UserRepository;
import com.project.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotificationActivity extends AppCompatActivity {

    ArrayList<String> title,body,ids;
    ArrayList<LatLng> cords;
    ArrayList<Long> firstAppears;
    int images = R.drawable.google_map;
    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG="notif";
    ProgressBar progressBar;
    final String MyMacAdress = "MAC1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.loadingNotif);
        title = new ArrayList<>();
        body = new ArrayList<>();
        ids = new ArrayList<>();
        cords = new ArrayList<>();
        firstAppears = new ArrayList<>();
        //reset notification field in database
        new UserRepository().turnOffNotification();

        // MyMacAdress= Util.getMacAddr();


        db.collection("notifications")
                .document(MyMacAdress)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(NotificationActivity.this, "Nice", Toast.LENGTH_SHORT).show();

                           // title.add(task.getResult().get("title").toString());
                            //body.add(task.getResult().get("body").toString());
                            ids = (ArrayList<String>) task.getResult().get("infectors");

                            Toast.makeText(NotificationActivity.this, ids.toString(), Toast.LENGTH_SHORT).show();
                            db.collection("ContactedPersons")
                                    .document(MyMacAdress)
                                    .collection("Contacts")
                                    .whereIn("macAddress", ids)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {

                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    double lat = (double) document.get("latitude");
                                                    double longt = (double) document.get("longitude");
                                                    LatLng latLng=new LatLng(lat,longt);
                                                    cords.add(latLng);
                                                  //   Date expiry = new Date(Long.parseLong(document.get("firstAppearance").toString()));
                                                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                                    cal.setTimeInMillis(Long.parseLong(document.get("firstAppearance").toString()) * 1L);
                                                    String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
                                                    body.add(date);

                                                    firstAppears.add(Long.parseLong(document.get("firstAppearance").toString()));
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    MyAdapter myAdapter = new MyAdapter(getApplicationContext(),title,body,ids,images,cords,firstAppears);
                                                    recyclerView.setAdapter(myAdapter);
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                                    }
                                            } else {
                                                Log.d("pezones", "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });


                        } else {
                            Toast.makeText(NotificationActivity.this, "Shit", Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

       /* MyAdapter myAdapter = new MyAdapter(this,title,body,images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/
    }

    public void openTest(View view) {
        Intent intent = new Intent(getApplicationContext(), FormulaireActivity.class);
        startActivity(intent);
    }
}

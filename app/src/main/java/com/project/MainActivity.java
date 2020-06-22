package com.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.project.models.User;
import com.project.repositories.UserRepository;
import com.project.sync.ReminderUtilities;

public class MainActivity extends AppCompatActivity {
    private UserRepository userRepository;
    private static final String REMINDER_JOB_TAG = "scan_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ReminderUtilities.scheduleReminder(this, REMINDER_JOB_TAG);
        //NotificationReminder.scheduleReminder(this, NOTIFIER_JOB_TAG);
        Toast.makeText(this, "made it to Main", Toast.LENGTH_SHORT).show();


    }

}

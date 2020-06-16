package com.project;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.repositories.QuestionRepository;
import com.project.services.LoginService;
import com.project.services.SubscribeService;
import com.project.sync.ReminderUtilities;
import com.project.util.Util;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity{
    private EditText mail;
    private EditText password;
    private Button signUp;
    private Button logIn;
    private FirebaseAuth mAuth;
    private ProgressBar loading;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //Util.updateUi(this, user);
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        logIn = findViewById(R.id.login);
        signUp = findViewById(R.id.signIn);
        loading = findViewById(R.id.loading);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(mail.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                String TAG = "onComplete";
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Util.updateUi(LoginActivity.this, user);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                                loading.setVisibility(View.INVISIBLE);

                                // ...
                            }
                        });
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        getInfected();


    }

    public void getInfected(){
        db=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        db.collection("ContactedPersons")
                .document("40:D3:AE:2D:20:8:").collection("Contacts").get()
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



}

package com.project;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.models.User;
import com.project.services.UserService;
import com.project.util.Util;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    EditText mail;
    EditText password;
    EditText passwordConfirmation;
    Button join;
    private FirebaseAuth mAuth;
    private ProgressBar loading;
    private FirebaseFirestore db;
    private UserService mUserService;
    private User newUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mail = findViewById(R.id.mail2);
        password = findViewById(R.id.password2);
        join = findViewById(R.id.buttonJoin);
        loading = findViewById(R.id.progressBarSavingUser);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        join.setOnClickListener(this);
    }

    public void addUser(User user, String id) {
        db = FirebaseFirestore.getInstance();
        DocumentReference newUserRef = db.collection("users")
                .document(id);
        newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    System.out.println("succeed =======");
                }
                else {
                    System.out.println("failed==========");
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        System.out.println(mail.getText().toString());
        newUser = new User(mail.getText().toString(), password.getText().toString(), null);
        loading.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(mail.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String TAG = "onCompleteSubscribe";
                        loading.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            try {
                                addUser(newUser, user.getUid());
                            }catch (Exception e){
                                System.out.println("cannot perform that");
                            }
                            Util.updateUi(SignInActivity.this, user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }


}

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.util.Util;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mail;
    EditText password;
    EditText passwordConfirmation;
    Button join;
    private FirebaseAuth mAuth;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        join = findViewById(R.id.buttonJoin);
        loading = findViewById(R.id.progressBarSavingUser);
        mAuth = FirebaseAuth.getInstance();
        join.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        System.out.println(mail.getText().toString());
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

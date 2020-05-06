package com.project.util;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseUser;
import com.project.MainActivity;

public class Util {
    public static void updateUi(AppCompatActivity context, FirebaseUser user){
        if(user != null){
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }
}

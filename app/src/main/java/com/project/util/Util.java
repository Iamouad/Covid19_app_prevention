package com.project.util;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.FormulaireActivity;
import com.project.HomeActivity;
import com.project.MainActivity;

public class Util {
    private static FirebaseFirestore mFirestore;
    private boolean test=false;
    public static void updateUi(final AppCompatActivity context, FirebaseUser user){
        /*if(user != null){
            Intent intent = new Intent(context, FormulaireActivity.class);
            context.startActivity(intent);
        }*/
        if(user != null){
            mFirestore= FirebaseFirestore.getInstance();
            mFirestore.collection("users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    boolean tested= (boolean) documentSnapshot.get("tested");
                    if(tested){
                        Log.d("testFirebase","lol");
                        Intent intent = new Intent(context, HomeActivity.class);
                        context.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(context, FormulaireActivity.class);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}

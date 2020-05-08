package com.project.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.models.User;
import com.project.services.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UserService {
    @Override
    public void addUser(User user) {

    }


    @Override
    public User getCurrentUser(FirebaseAuth auth, FirebaseFirestore db) {
        DocumentReference docRef = db.collection("users").document(auth.getUid());
        System.out.println(docRef);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    User user = task.getResult().toObject(User.class);
                    System.out.println(user);
                }
                else {
                    System.out.println("couldnt perform thet operation");
                }
            }
        });
        System.out.println("couldnt perform thet operation task uncomplete");
        return null;
    }

    @Override
    public List<User> getAllUsers(FirebaseFirestore db) {
        final List<User> users = new ArrayList<>();
        CollectionReference collectionReference = db.collection("users");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (DocumentSnapshot doc:task.getResult()) {
                        users.add(doc.toObject(User.class));
                        System.out.println(doc.toObject(User.class));
                    }
                }
                else {
                    System.out.println("Unsuccessful query to retrieve all users");
                }
            }
        });
        return users;
    }

    @Override
    public void ListenToUser(String Userid, FirebaseFirestore db) {
        DocumentReference ref = db.collection("users").document(Userid);
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                String TAG = "Listening.......";
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }
}

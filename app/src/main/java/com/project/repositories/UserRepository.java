package com.project.repositories;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.project.gps.GpsPermissionChecker;
import com.project.gps.MyActualLocation;
import com.project.models.DeviceAppearance;
import com.project.models.User;
import com.project.services.UserService;
import com.project.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UserService {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    MyActualLocation myActualLocation;
    public UserRepository() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void addUser(User user) {

    }


    @Override
    public User getCurrentUser(FirebaseAuth auth) {
        DocumentReference docRef = db.collection("users").document(auth.getUid());
        System.out.println(docRef);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    User user = task.getResult().toObject(User.class);
                    System.out.println(user);
                } else {
                    System.out.println("could not perform that operation");
                }
            }
        });
        System.out.println("could not perform that operation task uncompleted");
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        final List<User> users = new ArrayList<>();
        CollectionReference collectionReference = db.collection("users");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        users.add(doc.toObject(User.class));
                        System.out.println(doc.toObject(User.class));
                    }
                } else {
                    System.out.println("Unsuccessful query to retrieve all users");
                }
            }
        });
        return users;
    }

    @Override
    public void ListenToUser(String Userid) {
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

    @Override
    public boolean isUsingTheApp(String macAddr) {
        final Boolean[] exist = {false};
        CollectionReference collectionReference = db.collection("users");
        Query query = collectionReference.whereEqualTo("macAddress", macAddr);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {
                        System.out.println("the member is using the app");
                        exist[0] = true;
                    }
                }
            }
        });
        return exist[0];
    }

    @Override
    public void addContactedDevice(DeviceAppearance dev) {
        /////////////////check if user using the app...
        System.out.println("getting the Last location.......");
                ///////////
            try {

                db.collection("ContactedPersons").document(Util.getMacAddr()).collection("Contacts").document().set(dev).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println("a new contacted person added to database");
                        } else {
                            System.out.println(" cannot add a contacted person to database");
                        }
                    }
                });
            }catch (Exception ex){
                System.out.println("exception ===============> adding contactedPersons");
                System.out.println(ex.getMessage());
            }
            }


        }





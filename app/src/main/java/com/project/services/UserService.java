package com.project.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.models.User;

import java.util.List;

public interface UserService {
    public void addUser(User user);

    public User getCurrentUser(FirebaseAuth auth, FirebaseFirestore db);

    public List<User> getAllUsers(FirebaseFirestore db);

    public void ListenToUser(String Userid, FirebaseFirestore db);
}

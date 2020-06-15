package com.project.services;

import android.app.Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.models.DeviceAppearance;
import com.project.models.User;
import java.util.List;

public interface UserService {

    public void addUser(User user);

    public User getCurrentUser(FirebaseAuth auth);

    public List<User> getAllUsers();

    public void ListenToUser(String Userid);

    public boolean isUsingTheApp(String macAddr);

    public void addContactedDevice(DeviceAppearance dev);
}

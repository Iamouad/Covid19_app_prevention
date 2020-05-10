package com.project.repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.services.QuestionService;

import java.util.ArrayList;

public class QuestionRepository implements QuestionService {
    @Override
    public ArrayList<String> listQuestions() {
        Log.d("testQuestion","Cabellooo");
        final ArrayList<String> questions = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Questions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                questions.add(document.get("Question").toString());

                            }
                        } else {
                            Log.d("testQuestion", "Error getting documents.", task.getException());
                        }
                    }
                });


        return questions;
    }
}

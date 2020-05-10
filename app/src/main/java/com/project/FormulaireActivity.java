package com.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.repositories.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

public class FormulaireActivity extends AppCompatActivity {

    TextView text,next,previous,questionNumber,terminer,textViewSeek,celsus;
    ImageView ic_previous,ic_next;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ProgressBar progressBar;
    SeekBar seekBar;

    int counter = 0;

    ArrayList<String> ListChecked;
    ArrayList<String> questions;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    public void displayButtons(int counter)
    {
        if(counter==0)
        {
            previous.setVisibility(View.INVISIBLE);
            ic_previous.setVisibility(View.INVISIBLE);
            radioGroup.setVisibility(View.INVISIBLE);
            seekBar.setVisibility(View.VISIBLE);
            textViewSeek.setVisibility(View.VISIBLE);
            celsus.setVisibility(View.VISIBLE);
        }

        else if(counter == questions.size()-1)
        {
            next.setVisibility(View.INVISIBLE);
            ic_next.setVisibility(View.INVISIBLE);

        }

        else
        {
            next.setVisibility(View.VISIBLE);
            previous.setVisibility(View.VISIBLE);
            ic_previous.setVisibility(View.VISIBLE);
            ic_next.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
            seekBar.setVisibility(View.INVISIBLE);
            textViewSeek.setVisibility(View.INVISIBLE);
            celsus.setVisibility(View.INVISIBLE);
        }
    }

    public void setPreviousChecked(int counter)
    {
        String checked = ListChecked.get(counter);
        if(checked.equals("Oui"))
        {
            radioButton = findViewById(R.id.radio_oui);
            radioButton.setChecked(true);
        }

        else if(checked.equals("Non"))
        {
            radioButton = findViewById(R.id.radio_non);
            radioButton.setChecked(true);
        }

        else if (checked.equals("empty"))
        {
            radioGroup.clearCheck();
        }
    }


    public void checkButton(View v)
    {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        ListChecked.set(counter,radioButton.getText().toString());
     }

     public void updateTested(String id)
     {
         db.collection("users").document(id)
                 .update("tested",true);
     }

     public void updateSuspect(String id,boolean result)
     {
         db.collection("users").document(id)
                 .update("suspect",result);
     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);
        Toast.makeText(this, "made it to Form", Toast.LENGTH_SHORT).show();
        text = findViewById(R.id.text);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        questionNumber = findViewById(R.id.questionNumber);
        radioGroup = findViewById(R.id.radio_group);
        ic_previous = findViewById(R.id.ic_previous);
        ic_next = findViewById(R.id.ic_go_next);
        terminer = findViewById(R.id.terminer);
        progressBar = findViewById(R.id.progressBar);
        seekBar = findViewById(R.id.seekBar);
        textViewSeek = findViewById(R.id.textViewSeek);
        celsus = findViewById(R.id.celsus);

        ListChecked = new ArrayList<>();
        questions = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        db.collection("Questions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            text.setVisibility(View.VISIBLE);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                questions.add(document.get("Question").toString());
                                text.setText(questions.get(0));
                                ListChecked.add("empty");

                            }
                        } else {
                            Log.d("testQuestion", "Error getting documents.", task.getException());
                        }
                    }
                });


                previous.setVisibility(View.INVISIBLE);
                ic_previous.setVisibility(View.INVISIBLE);
                radioGroup.setVisibility(View.INVISIBLE);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter++;
                setPreviousChecked(counter);
                text.setText(questions.get(counter));
                questionNumber.setText("Question N° "+(counter+1));
                displayButtons(counter);

                // Get toast of checked
                String text =" ";
                for(int i=0;i< ListChecked.size();i++)
                {
                    text+=ListChecked.get(i)+" ";
                }

                Toast.makeText(FormulaireActivity.this, text, Toast.LENGTH_SHORT).show();

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter--;
                setPreviousChecked(counter);
                text.setText(questions.get(counter));
                questionNumber.setText("Question N° "+(counter+1));

               displayButtons(counter);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSeek.setText((progress+30)+"");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(Integer.parseInt(textViewSeek.getText().toString())>37)
                {
                    ListChecked.set(0,"Oui");
                }

                else
                {
                    ListChecked.set(0,"Non");
                }
            }
        });

        terminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTested(currentUser.getUid());
                int count=0;
                for(int i=0;i<ListChecked.size();i++)
                {
                     if(ListChecked.get(i).equals("Oui"))
                     {
                         count ++;
                     }
                }

                if(count > ListChecked.size()*2/3)
                {
                    updateSuspect(currentUser.getUid(),true);
                    Toast.makeText(FormulaireActivity.this, "You ara Dead ! "+count+" / "+ListChecked.size(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FormulaireActivity.this, ResultActivity.class);
                    intent.putExtra("suspect","Oui");
                    startActivity(intent);
                }

                else
                {
                    updateSuspect(currentUser.getUid(),false);
                    Toast.makeText(FormulaireActivity.this, "You could live another day "+count+" / "+ListChecked.size(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FormulaireActivity.this, ResultActivity.class);
                    intent.putExtra("suspect","Non");
                    startActivity(intent);
                }
            }
        });

    }



}

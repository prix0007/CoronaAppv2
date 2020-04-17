package com.example.project;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;

import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegisterActivityAuthority extends AppCompatActivity {
    public static final String TAG = "RegisterActivityVolunteerJava";
    EditText aname, aage, aemail_phone, apass,workplace;
    //    EditText aphone;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userid;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authority_register);


        Spinner spin=findViewById(R.id.spinner);
        String y = String.valueOf(spin.getSelectedItem());


        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }




    public void homeA(View view) {
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivityAuthority.this, VolunteerHome.class));
            finish();
        }
        final boolean[] flag = {false};
        aname = findViewById(R.id.editText4);
        aage = findViewById(R.id.editText);
        aemail_phone = findViewById(R.id.editText2);
        apass = findViewById(R.id.editText3);
        workplace = findViewById(R.id.editText5);
//           arphone = findViewById(R.id.)
        final String name = aname.getText().toString();
        final String age = aage.getText().toString();
        final String email = aemail_phone.getText().toString().trim();
        final String password = apass.getText().toString().trim();
        final String place;
        place = workplace.getText().toString();
        Spinner spin=findViewById(R.id.spinner);
        final String y = String.valueOf(spin.getSelectedItem());
        if (TextUtils.isEmpty(name)) {
            aname.setError("Name field is empty");
        }
        if (TextUtils.isEmpty(email)) {
            aemail_phone.setError("Email field is Empty");
        }
//        if(TextUtils.isDigitsOnly(email) || TextUtils.isEmpty(email)){
//            if(email.length() != 10 && TextUtils.isDigitsOnly(email)){
//                aemail_phone.setError("Phone no can't be greater than 10 digits");
//            }
//            if(TextUtils.isEmpty(email)){
//                aemail_phone.setError(("Email field is Empty"));
//            }
//        }
        if (TextUtils.isEmpty(password)) {
            apass.setError("Password is empty");
        }
        if (password.length() < 8) {
            apass.setError("Password in less than 8");
        }
        if (TextUtils.isEmpty(age)) {
            aage.setError("Age Field is Empty");
        }
//            if(TextUtils.isEmpty(phone)){
//                vphone.setError("Phone field is Empty");
//            }
        // Register Authority in firebase

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    System.out.println("created Authority");
                    userid = firebaseAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = db.collection("Authorities").document(userid);
                    Map<String, Object> user = new HashMap<>();
                    user.put("fullname",name);
                    user.put("age",age);
                    user.put("email_phone",email);
//                        user.put("Phone",phone)
                    user.put("role","Authority");
                    user.put("occupation",y);
                    user.put("workpalce",place);
                    db.collection("Authorities")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @SuppressLint("LongLogTag")
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @SuppressLint("LongLogTag")
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                    Toast.makeText(RegisterActivityAuthority.this, "user created Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivityAuthority.this, AuthorityHome.class));
                } else {
                    Toast.makeText(RegisterActivityAuthority.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


}






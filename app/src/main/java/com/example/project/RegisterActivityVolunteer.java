package com.example.project;

import android.content.Intent;
import android.os.Build;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;


public class RegisterActivityVolunteer extends AppCompatActivity {

    public static final String TAG = "RegActVolJava";
    EditText rname, rage, remail, rpass;
    EditText rphone;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer_register);


        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void homeV(View view) {
//        if (firebaseAuth.getCurrentUser() != null) {
//            startActivity(new Intent(RegisterActivityVolunteer.this, VolunteerHome.class));
//            finish();
//        }
        final boolean[] flag = {false};
        rname = findViewById(R.id.editText4);
        rage = findViewById(R.id.editText);
        remail = findViewById(R.id.editText2);
        rpass = findViewById(R.id.editText3);
//            rphone = findViewById(R.id.)
        final String name = rname.getText().toString();
        final String age = rage.getText().toString();
        final String email = remail.getText().toString().trim();
        final String password = rpass.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            rname.setError("Name field is empty");
        }
        if (TextUtils.isEmpty(email)) {
            remail.setError("Email field is Empty");
        }
        if (TextUtils.isEmpty(password)) {
            rpass.setError("Password is empty");
        }
        if (password.length() < 6) {
            rpass.setError("Password in less than 6");
        }
        if (TextUtils.isEmpty(age)) {
            rage.setError("Age Field is Empty");
        }
//            if(TextUtils.isEmpty(phone)){
//                rphone.setError("Phone field is Empty");
//            }
        // Register volunteer in firebase

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    System.out.println("created volunteer");
                    userid = firebaseAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = db.collection("users").document(userid);
                    Map<String, Object> user = new HashMap<>();
                    user.put("fullname",name);
                    user.put("age",age);
                    user.put("email",email);
//                        user.put("Phone",phone)
                    user.put("role","user");

                    db.collection("volunteer")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                    Toast.makeText(RegisterActivityVolunteer.this, "user created Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivityVolunteer.this, user_nav.class));
                } else {
                    Toast.makeText(RegisterActivityVolunteer.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}




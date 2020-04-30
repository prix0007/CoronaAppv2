package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends Activity {

    public static final String TAG = "RegisterActivityJava";
    EditText rname, rage, remail, rpass, workplace;
    EditText rphone;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userid;
    Button register_btn;
    Spinner role, occupation;
    ProgressBar progressBar;
    LinearLayout occupation_layout, workplace_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_v2);

        register_btn  = findViewById(R.id.register_btn);
        initializeItems();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Log.d(TAG, "Current Item is "+role.getSelectedItem());
        final String[] roles = getResources().getStringArray(R.array.roles);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, roles);
        role.setAdapter(adapter);
          role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> arg0, View arg1,
              int arg2, long arg3) {
            int index = arg0.getSelectedItemPosition();
            if(roles[index].equals("Authority")){
                viewOccupationView();
            } else {
                hideOccupationView();
            }
          }

          @Override
          public void onNothingSelected(AdapterView<?> arg0) {
          }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = rname.getText().toString();
                final String age = rage.getText().toString();
                final String email = remail.getText().toString().trim();
                final String password = rpass.getText().toString().trim();
                final String srole = role.getSelectedItem().toString();
                String soccu = null;
                String sworkPlace = null;
                if(srole.equals("Authority")){
                    soccu = occupation.getSelectedItem().toString();
                    sworkPlace = workplace.getText().toString();
                    if(!checkOccupationWorkPlace(soccu, sworkPlace)){
                        Toast.makeText(getApplicationContext(), "Please Provide further details", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                progressBar.setVisibility(View.VISIBLE);
                register_btn.setClickable(false);
                if(checkErrors(name, age, email, password)){
                    final String finalSoccu = soccu;
                    final String finalSworkPlace = sworkPlace;
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            userid = firebaseAuth.getCurrentUser().getUid();
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("fullname",name);
                                            user.put("age",age);
                                            user.put("email",email);
                                            user.put("role",srole);
                                            if(srole.equals("Authority")){
                                                user.put("occupation", finalSoccu);
                                                user.put("workplace", finalSworkPlace);
                                            }
                                            final DocumentReference documentReference = firebaseFirestore.collection("users").document(userid);
                                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    progressBar.setVisibility(View.GONE);
                                                    register_btn.setClickable(true);
                                                    Log.d("SUCCESS","SuccessFully Registered for " + userid);
                                                    Toast.makeText(getApplicationContext(), "SuccessFully Registered .Please Verify your email for verification.", Toast.LENGTH_LONG).show();
                                                    resetFields();
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressBar.setVisibility(View.GONE);
                                                    register_btn.setClickable(true);
                                                    Log.d("FAILURE","Something Occured and Error Happened");
                                                    Toast.makeText(getApplicationContext(), "Some Error Occured", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    }
                                });
                            } else {
                                progressBar.setVisibility(View.GONE);
                                register_btn.setClickable(true);
                                Toast.makeText(RegisterActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Enter all details for field.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

        private void initializeItems(){
            rname = findViewById(R.id.name);
            rage = findViewById(R.id.age);
            remail = findViewById(R.id.email);
            rpass = findViewById(R.id.password);
            workplace = findViewById(R.id.workplace);
            occupation = findViewById(R.id.occupation);
            role = findViewById(R.id.roles);
            progressBar = findViewById(R.id.progressBarRegister);
            occupation_layout = findViewById(R.id.occupation_layout);
            workplace_layout = findViewById(R.id.workplace_layout);
        }


        //Use this function only after initializing initializeItems()
        private boolean checkErrors(String name , String email, String password, String age){
            if (TextUtils.isEmpty(name)) {
                rname.setError("Name field is empty");
                return false;
            }
            if (TextUtils.isEmpty(email)) {
                remail.setError("Email field is Empty");
                return false;
            }
            if (TextUtils.isEmpty(password)) {
                rpass.setError("Password is empty");
                return false;
            }
            if (password.length() < 6) {
                rpass.setError("Password in less than 6");
                return false;
            }
            if (TextUtils.isEmpty(age)) {
                rage.setError("Age Field is Empty");
                return false;
            }
            if(Integer.parseInt(age) < 9){
                rage.setError("You must be 9 Years Old");
                return false;
            }
            return true;
        }
        private void viewOccupationView(){
            occupation_layout.setVisibility(View.VISIBLE);
            workplace_layout.setVisibility(View.VISIBLE);
        }

        private void hideOccupationView(){
            occupation_layout.setVisibility(View.GONE);
            workplace_layout.setVisibility(View.GONE);
        }

        private boolean checkOccupationWorkPlace(String soccu, String sworkplace){
            if(soccu.equals("Select")){
                return false;
            }
            if(TextUtils.isEmpty(sworkplace)){
                return false;
            }
            return true;
        }
        private void resetFields(){
            rname.setText("");
            rage.setText("");
            remail.setText("");
            rpass.setText("");
            role.setSelection(0);
            occupation.setSelection(0);
            workplace.setText("");
        }
}




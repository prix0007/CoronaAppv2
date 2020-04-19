package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText memail,mpassword;
    TextView forgot_pwd, register_page;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ImageView login_btn;
    ProgressBar progressBar;
    final String TAG = "Login Activity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.login );

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        initializeItems();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn.setClickable(false);
                progressBar.setVisibility(View.VISIBLE);

                String userName = memail.getText().toString();
                String password = mpassword.getText().toString();

                if(!(checkEmail(userName) && checkPassword(password))){
                    progressBar.setVisibility(View.GONE);
                    login_btn.setClickable(true);
                    Toast.makeText(getApplicationContext(), "Check your Credentials", Toast.LENGTH_LONG).show();
                    return;
                }


                firebaseAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        login_btn.setClickable(true);
                        if(task.isSuccessful()){
                            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                      @Override
                                      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                          String role = null;
                                          if (task.isSuccessful()) {
                                              DocumentSnapshot document = task.getResult();
                                              if (document.exists()) {
                                                  Log.d(TAG, "DocumentSnapshot data: " + document.getString("role"));
                                                  role =  document.getString("role");
                                              } else {
                                                  Log.d(TAG, "No such document");
                                              }
                                          } else {
                                              Log.d(TAG, "get failed with ", task.getException());
                                          }
                                          if(role.equals("User")){
                                              Intent i = new Intent(getApplicationContext(), Userhome.class);
                                              startActivity(i);
                                          } else if(role.equals("Volunteer")){
                                              Intent i = new Intent(getApplicationContext(), HomeVolunteerv2.class);
                                              startActivity(i);
                                          } else if(role.equals("Authority")){
                                              Intent i = new Intent(getApplicationContext(), AuthorityHome.class);
                                              startActivity(i);
                                          } else{
                                              Toast.makeText(getApplicationContext(), "No Role Defined for this Account", Toast.LENGTH_LONG).show();
                                              return ;
                                          }

                                          Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                                          resetFields();
                                      }
                                  });
                            } else {
                                Toast.makeText(getApplicationContext(), "Please verify your Email for Access", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        forgot_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
            }
        });

        register_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean checkEmail(String email){
        if(TextUtils.isEmpty(email)){
            memail.setError("Email is Empty");
            return false;
        }
        return true;
    }

    private boolean checkPassword(String pwd){
        if(TextUtils.isEmpty(pwd)){
            mpassword.setError("Password is Empty");
            return false;
        }
        if(pwd.length() < 6){
            mpassword.setError("Password is too short");
            return false;
        }
        return true;
    }

    private void initializeItems(){
        memail = findViewById(R.id.editText2);
        mpassword = findViewById(R.id.editText3);
        forgot_pwd = findViewById(R.id.forgot_pwd);
        login_btn = findViewById(R.id.login_button);
        register_page = findViewById(R.id.register_page);
        progressBar = findViewById(R.id.progressBarLogin);
    }

    private void resetFields(){
        memail.setText("");
        mpassword.setText("");
    }

}

package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText memail,mpassword;
    ImageView login;
    TextView new_reg;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.login );


        firebaseAuth = FirebaseAuth.getInstance();



    }

    public void home(View view) {
//        if (firebaseAuth.getCurrentUser() != null) {
//            startActivity(new Intent(Login.this, user_nav.class));
//            finish();
//        }
        memail = findViewById(R.id.editText2);
        mpassword = findViewById(R.id.editText3);
        final ProgressBar progressBar = findViewById(R.id.progressBar2);
        boolean flag = false;

        String email = memail.getText().toString().trim();
        String password = mpassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            memail.setError("Email is Empty");
            return;
        }
        if(TextUtils.isEmpty(password)){
            mpassword.setError("Password is Empty");
            return;
        }
        if(password.length() < 6){
            mpassword.setError("Password is too short");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
//        authenticate user
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Login.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Userhome.class));
                }
                else {

                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Login.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT ).show();
                }
            }
        });

    }

    public void register(View view) {
        startActivity(new Intent(Login.this, RegisterActivity.class));
    }
    public void menu(View view) {
        startActivity(new Intent(Login.this, page2.class));
    }


}

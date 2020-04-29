package com.example.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AuthorityHome extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Button delete;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.authority_home);
        firebaseAuth  = FirebaseAuth.getInstance();
        delete = findViewById(R.id.del);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder( AuthorityHome.this);
                dialog.setTitle("Attention !!");
                dialog.setMessage("After this action your Account will be deleted from all CoWar services. Are you sure to continue?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("Delete account", "User account deleted.");
                                            Toast.makeText(getApplicationContext(),"Account Deleted SuccessFully!!",Toast.LENGTH_LONG).show();
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alert = dialog.create();
                alert.show();
            }
        });

    }
    public void vd(View view){
        startActivity(new Intent(AuthorityHome.this,Data.class));
    }
    public void mc(View view){
        startActivity(new Intent(AuthorityHome.this, Map.class));
    }
    public void co(View view){ startActivity(new Intent(AuthorityHome.this, Organizations.class));}
    public void stat(View view){ startActivity(new Intent(AuthorityHome.this, AuthStatus.class));}
    public void signout(View view){
        firebaseAuth.signOut();
        finish();
    }
}
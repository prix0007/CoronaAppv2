package com.example.project.volunteerFragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.Userhome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Profile extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    TextView username;
    Button signout, delete;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_volunteer_profile, container);

        username = v.findViewById(R.id.volunteerName);
        signout = v.findViewById(R.id.volunteerSignout);
        delete = v.findViewById(R.id.deleteVolunteer);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    username.setText("Hello "+task.getResult().getString("fullname"));
                }
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                getActivity().finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
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
                                              Toast.makeText(getActivity(),"Account Deleted SuccessFully!!",Toast.LENGTH_LONG).show();
                                              getActivity().finish();
                                          } else {
                                              Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
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
          }
        );
        return v;
    }
}

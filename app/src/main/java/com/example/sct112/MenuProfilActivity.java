package com.example.sct112;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuProfilActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * initialization to display the registered name and email */
    private FirebaseUser user;
    private DatabaseReference reference;
    private UserProfileChangeRequest profileUpdates;
    private String userId;

    private static final String TAG = "ProfilActivity";

    private EditText namaMenuProfil, noHpProfil, emailMenuProfil;
    private Button cancelProfil, submitProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_profil);

        namaMenuProfil = (EditText) findViewById(R.id.NamaUbahProfile);
        noHpProfil = (EditText) findViewById(R.id.NoHandphone);
        emailMenuProfil = (EditText) findViewById(R.id.EmailUbahProfile);

        cancelProfil = (Button) findViewById(R.id.buttonProfileCancel);
        cancelProfil.setOnClickListener(this);
        submitProfil = (Button) findViewById(R.id.buttonProfileSubmit);
        submitProfil.setOnClickListener(this);



        /**
         * inisialization to display name*/
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();
        final TextView haiUsername = (TextView) findViewById(R.id.NamaUbahProfile);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userName = snapshot.getValue(User.class);
                if (userName != null){
                    String name = userName.NameRegist;
                    haiUsername.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenuProfilActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * inisialization to display email*/
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();
        final TextView haiUserEmail = (TextView) findViewById(R.id.EmailUbahProfile);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User email = snapshot.getValue(User.class);
                if (email != null){
                    String mail = email.EmailRegist;
                    haiUserEmail.setText(mail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenuProfilActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonProfileCancel:
                startActivity(new Intent(this, ProfilActivity.class));
                break;
            case R.id.buttonProfileSubmit:
                updateProfil();
                break;
        }
    }

    private void updateProfil() {
        String name = namaMenuProfil.getText().toString().trim();
//        String noHP = noHpProfil.getText().toString().trim();
        String email = emailMenuProfil.getText().toString().trim();

        if (name.isEmpty()){
            namaMenuProfil.setError("Nama harus di isi !");
            namaMenuProfil.requestFocus();
            return;
        }

//        if (noHP.isEmpty()){
//            noHpProfil.setError("No HP Harus di isi");
//            noHpProfil.requestFocus();
//            return;
//        }else if (noHP.length()<14){
//            noHpProfil.setError("No HP tidak boleh lebih dari 14 digit");
//            noHpProfil.requestFocus();
//            return;
//        }

        if (email.isEmpty()){
            emailMenuProfil.setError("Email harus di isi !");
            emailMenuProfil.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailMenuProfil.setError("Email tidak valid !");
            emailMenuProfil.requestFocus();
            return;
        }

        user = FirebaseAuth.getInstance().getCurrentUser();

        profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Users")
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "User Profile Update");
                        }else{
                            Log.d(TAG, "User Profile Update GAGAL");
                        }
                    }
                });

        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "User Email Update");
                        }
                    }
                });
    }

    public void SwipeHome(View view) {
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
    }

    public void SwipeSubmit(View view) {
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
    }
}
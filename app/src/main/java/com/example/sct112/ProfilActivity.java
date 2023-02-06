package com.example.sct112;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfilActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Button btnDelete, btnHome;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        btnHome = (Button) findViewById(R.id.buttonBackHome);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDelete();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilActivity.this, LoginActivity.class));
            }
        });
    }

    private boolean btnDelete() {
        // firebase auth delete
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "Delete Akun Sukses");
                        }else{
                            Log.d(TAG, "Delete Akun GAGAL");
                        }
                    }
                });

        // firebase realtime delete
        DatabaseReference deleteReference = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        deleteReference.removeValue();
        Toast.makeText(ProfilActivity.this, "Delete Akun", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        return true;
    }

    public void SwipeProfile(View view) {
        Intent intent = new Intent(this, MenuProfilActivity.class);
        startActivity(intent);
    }

    public void SwipeHome(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    public void SwipePassword(View view) {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }
}
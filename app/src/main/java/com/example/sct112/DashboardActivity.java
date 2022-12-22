package com.example.sct112;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    private ImageButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        logout = (ImageButton) findViewById(R.id.imageButtonMenuDashboard4);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView haiUser = (TextView) findViewById(R.id.textViewAkunUser);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String name = userProfile.NameRegist;
                    haiUser.setText("Hai, " + name + " !");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "ada sesuatu hal salah terjadi", Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void SwipeProfile(View view) {
//        Intent intent = new Intent(this, ProfilActivity.class);
//        startActivity(intent);
//    }
//
//    public void SwipeLaporan(View view) {
//        Intent intent = new Intent(this, FormPengaduanActivity.class);
//        startActivity(intent);
//    }
//
//    public void SwipeApkDevelop(View view) {
//        Intent intent = new Intent(this, ApkDevelopActivity.class);
//        startActivity(intent);
//    }
//
//    public void SwipeLogOut(View view) {
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//    }
//
//    public void SwipeDashboard(View view) {
//        Intent intent = new Intent(this, DashboardActivity.class);
//        startActivity(intent);
//    }
//
//    public void SwipeNotif(View view) {
//        Intent intent = new Intent(this, ApkDevelopActivity.class);
//        startActivity(intent);
//    }
//
//    public void SwipeButtonPanic(View view) {
//        Intent intent = new Intent(this, ButtonPanikActivity.class);
//        startActivity(intent);
//    }
}
package com.example.sct112;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void SwipeProfile(View view) {
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
    }

    public void SwipeLaporan(View view) {
        Intent intent = new Intent(this, FormPengaduanActivity.class);
        startActivity(intent);
    }

    public void SwipeApkDevelop(View view) {
        Intent intent = new Intent(this, ApkDevelopActivity.class);
        startActivity(intent);
    }

    public void SwipeLogOut(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void SwipeDashboard(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    public void SwipeNotif(View view) {
        Intent intent = new Intent(this, ApkDevelopActivity.class);
        startActivity(intent);
    }

    public void SwipeButtonPanic(View view) {
        Intent intent = new Intent(this, ButtonPanikActivity.class);
        startActivity(intent);
    }
}
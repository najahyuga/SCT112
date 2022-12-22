package com.example.sct112;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private TextView textViewSCT1, textViewSCT2, textViewSCT3, textViewSCT4, registerUser;
    private EditText editTextNamaRegist, editTextEmailRegist, editTextPasswordRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        textViewSCT1 = (TextView) findViewById(R.id.textViewSCT1);
        textViewSCT1.setOnClickListener(this);

        textViewSCT2 = (TextView) findViewById(R.id.textViewSCT2);
        textViewSCT2.setOnClickListener(this);

        textViewSCT3 = (TextView) findViewById(R.id.textViewSCT3);
        textViewSCT3.setOnClickListener(this);

        textViewSCT4 = (TextView) findViewById(R.id.textViewSCT4);
        textViewSCT4.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextNamaRegist = (EditText) findViewById(R.id.NamaRegist);
        editTextEmailRegist = (EditText) findViewById(R.id.EmailRegist);
        editTextPasswordRegist = (EditText) findViewById(R.id.PasswordRegist);
    }

//    public void signUp(View view) {
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textViewSCT1:
            case R.id.textViewSCT2:
            case R.id.textViewSCT3:
            case R.id.textViewSCT4:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editTextEmailRegist.getText().toString().trim();
        String password = editTextPasswordRegist.getText().toString().trim();
        String name = editTextNamaRegist.getText().toString().trim();

        if (name.isEmpty()){
            editTextNamaRegist.setError("Nama harus di isi !");
            editTextNamaRegist.requestFocus();
            return;
        }

        if (email.isEmpty()){
            editTextEmailRegist.setError("Email harus di isi !");
            editTextEmailRegist.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmailRegist.setError("Email tidak valid !");
            editTextEmailRegist.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPasswordRegist.setError("Password harus di isi !");
            editTextPasswordRegist.requestFocus();
            return;
        }

        if (password.length() < 6){
            editTextPasswordRegist.setError("Password harus lebih dari 6 !");
            editTextPasswordRegist.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(name, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast toast = Toast.makeText(RegisterActivity.this, "Registrasi berhasil !", Toast.LENGTH_LONG);
                                                toast.show();
                                            }else{
                                                Toast.makeText(RegisterActivity.this, "Registrasi gagal !", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Registrasi GAGAL !", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
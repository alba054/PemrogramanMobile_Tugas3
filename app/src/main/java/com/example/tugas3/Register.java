package com.example.tugas3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private TextView loginLink;
    private ProgressBar loadRegister;
    private TextInputEditText mail, password;
    private Button registerBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        loginLink = findViewById(R.id.loginLink);
        loadRegister = findViewById(R.id.loadRegister);
        mail = findViewById(R.id.registerMail);
        password = findViewById(R.id.registerPass);
        registerBtn = findViewById(R.id.registerBtn);

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMail = mail.getText().toString();
                String strPassword = password.getText().toString();
                loadRegister.setVisibility(View.VISIBLE);
                createAccount(strMail, strPassword);
                loadRegister.setVisibility(View.GONE);
            }
        });

    }


    private void createAccount(String email, String password) {
        boolean isMailEmpty = email.trim().isEmpty();
        boolean isPasswordEmpty = password.trim().isEmpty();
        if(isMailEmpty) {
            Toast.makeText(Register.this, "email tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
        else if(isPasswordEmpty) {
            Toast.makeText(Register.this, "password tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
        else if(isMailEmpty && isPasswordEmpty) {
            Toast.makeText(Register.this, "isi form", Toast.LENGTH_LONG).show();
        }
        else {
            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                startActivity(new Intent(Register.this, MainActivity.class));

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Register.this, "authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            // [END create_user_with_email]
        }
    }

}
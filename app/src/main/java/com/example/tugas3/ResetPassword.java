package com.example.tugas3;

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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    Button resetBtn;
    TextInputEditText resetMail;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetBtn = findViewById(R.id.resetMailBtn);
        resetMail = findViewById(R.id.resetMail);
        mAuth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resetMail.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ResetPassword.this, "form empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendPasswordReset(resetMail.getText().toString().trim());
                    startActivity(new Intent(ResetPassword.this, MainActivity.class));
                }
            }
        });
    }

    public void sendPasswordReset(String emailAddress) {
        // [START send_password_reset]
        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPassword.this, "email sent", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ResetPassword.this, "authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END send_password_reset]
    }
}
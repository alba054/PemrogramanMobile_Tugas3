package com.example.tugas3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView registerLink, forgetPass;
    TextInputEditText mail, password;
    CheckBox rememberPass;
    Button loginBtn;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerLink = findViewById(R.id.registerLink);
        forgetPass = findViewById(R.id.forget);
        mail = findViewById(R.id.loginMail);
        password = findViewById(R.id.loginPass);
        rememberPass = findViewById(R.id.remembered);
        loginBtn = findViewById(R.id.loginBtn);
        mAuth = FirebaseAuth.getInstance();
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        if (saveLogin) {
            mail.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            rememberPass.setChecked(true);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strMail = mail.getText().toString();
                String strPassword = password.getText().toString();

                if (rememberPass.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", strMail);
                    loginPrefsEditor.putString("password", strPassword);
                } else {
                    loginPrefsEditor.clear();
                }
                loginPrefsEditor.commit();

                signIn(strMail, strPassword);

            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ResetPassword.class);
                startActivity(i);
            }
        });
    }

    private void signIn(String email, String password) {
        boolean isMailEmpty = email.trim().isEmpty();
        boolean isPasswordEmpty = password.trim().isEmpty();
        if(isMailEmpty) {
            Toast.makeText(MainActivity.this, "email tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
        else if(isPasswordEmpty) {
            Toast.makeText(MainActivity.this, "password tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
        else if(isMailEmpty && isPasswordEmpty) {
            Toast.makeText(MainActivity.this, "isi form", Toast.LENGTH_LONG).show();
        }
        else {
            // [START sign_in_with_email]
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                startActivity(new Intent(MainActivity.this, Home.class));

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            // [END sign_in_with_email]
        }

    }

}
package com.batost.musicPlayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private User newUser;

    TextView txtSignIn;
    TextInputEditText fullNameInput;
    TextInputEditText emailInput;
    TextInputEditText telNumberInput;
    TextInputEditText passwordInput;
    TextInputEditText confirmPasswordInput;
    MaterialButton signUpButton;
    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        users = (ArrayList<User>) getIntent().getSerializableExtra("USERS");

        txtSignIn = findViewById(R.id.txtSignIn);

        fullNameInput = findViewById(R.id.edtSignUpFullName);
        emailInput = findViewById(R.id.edtSignUpEmail);
        telNumberInput = findViewById(R.id.edtSignUpMobile);
        passwordInput = findViewById(R.id.edtSignUpPassword);
        confirmPasswordInput = findViewById(R.id.edtSignUpConfirmPassword);
        signUpButton = findViewById(R.id.btnSignUp);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if (!Objects.requireNonNull(passwordInput.getText()).toString().equals(Objects.requireNonNull(confirmPasswordInput.getText()).toString())) {
                    Toast.makeText(SignUpActivity.this, "Given Passwords Did Not Match. Please Try Again", Toast.LENGTH_LONG).show();
                    return;
                }

                for (User u : users) {
                    if (Objects.nonNull(u) && u.getEmail().equals(Objects.requireNonNull(emailInput.getText()).toString())) {
                        Toast.makeText(SignUpActivity.this, "Given E-mail Already in Use by Another User. Please Try Again", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                newUser = new User(
                        Objects.requireNonNull(fullNameInput.getText()).toString(),
                        Objects.requireNonNull(emailInput.getText()).toString(),
                        Objects.requireNonNull(telNumberInput.getText()).toString(),
                        Objects.requireNonNull(passwordInput.getText()).toString(),
                        Objects.requireNonNull(confirmPasswordInput.getText()).toString());

                Intent sendUserDataByEmail = new Intent(Intent.ACTION_SEND);
                sendUserDataByEmail.setType("text/html");
                sendUserDataByEmail.putExtra(Intent.EXTRA_EMAIL, new String [] {Objects.requireNonNull(emailInput.getText()).toString()});
                sendUserDataByEmail.putExtra(Intent.EXTRA_SUBJECT, "User Credentials");
                sendUserDataByEmail.putExtra(Intent.EXTRA_TEXT,
                        "Full Name : " + Objects.requireNonNull(fullNameInput.getText()).toString()
                            + "\nPassword : " + Objects.requireNonNull(passwordInput.getText()).toString());
//                startActivity(Intent.createChooser(sendUserDataByEmail, "EMAIL"));
                startActivity(sendUserDataByEmail);


                Intent successfulSingUp = new Intent(SignUpActivity.this, MainActivity.class);
                successfulSingUp.putExtra("USER", newUser);
                successfulSingUp.putExtra("FaultyInputCount", 0);
                startActivity(successfulSingUp);
                finish();
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
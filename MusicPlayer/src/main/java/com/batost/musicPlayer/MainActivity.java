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

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> users = new ArrayList<>();
    private int faultyInputCount = 0;
    private boolean successfulLogin = false;
    private User newUser;
    TextView txtSignUp;
    TextInputEditText emailInput;
    TextInputEditText passwordInput;
    MaterialButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSignUp = findViewById(R.id.txtSignUp);
        emailInput = findViewById(R.id.edtSignInEmail);
        passwordInput = findViewById(R.id.edtSignInPassword);
        signInButton = findViewById(R.id.btnSignIn);

        initUsers(users);

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                intent.putExtra("USERS", users);
                startActivity(intent);
                finish();
            }
        });


    }


    private void initUsers(ArrayList<User> users) {
        User u1 = new User("Batu Han", "lelbatuhan@gmail.com", "5433453456", "12345", "12345"); users.add(u1);
        User u2 = new User("Asli Han", "batuhan.lel@hotmail.com", "5433653456", "12345", "12345"); users.add(u2);
        User u3 = new User("Batu Ahmet", "asdasdas@gmail.com", "5433412356", "12345", "12345"); users.add(u3);
        User u4 = new User("Sercan Harputlu", "asdaf@gmail.com", "5433453478", "12345", "12345"); users.add(u4);
        User u5 = new User("Seren yilmaz", "asfasf@gmail.com", "5433453496", "12345", "12345"); users.add(u5);
        User u6 = new User("Selen Yilmaz", "asfasasddas@gmail.com", "5433456656", "12345", "12345"); users.add(u6);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();

        newUser = (User) getIntent().getSerializableExtra("USER");
        faultyInputCount = (Integer) getIntent().getIntExtra("FaultyInputCount", 0);
        if (Objects.nonNull(newUser))
            users.add(newUser);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loggedEmail = Objects.requireNonNull(emailInput.getText()).toString();
                String loggedPassword = Objects.requireNonNull(passwordInput.getText()).toString();

                for (User u : users ) {
                    if (Objects.nonNull(u) && u.getEmail().equals(loggedEmail) && u.getPassword().equals(loggedPassword)) {
                        successfulLogin = true;
                        Intent successfulLogin = new Intent(MainActivity.this, SongListActivity.class);
                        startActivity(successfulLogin);
                        finish();
                    }
                }
                faultyInputCount++;
                if (faultyInputCount == 3) {
                    Toast.makeText(MainActivity.this, "3 Faulty Login Attempt... You are Redirecting to SingUp Page ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                    intent.putExtra("USERS", users);
                    startActivity(intent);
                    finish();
                } else if (successfulLogin) {
                    Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Given E-mail and Password Did Not Match. Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
package com.example.loginandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private ImageView imageView;
    private TextView textViewRegister;
    private Button  buttonPhoneLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        imageView = findViewById(R.id.imageView);
        buttonPhoneLogin = findViewById(R.id.buttonPhoneLogin);
        textViewRegister = findViewById(R.id.textViewRegister);

        auth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(v -> loginUser());

        buttonPhoneLogin.setOnClickListener(v -> {
            // Transition to the phone activity
            Intent intent = new Intent(LoginActivity.this, phone.class);
            startActivity(intent);
        });

        textViewRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        // Chuyển đến màn hình chính sau khi đăng nhập thành công
                        Intent intent = new Intent(LoginActivity.this, LogoutActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Đăng nhập không thành công: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

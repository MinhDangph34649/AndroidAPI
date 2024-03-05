package com.example.loginandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginandroid.R;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.loginandroid.R.layout.activity_logout);

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut(); // Đăng xuất khỏi Firebase
            Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Đăng Xuất  thành công: " , Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

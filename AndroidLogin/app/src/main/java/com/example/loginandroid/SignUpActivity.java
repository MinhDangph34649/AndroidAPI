package com.example.loginandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText editTextFullName, editTextEmail, editTextNewPassword, editTextConfirmPassword;
    private Button buttonRegister;
    private ImageView imageView;
    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acitivy);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        imageView = findViewById(R.id.imageView);
        buttonBack = findViewById(R.id.buttonBack);

        auth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(v -> {
            if (validateEditText(editTextFullName) && validateEmail(editTextEmail) &&
                    validatePassword(editTextNewPassword) && validateConfirmPassword(editTextConfirmPassword)) {

                String email = editTextEmail.getText().toString().trim();
                String password = editTextNewPassword.getText().toString().trim();

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean validateEditText(EditText editText) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            editText.setError("Vui lòng điền thông tin");
            return false;
        }
        return true;
    }

    private boolean validateEmail(EditText editText) {
        String email = editText.getText().toString().trim();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText.setError("Email không hợp lệ");
            return false;
        }
        return true;
    }

    private boolean validatePassword(EditText editText) {
        String password = editText.getText().toString().trim();
        if (password.length() < 6) {
            editText.setError("Mật khẩu phải ít nhất 6 ký tự");
            return false;
        }
        return true;
    }

    private boolean validateConfirmPassword(EditText editText) {
        String password = editTextNewPassword.getText().toString().trim();
        String confirmPassword = editText.getText().toString().trim();
        if (!confirmPassword.equals(password)) {
            editText.setError("Mật khẩu xác nhận không khớp");
            return false;
        }
        return true;
    }
}

package com.example.loginandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;

public class phone extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;

    TextView textViewPhoneNumber, textViewOTPphone;
    EditText editTextPhoneNumber, editTextOptNumber;
    Button buttonSendOTP, buttonSendlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        mAuth = FirebaseAuth.getInstance();

        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        textViewOTPphone = findViewById(R.id.textViewOTPphone);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextOptNumber = findViewById(R.id.editTextOptNumber);
        buttonSendOTP = findViewById(R.id.buttonSendOTP);
        buttonSendlogin = findViewById(R.id.buttonSendlogin);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(phone.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                textViewOTPphone.setText("OTP has been sent");
            }
        };

        buttonSendOTP.setOnClickListener(v -> {
            String phoneNumber = editTextPhoneNumber.getText().toString();
            startPhoneNumberVerification(phoneNumber);
        });

        buttonSendlogin.setOnClickListener(v -> {
            String code = editTextOptNumber.getText().toString();
            verifyPhoneNumberWithCode(mVerificationId, code);
        });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+84" + phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        textViewOTPphone.setText("Sign in success");
                        startActivity(new Intent(phone.this, LogoutActivity.class)); // Assuming you have a LogoutActivity
                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            textViewOTPphone.setText("The verification code entered was invalid");
                        }
                    }
                });
    }
}

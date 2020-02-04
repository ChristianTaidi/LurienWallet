package com.christian.lurienwallet.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;

public class LoginActivity extends AppCompatActivity {

    EditText emailIn,passwordIn;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = findViewById(R.id.login_btn);
        emailIn = findViewById(R.id.user_email);
        passwordIn = findViewById(R.id.user_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signInWithEmailAndPassword(emailIn.getText().toString().trim(),passwordIn.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(LoginActivity.this, "User logged", Toast.LENGTH_SHORT).show();
                            Intent main = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(main);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

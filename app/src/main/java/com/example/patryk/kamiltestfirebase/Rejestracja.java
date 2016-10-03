package com.example.patryk.kamiltestfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Rejestracja extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonRegister;
    private TextView textViewRejestracja;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.EditTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        textViewRejestracja = (TextView) findViewById(R.id.textView);
    }

    public void registration(View view) {
        String emial;
        String password;

        emial = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(emial)) {
            Toast.makeText(getApplicationContext(),"Wpisz email",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),"Wpisz hasło",Toast.LENGTH_SHORT).show();
        }



        firebaseAuth.createUserWithEmailAndPassword(emial, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Rejestracja.this,"Rejestracja pomyślna",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Rejestracja.this,"Rejestracja niepomyślna: \n1)hasło musi mieć minimum 6 znaków. \n2)Na podany email zostalo utworzone" +
                            "konto, skontaktuj się z administratorem", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}

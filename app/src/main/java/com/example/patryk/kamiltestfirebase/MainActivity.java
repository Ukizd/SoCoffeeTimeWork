package com.example.patryk.kamiltestfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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


public class MainActivity extends AppCompatActivity {

    private EditText editTextEmailLogowanie;
    private EditText editTextPasswordLogowanie;
    private FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(),Glowna.class));
        }

        editTextEmailLogowanie = (EditText) findViewById(R.id.EditTextEmailLogowanie);
        editTextPasswordLogowanie = (EditText) findViewById(R.id.editTextPasswordLogowanie);
        Button buttonZaloguj = (Button) findViewById(R.id.buttonZaloguj);
        TextView textViewLogowanie = (TextView) findViewById(R.id.textViewLogowanie);

        progressDialog = new ProgressDialog(this);
        buttonZaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               logowanie();
                //Toast.makeText(getApplicationContext(),"textViewLogowanie",Toast.LENGTH_SHORT).show();
            }
        });

        textViewLogowanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Rejestracja.class));
                //Toast.makeText(getApplicationContext(),"textViewLogowanie",Toast.LENGTH_SHORT).show();
            }
        });

    }

//    @Override
//    public void onClick(View v) {
//        if (v == textViewLogowanie) {
//            startActivity(new Intent(this,Rejestracja.class));
//            Toast.makeText(getApplicationContext(),"textViewLogowanie",Toast.LENGTH_SHORT).show();
//        }
//        if (v == buttonZaloguj) {
//            logowanie();
//            Toast.makeText(getApplicationContext(),"buttonZaloguj",Toast.LENGTH_SHORT).show();
//        }
//
//    }
    public void logowanie() {
        String emial;
        String password;

        emial = editTextEmailLogowanie.getText().toString().trim();
        password = editTextPasswordLogowanie.getText().toString().trim();

        if (TextUtils.isEmpty(emial)) {
            Toast.makeText(getApplicationContext(),"Wpisz email",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),"Wpisz hasło",Toast.LENGTH_SHORT).show();
        }

        progressDialog.setMessage("Logowanie");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(emial,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), Glowna.class));

                }

            }
        });
}
}




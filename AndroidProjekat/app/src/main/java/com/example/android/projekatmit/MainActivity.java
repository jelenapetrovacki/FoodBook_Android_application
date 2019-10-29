package com.example.android.projekatmit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    EditText emailId, password, firstName, lastName, phone;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();

        emailId = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        firstName = findViewById(R.id.editText3);
        lastName = findViewById(R.id.editText4);
        phone = findViewById(R.id.editText5);
        btnSignUp = findViewById(R.id.button);
        tvSignIn = findViewById(R.id.textView);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String  email = emailId.getText().toString();
                String  pwd = password.getText().toString();
                final String  fName = firstName.getText().toString();
                final String  lName = lastName.getText().toString();
                final String  phoneNum = phone.getText().toString();

                if(email.isEmpty()){
                    emailId.setError(getString(R.string.enter_email));
                    emailId.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError(getString(R.string.enter_password));
                    password.requestFocus();
                }
                else if(phoneNum.isEmpty()){
                    phone.setError(getString(R.string.enter_phone));
                    phone.requestFocus();
                }
                else if(fName.isEmpty()){
                    firstName.setError(getString(R.string.enter_name));
                    firstName.requestFocus();
                }
                else if(lName.isEmpty()){
                    lastName.setError(getString(R.string.enter_name));
                    lastName.requestFocus();
                }
                else if(pwd.length() < 6) {
                    password.setError(getString(R.string.input_error_password_length));
                    password.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields are empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                User user = new User(fName,lName,email,phoneNum);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(MainActivity.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                startActivity(new Intent(MainActivity.this,HomePageActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }

            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
    }

}

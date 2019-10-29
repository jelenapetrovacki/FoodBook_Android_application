package com.example.android.projekatmit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    Button btnLogout;

    TextView txtNameSurname,txtPhone,txtEmail;
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser  = mFirebaseAuth.getCurrentUser();
    User myUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);

        txtNameSurname = findViewById(R.id.txtNameSurname);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);


        DatabaseReference databaseRef2 = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());


        databaseRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myUser=dataSnapshot.getValue(User.class);
                txtNameSurname.setText(myUser.getName() + ' ' + myUser.getSurname());
                txtPhone.setText("Phone : " + myUser.getPhone());
                txtEmail.setText("Email : " + myUser.getEmail());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        txtNameSurname.setText(myUser.getName() + ' ' + myUser.getSurname());
        txtPhone.setText("Phone : " + myUser.getPhone());
        txtEmail.setText("Email : " + myUser.getEmail());

        btnLogout = findViewById(R.id.logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intToMain);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_home:
                startActivity(new Intent(ProfileActivity.this, HomePageActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

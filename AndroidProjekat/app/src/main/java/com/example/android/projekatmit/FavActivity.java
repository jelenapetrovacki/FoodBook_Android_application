package com.example.android.projekatmit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.FavAdapter;
import adapter.MealsAdapter;
import model.MealRecs;
import model.Meals;

public class FavActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FavAdapter favAdapter;
    MealRecs.MealRec reciepe;
    List<MealRecs.MealRec> lista = new ArrayList<>();


    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser  = mFirebaseAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);



        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(currentUser.getUid()).child("meals");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                   reciepe = snapshot.getValue(MealRecs.MealRec.class);
                   lista.add(reciepe);
                }
                favAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FavActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        recyclerView = (RecyclerView)findViewById(R.id.RecViewMeal2);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(FavActivity.this, 2);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

            favAdapter = new FavAdapter( FavActivity.this,lista);
            recyclerView.setAdapter(favAdapter);





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
                startActivity(new Intent(FavActivity.this, HomePageActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

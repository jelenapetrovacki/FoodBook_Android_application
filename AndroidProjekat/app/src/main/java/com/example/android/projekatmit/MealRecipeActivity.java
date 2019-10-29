package com.example.android.projekatmit;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import adapter.FavAdapter;
import model.MealRecs;
import model.Meals;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRecipeActivity extends AppCompatActivity {

    MealRecs recipes = new MealRecs();
    MealRecs.MealRec reciepe;

    AppCompatImageView imgMealRecipe;
    TextView txtInstructions,txtCategory,txtCountry;
    Button btnYoutube;
    public  int a=0;

    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser  = mFirebaseAuth.getCurrentUser();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_recipe);

        imgMealRecipe = findViewById(R.id.imgMealRecipe);
        txtInstructions = findViewById(R.id.txtInstructions);
        txtCategory = findViewById(R.id.txtCategory);
        txtCountry = findViewById(R.id.txtCountry);
        btnYoutube  = findViewById(R.id.btnYoutube);

        btnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reciepe.getStrYoutube()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            }
        });
        final FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.floatingButton);

        Intent intent2 = getIntent();
        final Meals.Meal selMeal = (Meals.Meal) intent2.getSerializableExtra("selectedMeal");
        final  boolean isLike = intent2.getBooleanExtra("isLiked",false);
        if (isLike == true){
            floatingActionButton.setImageResource(R.drawable.ic_fav2);
            a=1;
        }

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(currentUser.getUid()).child("meals");

        Query likeQuery =  databaseReference.orderByChild("idMeal").equalTo(selMeal.getIdMeal());
        likeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    floatingActionButton.setImageResource(R.drawable.ic_fav2);
                    a=1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                floatingActionButton.setImageResource(R.drawable.ic_fav);
                a=0;
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(a==0 ) {
                    floatingActionButton.setImageResource(R.drawable.ic_fav2);
                    a=1;

                    databaseReference.push().setValue(reciepe);
                }else {
                    floatingActionButton.setImageResource(R.drawable.ic_fav);
                    a=0;
                    Query delQuery = databaseReference.orderByChild("idMeal").equalTo(selMeal.getIdMeal());
                    delQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                snapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(MealRecipeActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

        GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<MealRecs> call = api.getAllMealRecs(selMeal.getIdMeal());

        call.enqueue(new Callback<MealRecs>() {
            @Override
            public void onResponse(Call<MealRecs> call, Response<MealRecs> response) {

                if(response.isSuccessful() && response.body()!=null) {
                    recipes=response.body();


                        reciepe = recipes.getMealRecs().get(0);

                            Picasso.Builder builder = new Picasso.Builder(MealRecipeActivity.this);
                            builder.downloader(new OkHttp3Downloader(MealRecipeActivity.this));
                            builder.build().load(reciepe.getStrMealThumb())
                                    .placeholder((R.drawable.ic_launcher_background))
                                    .error(R.drawable.ic_launcher_background)
                                    .into(imgMealRecipe);

                            txtInstructions.setText(reciepe.getStrInstructions());
                            toolbar.setTitle(selMeal.getStrMeal());
                            txtCategory.setText("Category: " + reciepe.getStrCategory());
                            txtCountry.setText("Country: " + reciepe.getStrArea());






                }
            }

            @Override
            public void onFailure(Call<MealRecs> call, Throwable t) {

                Toast.makeText(MealRecipeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();





    }
}

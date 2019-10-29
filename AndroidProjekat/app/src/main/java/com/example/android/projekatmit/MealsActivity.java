package com.example.android.projekatmit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import adapter.MealsAdapter;
import model.Categories;
import model.Meals;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MealsAdapter mealsAdapter;
    Meals mealList = new Meals();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar3);
        setSupportActionBar(myToolbar);



        Intent i = getIntent();
        Categories.Category category = (Categories.Category) i.getSerializableExtra("cat");

        myToolbar.setSubtitle(""+category.getStrCategory());

        GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<Meals> call = api.getAllMeals(category.getStrCategory());

        call.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {

                if(response.isSuccessful() && response.body()!=null) {
                    mealList=response.body();

                    recyclerView = (RecyclerView)findViewById(R.id.RecViewMeal);
                    GridLayoutManager mGridLayoutManager = new GridLayoutManager(MealsActivity.this, 2);
                    recyclerView.setLayoutManager(mGridLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    mealsAdapter = new MealsAdapter(mealList, MealsActivity.this);
                    recyclerView.setAdapter(mealsAdapter);
                }
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {

                Toast.makeText(MealsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(MealsActivity.this, HomePageActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

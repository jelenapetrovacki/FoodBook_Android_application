package com.example.android.projekatmit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import adapter.CategoriesAdapter;
import model.Categories;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CategoriesAdapter categoriesAdapter;
    Categories categoryList = new Categories();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);


        GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<Categories> call = api.getAllCategories();

        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {

                if(response.isSuccessful() && response.body()!=null) {
                    categoryList=response.body();

                    recyclerView = (RecyclerView)findViewById(R.id.RecViewCat);
                    recyclerView.setLayoutManager(new LinearLayoutManager(HomePageActivity.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    categoriesAdapter = new CategoriesAdapter(HomePageActivity.this,categoryList);
                    recyclerView.setAdapter(categoriesAdapter);
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {

                Toast.makeText(HomePageActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_favorite:
                startActivity(new Intent(HomePageActivity.this, FavActivity.class));
                return true;
            case R.id.action_profile:
                startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    }


package com.example.android.projekatmit;

import java.util.List;

import model.Categories;
import model.MealRecs;
import model.Meals;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("categories.php")
    Call<Categories> getAllCategories();

    @GET("filter.php")
    Call<Meals> getAllMeals(@Query("c") String mealCat);

    @GET("lookup.php")
    Call<MealRecs> getAllMealRecs(@Query("i") String mealID);
}

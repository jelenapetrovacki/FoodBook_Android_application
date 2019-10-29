package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Meals {
    @SerializedName("meals")
    @Expose
    private List<Meal> meals = null;


    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }



    public static class Meal implements Serializable {

        @SerializedName("strMeal")
        @Expose
        private String strMeal;
        @SerializedName("strMealThumb")
        @Expose
        private String strMealThumb;
        @SerializedName("idMeal")
        @Expose
        private String idMeal;

        public Meal(String strMeal, String strMealThumb, String idMeal) {
            this.strMeal = strMeal;
            this.strMealThumb = strMealThumb;
            this.idMeal = idMeal;
        }

        public String getStrMeal() {
            return strMeal;
        }

        public void setStrMeal(String strMeal) {
            this.strMeal = strMeal;
        }

        public String getStrMealThumb() {
            return strMealThumb;
        }

        public void setStrMealThumb(String strMealThumb) {
            this.strMealThumb = strMealThumb;
        }

        public String getIdMeal() {
            return idMeal;
        }

        public void setIdMeal(String idMeal) {
            this.idMeal = idMeal;
        }

    }
}

package com.example.android.projekatmit;

import java.util.List;

import model.MealRecs;

public class User {
    public String name, surname, email, phone;
    //public List<MealRecs.MealRec> meals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

  /*  public List<MealRecs.MealRec> getMeals() {
        return meals;
    }

    public void setMeals(List<MealRecs.MealRec> meals) {
        this.meals = meals;
    }
*/
    public User(){

    }
    public User(String name,String surname, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;

    }
  /*  public User(String name,String surname, String email, String phone,List<MealRecs.MealRec> meals) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.meals = meals;
    }*/
}

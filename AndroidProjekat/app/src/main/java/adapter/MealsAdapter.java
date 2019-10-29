package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.projekatmit.MealRecipeActivity;
import com.example.android.projekatmit.MealsActivity;
import com.example.android.projekatmit.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import model.Meals;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {

    Meals meals;
    Meals.Meal meal;
    Context context;

    public MealsAdapter(Meals meals, Context context) {
        this.meals = meals;
        this.context = context;
    }

    @Override
    public MealsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2,parent,false);
        MealsAdapter.ViewHolder viewHolder = new MealsAdapter.ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MealsAdapter.ViewHolder holder, final int position) {
        meal = meals.getMeals().get(position);

        holder.textMeal.setText(meal.getStrMeal());


        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(meal.getStrMealThumb())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgMeal);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MealRecipeActivity.class);
                i.putExtra("selectedMeal", meals.getMeals().get(position));
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {

        return meals.getMeals().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal;
        TextView textMeal;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            imgMeal = (ImageView)itemView.findViewById(R.id.imgMeal);
            textMeal = (TextView)itemView.findViewById(R.id.textMeal);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }
    }
}

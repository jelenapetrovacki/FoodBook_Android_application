package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.projekatmit.MealRecipeActivity;
import com.example.android.projekatmit.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.MealRecs;
import model.Meals;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder>  {

    Context context;
    MealRecs mealRecs;
    MealRecs.MealRec mealRec;
    List<MealRecs.MealRec> lista;
    Meals.Meal meal2;
    public FavAdapter(Context context,List<MealRecs.MealRec> lista) {
        this.context = context;
        this.lista = lista;
    }

    public FavAdapter(Context context, MealRecs mealRecs) {
        this.context = context;
        this.mealRecs = mealRecs;
    }


    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2,parent,false);
        FavAdapter.ViewHolder viewHolder = new FavAdapter.ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, final int position) {


        mealRec = lista.get(position);


        //mealRec = mealRecs.getMealRecs().get(position);
        holder.textMeal.setText(mealRec.getStrMeal());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(mealRec.getStrMealThumb())

                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgMeal);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MealRecipeActivity.class);
                i.putExtra("selectedMeal", new Meals.Meal(lista.get(position).getStrMeal(),lista.get(position).getStrMealThumb(),lista.get(position).getIdMeal()));
                i.putExtra("isLiked", true);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {

        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMeal;
        TextView textMeal;
        CardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = (ImageView)itemView.findViewById(R.id.imgMeal);
            textMeal = (TextView)itemView.findViewById(R.id.textMeal);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }
    }
}

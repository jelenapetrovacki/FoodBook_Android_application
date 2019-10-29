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

import com.example.android.projekatmit.MealsActivity;
import com.example.android.projekatmit.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;


import model.Categories;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>  {
    Categories categoryList;
    Context context;
    public Categories.Category category;


    public CategoriesAdapter(Context context,Categories categoryList)
    {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        CategoriesAdapter.ViewHolder viewHolder = new CategoriesAdapter.ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoriesAdapter.ViewHolder holder, final int position) {
        category = categoryList.getCategories().get(position);

        holder.textCategory.setText(category.getStrCategory());


        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(category.getStrCategoryThumb())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgCategory);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MealsActivity.class);
                i.putExtra("cat", categoryList.getCategories().get(position));
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {

        return categoryList.getCategories().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgCategory;
        TextView textCategory;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            imgCategory = (ImageView)itemView.findViewById(R.id.imgCategory);
            textCategory = (TextView)itemView.findViewById(R.id.textCategory);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }

    }
}

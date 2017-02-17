package com.viralandroid.receipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by T on 13-02-2017.
 */

public class ProductsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Integer> mimages;
    ArrayList<String> mtitles;
    ArrayList<String> mtime;
    ArrayList<Recipes> recipes;
    public ProductsAdapter(Context context,ArrayList<Recipes> recipes){
        this.context = context;
//        mimages = images;
//        mtitles = titles;
//        mtime   = time;
        this.recipes = recipes;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View item_view = inflater.inflate(R.layout.products,null);
        ImageView product_image = (ImageView) item_view.findViewById(R.id.product_image);
        TextView  product_title = (TextView) item_view.findViewById(R.id.product_title);
        TextView  product_time  = (TextView) item_view.findViewById(R.id.product_time);
        //product_image.setImageResource(mimages.get(position));
        product_title.setText(recipes.get(position).title);
        product_time.setText(recipes.get(position).preparation_time);
        //product_time.setText(mtime.get(position));
        Picasso.with(context).load(recipes.get(position).picture).placeholder(R.drawable.gennaros_pasta01).into(product_image);

        return item_view;
    }
}

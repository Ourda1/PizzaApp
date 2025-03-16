package com.example.pizza.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pizza.R;
import com.example.pizza.classes.Produit;

import java.util.List;

public class PizzaAdapter extends BaseAdapter {
    private List<Produit> produits;
    private LayoutInflater inflater;


    public PizzaAdapter(List<Produit> produits, Context context) {
        this.produits = produits;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return produits.size();
    }

    @Override
    public Object getItem(int position) {
        return produits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, parent, false);
            holder = new ViewHolder();
            holder.img = convertView.findViewById(R.id.img);
            Log.d("PizzaAdapter", "ImageView found: " + (holder.img != null));
            holder.nom = convertView.findViewById(R.id.nom);
            holder.nbrIngredients = convertView.findViewById(R.id.nbrIngredient);
            holder.duree = convertView.findViewById(R.id.duree);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Produit produit = produits.get(position);
        // Ajoutez une vérification de nullité
        if (holder.img != null) {
            holder.img.setImageResource(produit.getPhoto());
        } else {
            Log.e("PizzaAdapter", "ImageView is null for position: " + position);
        }

        holder.img.setImageResource(produit.getPhoto());
        holder.nom.setText(produit.getNom());
        holder.nbrIngredients.setText(produit.getNbrIngredients() + "");
        holder.duree.setText("" + produit.getDuree());

        return convertView;
    }

    private class ViewHolder {
        ImageView img;
        TextView nom;
        TextView nbrIngredients;
        TextView duree;
    }
}

package com.example.victor.bandejapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Victor on 20/09/2016.
 */
public class CategoriaAlimentoAdapter extends ArrayAdapter<CategoriaAlimento>{

    public static class ViewHolder{
        TextView tipoAlimento;
        ImageView iconeAlimento;
        RatingBar ratingBar;
    }


    public CategoriaAlimentoAdapter (Context context, ArrayList<CategoriaAlimento> categorias){
        super(context, 0 , categorias);
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent){

        final ViewHolder viewHolder;

        if (convertView == null){

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.categoria_alimento_fragment, parent, false);

            viewHolder.iconeAlimento = (ImageView) convertView.findViewById(R.id.iconeAlimento);
            viewHolder.tipoAlimento = (TextView) convertView.findViewById(R.id.tipoAlimento);
            viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ratingBar.setOnRatingBarChangeListener(onRatingChangedListener(viewHolder, position));
        viewHolder.ratingBar.setTag(position);
        viewHolder.ratingBar.setRating(getItem(position).getRatingAlimento());

        viewHolder.iconeAlimento.setImageResource(getItem(position).getIconeAlimento());
        viewHolder.tipoAlimento.setText(getItem(position).getTipoAlimento());


        return convertView;
    }

    private RatingBar.OnRatingBarChangeListener onRatingChangedListener(final ViewHolder holder, final int position) {
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float f, boolean b) {

                CategoriaAlimento categoria = getItem(position);
                categoria.setRatingAlimento(f);
            }
        };
    }
}

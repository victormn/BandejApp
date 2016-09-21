package com.example.victor.bandejapp;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;


public class MainFragmentList extends ListFragment {


    private ArrayList<CategoriaAlimento> categoriaAlimentos;
    private CategoriaAlimentoAdapter categoriaAlimentosAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        categoriaAlimentos = new ArrayList<CategoriaAlimento>();
        categoriaAlimentos.add(new CategoriaAlimento("Arroz"));
        categoriaAlimentos.add(new CategoriaAlimento("Arroz Integral"));
        categoriaAlimentos.add(new CategoriaAlimento("Feijão"));
        categoriaAlimentos.add(new CategoriaAlimento("Salada"));
        categoriaAlimentos.add(new CategoriaAlimento("Prato Principal"));
        categoriaAlimentos.add(new CategoriaAlimento("Opção Vegetariana"));
        categoriaAlimentos.add(new CategoriaAlimento("Guarnição"));
        categoriaAlimentos.add(new CategoriaAlimento("Sobremesa"));
        categoriaAlimentos.add(new CategoriaAlimento("Suco"));

        categoriaAlimentosAdapter = new CategoriaAlimentoAdapter(getActivity(), categoriaAlimentos);

        setListAdapter(categoriaAlimentosAdapter);

        getListView().setDivider(null);

    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

    }


}

package com.example.victor.bandejapp.Feedback;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.victor.bandejapp.CategoriaAlimento.CategoriaAlimento;
import com.example.victor.bandejapp.CategoriaAlimento.CategoriaAlimentoAdapter;
import com.example.victor.bandejapp.DataBase.DBAdapter;
import com.example.victor.bandejapp.DataBase.TimeStamp;

import java.util.ArrayList;


public class FeedbackFragmentList extends ListFragment {

    private ArrayList<CategoriaAlimento> categoriaAlimentos;
    private CategoriaAlimentoAdapter categoriaAlimentosAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        categoriaAlimentos = new ArrayList<>();
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

    public CategoriaAlimentoAdapter getCategoriaAlimentoAdapter(){
        return categoriaAlimentosAdapter;
    }
}

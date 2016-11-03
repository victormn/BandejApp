package com.example.victor.bandejapp.CategoriaAlimento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.victor.bandejapp.CategoriaAlimento.CategoriaAlimento;
import com.example.victor.bandejapp.R;

import java.util.ArrayList;

/**
 * Created by Victor on 20/09/2016.
 */
public class CategoriaAlimentoAdapter extends ArrayAdapter<CategoriaAlimento> {

    private ArrayList<CategoriaAlimento> categoriaAlimentos;

    public static class ViewHolder {
        TextView tipoAlimento;
        ImageView iconeAlimento;
        RatingBar ratingBar;
        CheckBox checkBox;

        CheckBox opt1;
        CheckBox opt2;
        CheckBox opt3;
        LinearLayout optLayout;
    }

    public CategoriaAlimentoAdapter(Context context, ArrayList<CategoriaAlimento> categorias) {
        super(context, 0, categorias);
        categoriaAlimentos = new ArrayList<>();
    }

    public ArrayList<CategoriaAlimento> getCategoriaAlimentos() {
        return categoriaAlimentos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        CategoriaAlimento currentCategoria = getItem(position);

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.categoria_alimento_fragment, parent, false);

            viewHolder.iconeAlimento = (ImageView) convertView.findViewById(R.id.iconeAlimento);
            viewHolder.tipoAlimento = (TextView) convertView.findViewById(R.id.tipoAlimento);
            viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

            viewHolder.opt1 = (CheckBox) convertView.findViewById(R.id.opt1);
            viewHolder.opt2 = (CheckBox) convertView.findViewById(R.id.opt2);
            viewHolder.opt3 = (CheckBox) convertView.findViewById(R.id.opt3);
            viewHolder.optLayout = (LinearLayout) convertView.findViewById(R.id.opiniao_layout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Settando a visibilidade do RatingBar
        if (viewHolder.checkBox.isChecked()) {
            viewHolder.ratingBar.setVisibility(View.VISIBLE);

            // Settando a visibilidade das Opcoes
            if(viewHolder.ratingBar.getRating() > 0){
                viewHolder.optLayout.setVisibility(View.VISIBLE);
            } else{
                viewHolder.optLayout.setVisibility(View.GONE);
            }

        } else {
            viewHolder.ratingBar.setVisibility(View.GONE);
            viewHolder.optLayout.setVisibility(View.GONE);
        }

        // Settando o texto das opções
        if (currentCategoria.getRatingAlimento() <= 1.5f){
            viewHolder.opt1.setText("Gosto Ruim");
            viewHolder.opt2.setText("Temperatura Ruim");
            viewHolder.opt3.setText("Não gosto desse alimento");

        }else {
            if (currentCategoria.getRatingAlimento() <= 3.5f) {
                viewHolder.opt1.setText("Sem Gosto");
                viewHolder.opt2.setText("Temperatura Razoável");
                viewHolder.opt3.setText("Gosto pouco desse alimento");
            }else{
                viewHolder.opt1.setText("Saborosa");
                viewHolder.opt2.setText("Temperatura Ideal");
                viewHolder.opt3.setText("Gosto muito desse alimento");
            }
        }

        // Atualizando os valores dos itens da tela
        viewHolder.checkBox.setOnCheckedChangeListener(onCheckChangedListener(viewHolder, position, convertView, parent, true));
        viewHolder.checkBox.setTag(position);
        viewHolder.checkBox.setChecked(currentCategoria.isCheckAlimento());

        viewHolder.ratingBar.setOnRatingBarChangeListener(onRatingChangedListener(position, convertView, parent));
        viewHolder.ratingBar.setTag(position);
        viewHolder.ratingBar.setRating(currentCategoria.getRatingAlimento());

        viewHolder.opt1.setOnCheckedChangeListener(onCheckChangedListener(viewHolder, position, convertView, parent, false));
        viewHolder.opt1.setTag(position);
        viewHolder.opt1.setChecked(currentCategoria.isOpt1Alimento());

        viewHolder.opt2.setOnCheckedChangeListener(onCheckChangedListener(viewHolder, position, convertView, parent, false));
        viewHolder.opt2.setTag(position);
        viewHolder.opt2.setChecked(currentCategoria.isOpt2Alimento());

        viewHolder.opt3.setOnCheckedChangeListener(onCheckChangedListener(viewHolder, position, convertView, parent, false));
        viewHolder.opt3.setTag(position);
        viewHolder.opt3.setChecked(currentCategoria.isOpt3Alimento());

        viewHolder.iconeAlimento.setImageResource(getItem(position).getIconeAlimento());
        viewHolder.tipoAlimento.setText(currentCategoria.getTipoAlimento());

        int igual = 0;

        for (int i = 0; i<categoriaAlimentos.size(); i++)
            if(categoriaAlimentos.get(i).getTipoAlimento() == currentCategoria.getTipoAlimento())
                igual = 1;
        if(igual == 0) categoriaAlimentos.add(currentCategoria);

        return convertView;
    }

    private RatingBar.OnRatingBarChangeListener onRatingChangedListener(final int position,  final View convertView, final ViewGroup parent) {
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float f, boolean b) {

                getItem(position).setRatingAlimento(f);
                getView(position, convertView, parent);
            }
        };
    }

    private CheckBox.OnCheckedChangeListener onCheckChangedListener(final ViewHolder viewHolder, final int position,
                                                                    final View convertView, final ViewGroup parent, final boolean refresh) {
        return new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                CategoriaAlimento currentCategoria = getItem(position);

                if(buttonView == viewHolder.checkBox){
                    currentCategoria.setCheckAlimento(viewHolder.checkBox.isChecked());
                }
                if(buttonView == viewHolder.opt1){
                    currentCategoria.setOpt1Alimento(viewHolder.opt1.isChecked());
                }
                if(buttonView == viewHolder.opt2){
                    currentCategoria.setOpt2Alimento(viewHolder.opt2.isChecked());
                }
                if(buttonView == viewHolder.opt3){
                    currentCategoria.setOpt3Alimento(viewHolder.opt3.isChecked());
                }

                if(refresh)getView(position, convertView, parent);
            }
        };
    }
}

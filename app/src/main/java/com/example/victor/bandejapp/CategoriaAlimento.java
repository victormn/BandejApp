package com.example.victor.bandejapp;

/**
 * Created by Victor on 20/09/2016.
 */
public class CategoriaAlimento {

    private String tipoAlimento;
    private float ratingAlimento;

    public CategoriaAlimento(String tipoAlimento){
        this.tipoAlimento = tipoAlimento;
        this.ratingAlimento = 0;
    }

    public String getTipoAlimento() {return tipoAlimento; }

    public float getRatingAlimento(){return ratingAlimento; }

    public void setRatingAlimento(float ratingAlimento){
        this.ratingAlimento = ratingAlimento;
    }

    public int getIconeAlimento() {

        switch (tipoAlimento){
            case "Arroz":
                return R.drawable.arroz;
            /*case "Arroz Integral":
                return R.drawable.arroz_integral;
            case "Feijão":
                return R.drawable.feijao;
            case "Salada":
                return R.drawable.salada;
            case "Prato Principal":
                return R.drawable.prato_principal;
            case "Opção Vegetariana":
                return R.drawable.opcao_vegetariana;
            case "Guarnição":
                return R.drawable.guarnicao;
            case "Sobremesa":
                return R.drawable.sobremesa;
            case "Suco":
                return R.drawable.suco;*/
        }

        return R.drawable.arroz;
    }

}

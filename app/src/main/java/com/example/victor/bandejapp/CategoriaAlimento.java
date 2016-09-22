package com.example.victor.bandejapp;

import android.widget.CheckBox;

/**
 * Created by Victor on 20/09/2016.
 */
public class CategoriaAlimento {

    private String tipoAlimento;
    private float ratingAlimento;
    private boolean checkAlimento;

    private boolean opt1Alimento;
    private boolean opt2Alimento;
    private boolean opt3Alimento;


    public CategoriaAlimento(String tipoAlimento){
        this.tipoAlimento = tipoAlimento;
        this.ratingAlimento = 0;
        this.checkAlimento = false;

        this.opt1Alimento = false;
        this.opt2Alimento = false;
        this.opt3Alimento = false;
    }

    public String getTipoAlimento() {return tipoAlimento;}

    public float getRatingAlimento(){return ratingAlimento;}

    public boolean isCheckAlimento() {return checkAlimento;}

    public boolean isOpt1Alimento() {return opt1Alimento;}

    public boolean isOpt2Alimento() {return opt2Alimento;}

    public boolean isOpt3Alimento() {return opt3Alimento;}

    public void setRatingAlimento(float ratingAlimento){
        this.ratingAlimento = ratingAlimento;
    }

    public void setCheckAlimento(boolean checkAlimento) {
        this.checkAlimento = checkAlimento;
    }

    public void setOpt1Alimento(boolean opt1Alimento) { this.opt1Alimento = opt1Alimento; }

    public void setOpt2Alimento(boolean opt2Alimento) {
        this.opt2Alimento = opt2Alimento;
    }

    public void setOpt3Alimento(boolean opt3Alimento) {
        this.opt3Alimento = opt3Alimento;
    }

    public int getIconeAlimento() {

        switch (tipoAlimento){
            case "Arroz":
                return R.drawable.arroz;
            case "Arroz Integral":
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
                return R.drawable.suco;
        }

        return R.drawable.arroz;
    }

}

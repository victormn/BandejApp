package com.example.victor.bandejapp.DataBase;

/**
 * Created by Victor on 02/11/2016.
 */
public class TimeStamp {

    private int dia, refeicao;
    private long id;

    // dia
    //AAAAMMDD

    // refeicao
    //0 = ultima refeicao foi o almoco
    //1 = ultima refeicao foi a janta

    public TimeStamp (int dia, int refeicao){
        this.dia = dia;
        this.refeicao = refeicao;
        this.id = 0;
    }

    public int getDia() {return dia;}

    public int getRefeicao() {
        return refeicao;
    }

    public long getId() {
        return id;
    }
}

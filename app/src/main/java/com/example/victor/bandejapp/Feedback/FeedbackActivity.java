package com.example.victor.bandejapp.Feedback;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.victor.bandejapp.CategoriaAlimento.CategoriaAlimento;
import com.example.victor.bandejapp.DataBase.DBAdapter;
import com.example.victor.bandejapp.MainActivity;
import com.example.victor.bandejapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    private ArrayList<String> dataString;
    private String title, dia, refeicao;
    private int dbDia, dbRefeicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = getIntent().getStringExtra(MainActivity.EXTRA_QRCODE_RU_NAME);
        setTitle(title);

        final FeedbackFragmentList fragment = (FeedbackFragmentList) getFragmentManager().findFragmentById(R.id.categoriaAlimentoFrag);

        dataString = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dataString.clear();

                    ArrayList<CategoriaAlimento> categoriaAlimentos = fragment.getCategoriaAlimentoAdapter().getCategoriaAlimentos();
                    for (int i = 0; i < categoriaAlimentos.size(); i++) {

                        if (categoriaAlimentos.get(i).isCheckAlimento()) {
                            dataString.add(categoriaAlimentos.get(i).getTipoAlimento());
                            dataString.add(Float.toString(categoriaAlimentos.get(i).getRatingAlimento()));
                        }
                    }

                    if(dataString.size() == 0)
                        Toast.makeText(getApplicationContext(), "Você não opinou!", Toast.LENGTH_SHORT).show();
                    else {

                        Calendar calendar = Calendar.getInstance();

                        // Verificando o dia
                        String auxDay = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));

                        // para nao perder um digito do dia
                        if(calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                            auxDay = "0" + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
                        }
                        dia = Integer.toString(calendar.get(Calendar.YEAR)) + Integer.toString(calendar.get(Calendar.MONTH) + 1) + auxDay;
                        dbDia = Integer.parseInt(dia);

                        // Verificando a refeicao
                        if(calendar.get(Calendar.HOUR_OF_DAY) < 15){
                            refeicao = "almoco";
                            dbRefeicao = 0;
                        }
                        else{
                            refeicao = "janta";
                            dbRefeicao = 1;
                        }

                        // Atualizando valor na base de dados
                        DBAdapter dbAdapter = new DBAdapter(getBaseContext());
                        dbAdapter.open();
                        dbAdapter.deleteFlag(0);
                        dbAdapter.createTimeStamp(dbDia, dbRefeicao);
                        dbAdapter.close();

                        // Enviando dados para o servidor
//                        for (int i = 0; i < dataString.size(); i+=2){ // nao esta considerando o title, nem o dia, nem a refeicao
//                            sendData(dataString.get(i), dataString.get(i+1));
//                        }
//                        for (int i = 0; i < dataString.size(); i+=2){ // esta considerando o title, tem que mudar a funcao sendData
//                            sendData(dataString.get(i), dataString.get(i+1), title, dia, refeicao);
//                        }

                        Toast.makeText(getApplicationContext(), "Feedback Enviado!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }

    public void sendData(final String categoria, final String rating){

        final RequestQueue queue = Volley.newRequestQueue(this);

        //Instantiate the RequestQueue
        String url = "http://hsdh.dlinkddns.com:1337/vote";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //System.out.println("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Responde error");
            }
        }){
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(categoria, rating);
                return params;
            }
        };

        queue.add(stringRequest);

    }
}

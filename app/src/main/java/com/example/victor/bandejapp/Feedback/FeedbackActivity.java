package com.example.victor.bandejapp.Feedback;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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
    private String title, dia;
    private int dbDia, dbRefeicao;

    private final int LIMITE_ALMOCO_JANTA = 15;

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

                            // Ordem:
                            //  categoria
                            //  rating
                            //  opt1
                            //  opt2
                            //  opt3
                            //  id do RU

                            dataString.add(categoriaAlimentos.get(i).getTipoAlimento());
                            dataString.add(Float.toString(categoriaAlimentos.get(i).getRatingAlimento()));
                            dataString.add(String.valueOf(categoriaAlimentos.get(i).isOpt1Alimento()));
                            dataString.add(String.valueOf(categoriaAlimentos.get(i).isOpt2Alimento()));
                            dataString.add(String.valueOf(categoriaAlimentos.get(i).isOpt3Alimento()));

                            switch (title) {
                                case "USP - SC - Campus 1":
                                    dataString.add("1");
                                    break;
                                case "USP - SC - Campus 2":
                                    dataString.add("2");
                                    break;
                                default:
                                    Toast.makeText(getBaseContext(),getResources().getString(R.string.error_campus), Toast.LENGTH_LONG).show();
                                    System.exit(1);
                                    break;
                            }
                        }
                    }

                    if(dataString.size() == 0)
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.nao_opinou), Toast.LENGTH_SHORT).show();
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
                        if(calendar.get(Calendar.HOUR_OF_DAY) < LIMITE_ALMOCO_JANTA){
                            dbRefeicao = 0;
                        }
                        else{
                            dbRefeicao = 1;
                        }

                        // Atualizando valor na base de dados
                        DBAdapter dbAdapter = new DBAdapter(getBaseContext());
                        dbAdapter.open();
                        dbAdapter.deleteAll();
                        dbAdapter.createTimeStamp(dbDia, dbRefeicao);
                        dbAdapter.close();

                        // Enviando dados para o servidor
                        for (int i = 0; i < dataString.size(); i+=6){
                            sendData(dataString.get(i), dataString.get(i+1), dataString.get(i+5),
                                    dataString.get(i+2), dataString.get(i+3), dataString.get(i+4));
                        }

                        if(isNetworkAvailable(getBaseContext()))
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.feedback), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet), Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
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

    public void sendData(final String categoria, final String rating, final String ID,
                         final String comment0, final String comment1, final String comment2){

        final RequestQueue queue = Volley.newRequestQueue(this);

        final String commentTag0 = "comment0_" + categoria;
        final String commentTag1 = "comment1_" + categoria;
        final String commentTag2 = "comment2_" + categoria;
        final String ratingTag = "rating_" + categoria;
        final String idTag = "id_res";

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
                System.out.println(getResources().getString(R.string.error));
            }
        }){
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(commentTag0, comment0);
                params.put(commentTag1, comment1);
                params.put(commentTag2, comment2);
                params.put(ratingTag, rating);
                params.put(idTag, ID);
                return params;
            }
        };

        queue.add(stringRequest);

    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}

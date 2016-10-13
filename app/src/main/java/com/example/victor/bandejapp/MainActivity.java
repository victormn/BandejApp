package com.example.victor.bandejapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> dataString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final MainFragmentList fragment = (MainFragmentList) getFragmentManager().findFragmentById(R.id.categoriaAlimentoFrag);

        dataString = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ArrayList<CategoriaAlimento> categoriaAlimentos = fragment.getCategoriaAlimentoAdapter().getCategoriaAlimentos();
                    for (int i = 0; i < categoriaAlimentos.size(); i++) {

                        if (categoriaAlimentos.get(i).isCheckAlimento()) {
                            dataString.add(categoriaAlimentos.get(i).getTipoAlimento());
                            dataString.add(Float.toString(categoriaAlimentos.get(i).getRatingAlimento()));
                            dataString.add(Boolean.toString(categoriaAlimentos.get(i).isOpt1Alimento()));
                            dataString.add(Boolean.toString(categoriaAlimentos.get(i).isOpt2Alimento()));
                            dataString.add(Boolean.toString(categoriaAlimentos.get(i).isOpt3Alimento()));
                        }

                    }

                    for (int i = 0; i < dataString.size(); i++){
                        System.out.println(dataString.get(i));
                        //sendData(dataString.get(i));
                    }
                }
            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void sendData(final String dataString){

        final Client socket = new Client("192.168.0.8", 1234);
        socket.setClientCallback(new Client.ClientCallback () {
            @Override
            public void onMessage(String message) {
            }

            @Override
            public void onConnect(Socket s) {
                socket.send(dataString);
                socket.disconnect();
            }

            @Override
            public void onDisconnect(Socket socket, String message) {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        socket.connect();

    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}

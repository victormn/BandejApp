package com.example.victor.bandejapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.victor.bandejapp.DataBase.DBAdapter;
import com.example.victor.bandejapp.DataBase.TimeStamp;
import com.example.victor.bandejapp.Toolbox.CaptureActivityPortrait;
import com.example.victor.bandejapp.Feedback.FeedbackActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private TimeStamp timeStamp;

    public final static String EXTRA_QRCODE_RU_NAME = "com.example.victor.bandejapp.QRCODE_RU_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView myTextView=(TextView)findViewById(R.id.logo_text_main_screen);
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/Walkway_Bold.ttf");
        myTextView.setTypeface(typeFace);

        getSupportActionBar().hide();
    }

    public void onIniciarClick(View view){

        DBAdapter dbAdapter = new DBAdapter(getBaseContext());
        dbAdapter.open();
        timeStamp = dbAdapter.getTimeStamp();
        dbAdapter.close();

        // Fazer a consideracao do timestamp aqui

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Faça a leitura do código do Restaurante Universitário que deseja opinar");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){

                // Verificando a validade do QR Code
                String[] split = result.getContents().split("_");

                if(split[0].equals("BDJ#")) {
                    Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                    intent.putExtra(EXTRA_QRCODE_RU_NAME, split[1]);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(), "QR Code Inválido!", Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

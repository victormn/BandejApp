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

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private final static int ALMOCO_INF = 11;
    private final static int ALMOCO_SUP = 14;
    private final static int JANTA_INF = 17;
    private final static int JANTA_SUP = 20;
    private final static boolean COM_RESTRICOES = false;
    private final static String TAG = "BDJ#";

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
        TimeStamp timeStamp = dbAdapter.getTimeStamp();
        dbAdapter.close();

        // Calculando o dia e a refeicao atual

        // --- DIA ATUAL
        Calendar calendar = Calendar.getInstance();
        String auxDia = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        if(calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            auxDia = "0" + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        }
        int currentDia = Integer.parseInt(Integer.toString(calendar.get(Calendar.YEAR)) + Integer.toString(calendar.get(Calendar.MONTH) + 1) + auxDia);

        // --- REFEICAO ATUAL
        int currentRefeicao = 2; // nao pode opinar com esse valor
        if((calendar.get(Calendar.HOUR_OF_DAY) < ALMOCO_SUP) && (calendar.get(Calendar.HOUR_OF_DAY) >= ALMOCO_INF)){
            currentRefeicao = 0;
        }
        else if((calendar.get(Calendar.HOUR_OF_DAY) < JANTA_SUP) && (calendar.get(Calendar.HOUR_OF_DAY) >= JANTA_INF)){
            currentRefeicao = 1;
        }

        // Verificando a validade
        int error = 0;
        // 0 -> nenhum erro;
        // 1 -> ja opinou nesta refeicao hoje;
        // 2 -> nao esta na hora de opinar

        if(COM_RESTRICOES) {
            if (currentDia == timeStamp.getDia()) {
                if (currentRefeicao == timeStamp.getRefeicao()) error = 1;
            }
            if (currentRefeicao == 2) error = 2;
        }

        if(error == 0) {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt(getResources().getString(R.string.qr_code));
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.setCaptureActivity(CaptureActivityPortrait.class);
            integrator.initiateScan();
        }else if(error == 1){
            Toast.makeText(getBaseContext(), getResources().getString(R.string.ja_opinou), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getBaseContext(), getResources().getString(R.string.nao_esta_na_hora), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){

                // Verificando a validade do QR Code
                String[] split = result.getContents().split("_");

                if(split[0].equals(TAG)) {
                    Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                    intent.putExtra(EXTRA_QRCODE_RU_NAME, split[1]);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.qr_code_invalido), Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

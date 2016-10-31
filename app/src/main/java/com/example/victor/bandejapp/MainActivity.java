package com.example.victor.bandejapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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

        Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
        startActivity(intent);

    }
}

package com.example.rossivation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private TextView titleResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleResult = findViewById(R.id.titleResult);

    }

    public void searchTitle(View view) {

        GetQuote getQuote = new GetQuote(titleResult);
        getQuote.execute("inspire");

    }
}



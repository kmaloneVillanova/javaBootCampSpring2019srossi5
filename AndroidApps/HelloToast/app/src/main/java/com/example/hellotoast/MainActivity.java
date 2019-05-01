package com.example.hellotoast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button toastButton;
    private TextView countText;
    private Button incButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toastButton = findViewById(R.id.toast_button);
    }

    public void showToast(View view) {

        Log.d("MainActivity", "Clicked toast button");
        Toast toast = (Toast) Toast.makeText(this, "Hello Toast", Toast.LENGTH_LONG);

    }

    public void incCount(View view) {

        String numText = countText.getText().toString();
        int num = Integer.parseInt(numText);
        num++;
        countText.setText(num+"");
    }
}

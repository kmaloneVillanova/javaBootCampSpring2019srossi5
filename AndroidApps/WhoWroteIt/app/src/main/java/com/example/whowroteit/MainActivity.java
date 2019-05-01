package com.example.whowroteit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // create objects for each element
    private EditText bookTitleRequest;
    private TextView titleResult;
    private TextView authorResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookTitleRequest = findViewById(R.id.titleToSearch);
        titleResult = findViewById(R.id.titleResult);
        authorResult = findViewById(R.id.authorResult);
        // gives us access to all elements
    }

    public void searchTitle(View view) {

        String bookTitle = bookTitleRequest.getText().toString(); // gets book title from query
        FetchBook fetchBook = new FetchBook(titleResult, authorResult);
        fetchBook.execute(bookTitle);

    }

    public void playSound(View view) {
    }
}

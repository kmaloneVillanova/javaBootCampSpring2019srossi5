package com.example.whowroteit;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class FetchBook extends AsyncTask<String, Void, String> {
    // weak reference is used when something is being run in background
    private WeakReference<TextView> titleResult;
    private WeakReference<TextView> authorResult;

// Line 14-22 are what we are connecting to
    //API URL variables
    private static final String BOOK_BASE_URL =
            "https://www.googleapis.com/books/v1/volumes?";
    // Parameter for the search string.
    private static final String QUERY_PARAM = "q";
    // Parameter that limits search results.
    private static final String MAX_RESULTS = "maxResults";
    // Parameter to filter by print type.
    private static final String PRINT_TYPE = "Books";

    // Constructor
    FetchBook(TextView titleResult, TextView authorResult){
        this.titleResult = new WeakReference<>(titleResult);
        this.authorResult = new WeakReference<>(authorResult);
    }

    @Override
    protected String doInBackground(String... strings) {
        String bookQuery = strings[0]; // returns what we put in FetchBook.execute
        HttpURLConnection urlConnection;
        BufferedReader reader;
        String jsonResponse = null;


        //helper class
        Uri buildURL = Uri.parse(BOOK_BASE_URL).buildUpon().
                appendQueryParameter(QUERY_PARAM, bookQuery).
                appendQueryParameter(MAX_RESULTS, "10").
                appendQueryParameter(PRINT_TYPE, "books").build();
        try {
            URL apiURL = new URL(buildURL.toString());
            urlConnection = (HttpURLConnection)apiURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();
            while (line != null){
                builder.append(line);
                builder.append("\n");
                line = reader.readLine();
                System.out.println("getting json "+line);

            }
            jsonResponse=builder.toString();

            if(builder.length() == 0)
                return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("json response "+jsonResponse);
//        Log.d("JSON RESPONSE",jsonResponse);
        return jsonResponse;


    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String title = null;
        String author = null;

        try {
            JSONObject book = new JSONObject(s);
            JSONArray itemsArray = book.getJSONArray("items");
            for (int i = 0;i<itemsArray.length();i++){
                JSONObject aBook = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = aBook.getJSONObject("volumeInfo");
                title = volumeInfo.getString("title");
                author = volumeInfo.getString("authors");
                if (title != null && author != null){
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (title == null || author == null){
            titleResult.get().setText("No results");
            authorResult.get().setText("");
        }else {
            titleResult.get().setText(title);
            authorResult.get().setText(author);
        }
    }
}

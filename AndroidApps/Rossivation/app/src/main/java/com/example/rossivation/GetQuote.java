package com.example.rossivation;
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

import java.net.URL;


public class GetQuote extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> titleResult;




    //API URL variables

    private static final String QUOTE_BASE_URL =

            "https://quotes.rest/qod.json?";

    // Parameter for the search string.

    private static final String QUERY_PARAM = "category";

    // Parameter that limits search results.





    GetQuote(TextView titleResult) {

        this.titleResult = new WeakReference<>(titleResult);



    }
    @Override

    protected String doInBackground(String... strings) {

        String quoteQuery = strings[0];

        HttpURLConnection urlConnection;

        BufferedReader reader;

        String jsonResponse=null;



        //helper class

        Uri buildURL = Uri.parse(QUOTE_BASE_URL).buildUpon().

                appendQueryParameter(QUERY_PARAM, quoteQuery).build();

        try {

            URL apiURL = new URL(buildURL.toString());

            urlConnection = (HttpURLConnection)apiURL.openConnection();

            urlConnection.setRequestMethod("GET");

            urlConnection.connect();



            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();

            String line = reader.readLine();

            while(line!=null) {

                builder.append(line);

                builder.append("\n");

                line = reader.readLine();

            }

            if(builder.length()==0)

                return null;

            jsonResponse = builder.toString();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        Log.d("JSON RESPONSE",jsonResponse);

        return jsonResponse;

    }
    @Override

    protected void onPostExecute(String s) {

        super.onPostExecute(s);

        String aQuote = null;



        try {

            JSONObject quote = new JSONObject(s);

            JSONObject contents = quote.getJSONObject("contents");

            JSONArray itemsArray = contents.getJSONArray("quotes");

            for(int i =0;i<itemsArray.length();i++) {

                JSONObject quoteObjects = itemsArray.getJSONObject(i);
                aQuote = quoteObjects.getString("quote");





                if(aQuote!=null) {

                    break;

                }

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

        if(aQuote==null) {

            titleResult.get().setText("No results");


        }else {

            titleResult.get().setText(aQuote);


        }



    }

}
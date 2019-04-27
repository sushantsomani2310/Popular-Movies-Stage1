package com.example.android.popularmoviess1.datasource;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MovieData {

    private static final String apiKey = "";//Generate your own API key
    private static final String API_KEY = "api_key";

    public static URL buildURL(String moviesQuery){
        Uri builtUri =Uri.parse(moviesQuery).buildUpon()
                .appendQueryParameter("api_key",apiKey)
                .build();
        URL url = null;
        try{
            url =new URL(builtUri.toString());
        }
        catch (MalformedURLException malEx){
            malEx.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}

package com.example.android.illubamoviesandroid;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Movie> fetchData(String urlString) {
        URL url = generateUrl(urlString);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            System.out.println("FAILED MAKING HTTP REQUEST");
        }
        List<Movie> movies = parseJson(jsonResponse);
        return movies;
    }

    public static URL generateUrl(String urlString) {
        if (urlString == null || urlString.length() < 1) return null;
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            System.out.println("FAILED GENERATING URL");
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) return jsonResponse;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                System.out.println("BAD RESPONSE CODE");
            }
        } catch (IOException e) {
            System.out.println("FAILED OPENING CONNECTION");
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();
            if (inputStream != null) inputStream.close();
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Movie> parseJson(String json) {
        List<Movie> movies = new ArrayList<>();
        JSONObject jsonBase;
        try {
            jsonBase = new JSONObject(json);
            JSONArray moviesJsonArray = jsonBase.getJSONArray("results");
            for (int i = 0; i < moviesJsonArray.length(); i++) {
                String title = "Title Not Found";
                String date = "Date Not Found";
                String poster = "https://image.tmdb.org/t/p/w500";
                double vote = 0;
                String description = "Plot Synopsis Not Found";

                JSONObject currentMovie = moviesJsonArray.getJSONObject(i);

                if (currentMovie.has("title"))
                    title = currentMovie.optString("title");

                if (currentMovie.has("release_date"))
                    date = currentMovie.optString("release_date");

                if (currentMovie.has("poster_path"))
                    poster += currentMovie.optString("poster_path");

                if (currentMovie.has("vote_average"))
                    vote = currentMovie.optDouble("vote_average");

                if (currentMovie.has("overview"))
                    description = currentMovie.optString("overview");

                Movie movie = new Movie(title, date, poster, vote, description);
                movies.add(movie);
            }
        } catch (Exception e) {
            System.out.println("Error parsing JSON");
        }
        return movies;
    }

}

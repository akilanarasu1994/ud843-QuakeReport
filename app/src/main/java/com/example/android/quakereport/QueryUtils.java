package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
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

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

/**
 * Created by akilan on 03/01/17.
 */

public final class QueryUtils {

    private static final String TAG = "QueryUtils";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Returns the URL object from the given string URL.
     * @param stringUrl
     * @return url or null
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error creating url", e);
        }
        return url;
    }

    /**
     * @param url
     * @return the json response from USGS
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the input stream to a string
     *
     * @param inputStream
     * @return string containing the json response
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    private static ArrayList<Earthquake> extractEarthquakes(String earthquakesJson) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        try {
            // Create a JSONObject from the earthquakesJson string.
            JSONObject responseObject = new JSONObject(earthquakesJson);
            // Retrieve the features(earthquakes) array from responseObject.
            JSONArray featuresArray = responseObject.getJSONArray("features");
            // Get the length of the features array to loop through it.
            int featuresArrayLength = featuresArray.length();

            for (int i = 0; i < featuresArrayLength; i++) {
                // Retrieve the feature object.
                JSONObject feature = featuresArray.getJSONObject(i);
                // Retrieve the properties object from the feature object.
                JSONObject properties = feature.getJSONObject("properties");
                // Add the earthquake using the place, mag, time, and url keys' values to the earthquakes array.
                String location = properties.getString("place");
                double magnitude = properties.getDouble("mag");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                earthquakes.add(new Earthquake(location, (double)magnitude, time, url));
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try"
            // block, catch the exception here, so the app doesn't crash. Print a log
            // message with the message from the exception.
            Log.e(TAG, "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes.
        return earthquakes;
    }

    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error making request: ", e);
        }
        // Get the list of earthquakes.
        List<Earthquake> earthquakes = extractEarthquakes(jsonResponse);
        return earthquakes;
    }
}

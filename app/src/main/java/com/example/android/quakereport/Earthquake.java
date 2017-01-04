package com.example.android.quakereport;

/**
 * Created by akilan on 02/01/17.
 */

/**
 * An {@link Earthquake} object contains information related to a single earthquake.
 */
public class Earthquake {

    /** Location of the earthquake */
    private String mLocation;

    /** Magnitude of the earthquake */
    private double mMagnitude;

    /** Date of the earthquake */
    private long mOccurredDate;

    /** Url of the earthquake web page */
    private String mUrl;

    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param location is the location of the earthquake.
     * @param magnitude is the magnitude of the earthquake.
     * @param date is the date the earthquake happened.
     * @param url is the url for the earthquake's web page.
     */
    public Earthquake(String location, double magnitude, long date, String url) {
        mLocation = location;
        mMagnitude = magnitude;
        mOccurredDate = date;
        mUrl = url;
    }

    /**
     * @return location of the earthquake.
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * @return magnitude of the earthquake.
     */
    public double getMagnitude() {
        return mMagnitude;
    }

    /**
     * @return date the earthquake occurred.
     */
    public long getOccurredDate() {
        return mOccurredDate;
    }

    /**
     * @return url for the earthquake web page.
     */
    public String getUrl() {
        return mUrl;
    }
}

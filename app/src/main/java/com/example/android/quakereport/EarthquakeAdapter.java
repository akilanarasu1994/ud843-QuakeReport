package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by akilan on 02/01/17.
 */

/**
 * An {@link EarthquakeAdapter} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@link Earthquake} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    /**
     * Constructs a new {@link EarthquakeAdapter}.
     *
     * @param context of the app
     * @param earthquakes is the list of earthquakes, which is the data source of the adapter
     */
    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    /**
     * Constructs a list item view that displays information about the earthquake at the
     * given position in the list of earthquakes.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View mView = convertView;
        if (mView == null) {
            mView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
        }

        // Find the earthquake at the given position in the list of earthquakes.
        Earthquake currentEarthquake = getItem(position);
        if (currentEarthquake != null) {

            // Format the magnitude to have one digit after the decimal.
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            String magnitudeString = decimalFormat.format(currentEarthquake.getMagnitude());

            // Find the TextView with view ID magnitude_text_view
            TextView magnitudeTextView = (TextView) mView.findViewById(R.id.magnitude_text_view);

            // Set the proper background color on the magnitude circle.
            // Fetch the background from the TextView, which is a GradientDrawable.
            GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

            // Get the appropriate background color based on the current earthquake's magnitude.
            int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

            // Set the color on the magnitude circle.
            magnitudeCircle.setColor(magnitudeColor);

            // Display the magnitude of the current earthquake in that TextView
            magnitudeTextView.setText(magnitudeString);

            // Split the location string into locationOffset string and primaryLocation string.
            String locationString = currentEarthquake.getLocation();
            String locationOffset;
            String primaryLocation;
            // If the location string contains "of", there is a location offset.
            if (locationString.contains("of")) {
                String[] parts = locationString.split("of");
                locationOffset = parts[0] + " of";
                primaryLocation = parts[1];
            } else {
                // Otherwise, it doesn't.
                locationOffset = "Near the";
                primaryLocation = locationString;
            }

            // Find the TextView with view ID location_offset_text_view
            TextView locationOffsetTextView = (TextView) mView.findViewById(R.id.location_offset_text_view);
            // Display the location offset of the current earthquake in that TextView
            locationOffsetTextView.setText(locationOffset);

            // Find the TextView with view ID primary_location_text_view
            TextView primaryLocationTextView = (TextView) mView.findViewById(R.id.primary_location_text_view);
            //Display the primary location of the current earthquake in that TextView.
            primaryLocationTextView.setText(primaryLocation);

            // Split the occurred date into date and time strings.
            long occurredDate = currentEarthquake.getOccurredDate();
            Date date = new Date(occurredDate);

            SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("MMM dd, yyyy");
            String dateString = simpleDateFormatter.format(date);

            SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm aa");
            String timeString = timeFormatter.format(date);

            // Find the TextView with view ID date_text_view
            TextView dateTextView = (TextView) mView.findViewById(R.id.date_text_view);
            // Display the date of the current earthquake in that TextView
            dateTextView.setText(dateString);

            // Find the TextView with view ID time_text_view
            TextView timeTextView = (TextView) mView.findViewById(R.id.time_text_view);
            // Display the time the current earthquake occurred in that TextView.
            timeTextView.setText(timeString);
        }

        // Return the list item view that is now showing the appropriate data.
        return mView;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

}

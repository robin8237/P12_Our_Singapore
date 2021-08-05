package sg.robin.lai.C346.p12_our_singapore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Island> IslandList;

    public CustomAdapter(Context context, int resource, ArrayList<Island> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        IslandList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvName = rowView.findViewById(R.id.textViewName);
        TextView tvArea = rowView.findViewById(R.id.textViewArea);
        TextView tvDescription = rowView.findViewById(R.id.textViewDescription);
        RatingBar rb = rowView.findViewById(R.id.ratingBarList);

        // Obtain the Android Version information based on the position
        Island currentVersion = IslandList.get(position);

        // Set values to the TextView to display the corresponding information
        tvName.setText(currentVersion.getName());
        tvArea.setText(currentVersion.getArea()+"");
        tvDescription.setText(currentVersion.getDescription());
        rb.setRating(currentVersion.getStar());
        return rowView;
    }

}


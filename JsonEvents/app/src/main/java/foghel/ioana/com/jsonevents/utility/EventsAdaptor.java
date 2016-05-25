package foghel.ioana.com.jsonevents.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import foghel.ioana.com.jsonevents.R;
import foghel.ioana.com.jsonevents.model.Event;
import foghel.ioana.com.jsonevents.service.Service;

/**
 * Created by Alin on 26-May-16.
 */
public class EventsAdaptor extends ArrayAdapter<Event> {


    public EventsAdaptor(Context context, int resource, int textViewResourceId, List<Event> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public EventsAdaptor(Context context, int resource) {
        super(context, resource);
    }

    public EventsAdaptor(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public EventsAdaptor(Context context, int resource, Event[] objects) {
        super(context, resource, objects);
    }

    public EventsAdaptor(Context context, int resource, int textViewResourceId, Event[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public EventsAdaptor(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
    }

    public EventsAdaptor(Context context, List<Event> objects) {
        super(context, 0 , objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Event event = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_row_layout, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.title_textView);
        TextView startTimeTextView = (TextView) convertView.findViewById(R.id.startTime_textView);
        TextView endTimeTextView = (TextView) convertView.findViewById(R.id.endTime_textView);

        titleTextView.setText(event.getTitle_english());
        startTimeTextView.setText(event.getStartTime() + "");
        endTimeTextView.setText(event.getEndTime() + "");

        return convertView;
    }
}

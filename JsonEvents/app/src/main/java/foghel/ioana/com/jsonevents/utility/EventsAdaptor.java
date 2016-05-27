package foghel.ioana.com.jsonevents.utility;

import android.content.Context;
import android.graphics.Color;
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
 * Created by Ioana on 26-May-16.
 */
public class EventsAdaptor extends ArrayAdapter<Event> {

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
        TextView cachedTextView = (TextView) convertView.findViewById(R.id.cached_textView);

        if(Service.doesEventExistInCache(event.getEventid())){
            cachedTextView.setText("Saved");
            cachedTextView.setTextColor(Color.RED);
        }else{
            cachedTextView.setText("");
        }

        titleTextView.setText( event.getTitle_english());
        startTimeTextView.setText("Start time: " + event.getStartTimeFormated() + "");
        endTimeTextView.setText( "End  time:  " + event.getEndTimeFormated() + "");

        return convertView;
    }
}

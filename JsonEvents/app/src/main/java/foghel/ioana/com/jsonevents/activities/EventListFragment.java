package foghel.ioana.com.jsonevents.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import foghel.ioana.com.jsonevents.R;
import foghel.ioana.com.jsonevents.service.Service;
import foghel.ioana.com.jsonevents.utility.DownloadJsonTask;
import foghel.ioana.com.jsonevents.utility.EventsAdaptor;

/**
 * Created by Alin on 26-May-16.
 */
public class EventListFragment extends android.support.v4.app.Fragment {

    private ListView eventsListView;
    private EventsAdaptor eventsAdaptor;
    private AdapterView.OnItemClickListener onListItemListener;

    public void setOnListItemListener(AdapterView.OnItemClickListener onListItemListener) {
        this.onListItemListener = onListItemListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.eventslist_fragment, container, false);
        eventsListView = (ListView) fragmentView.findViewById(R.id.eventsListView);

        setUpEventList();

        return fragmentView;
    }

    private void setUpEventList() {
        eventsAdaptor = new EventsAdaptor(getContext(), Service.getEvents());
        eventsListView.setAdapter(eventsAdaptor);
        eventsListView.setOnItemClickListener(onListItemListener);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Trigger loading the data
        Service.LoadJsonData(this, new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        //update the list with data here
                        setUpEventList();
                        break;
                }
            }
        });
    }
}

package foghel.ioana.com.jsonevents.activities;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.logging.LogRecord;

import foghel.ioana.com.jsonevents.R;
import foghel.ioana.com.jsonevents.service.Service;
import foghel.ioana.com.jsonevents.utility.DownloadJsonTask;
import foghel.ioana.com.jsonevents.utility.EventsAdaptor;

public class MainActivity extends AppCompatActivity {

    private ListView eventListView;
    private EventsAdaptor eventsAdaptor;
    private boolean dataHasBeenLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventListView = (ListView) findViewById(R.id.events_ListView);

        if(!dataHasBeenLoaded)
        {
            LoadData();
            dataHasBeenLoaded = true;
        }
    }

    private void LoadData(){
        DownloadJsonTask asyncTask =  new DownloadJsonTask(this, new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0 :
                        //update the list with data here
                        eventsAdaptor = new EventsAdaptor(getApplicationContext(), Service.getEvents());
                        eventListView.setAdapter(eventsAdaptor);
                        break;
                }
            }
        });
        asyncTask.execute();
    }
}

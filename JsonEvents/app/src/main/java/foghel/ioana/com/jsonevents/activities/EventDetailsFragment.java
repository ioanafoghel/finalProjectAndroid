package foghel.ioana.com.jsonevents.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import foghel.ioana.com.jsonevents.R;
import foghel.ioana.com.jsonevents.model.Event;
import foghel.ioana.com.jsonevents.service.Service;

/**
 * Created by Alin on 26-May-16.
 */
public class EventDetailsFragment extends Fragment {

    private int eventIndex = 0;
    private Event event;

    private ImageView imageView;
    private TextView titleTextView;
    private TextView subtitleTextView;
    private TextView startTimeTextView;
    private TextView endTimeTextView;
    private TextView descriptionTextView;
    private TextView urlTextView;
    private Button saveButton;

    private boolean canSaveOffline;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.event_details_fragment, container, false);

        imageView = (ImageView) fragmentView.findViewById(R.id.imageView);
        titleTextView = (TextView) fragmentView.findViewById(R.id.title_textView);
        subtitleTextView = (TextView) fragmentView.findViewById(R.id.subtitle_TextView);
        startTimeTextView = (TextView) fragmentView.findViewById(R.id.startTime_TextView);
        endTimeTextView = (TextView) fragmentView.findViewById(R.id.endTime_TextView);
        descriptionTextView = (TextView) fragmentView.findViewById(R.id.description_textView);
        urlTextView = (TextView) fragmentView.findViewById(R.id.url_textView);
        saveButton = (Button) fragmentView.findViewById(R.id.saveOffline_button);

        setSaveButtonState();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(event!=null && canSaveOffline){
                    Service.StoreEventinDb(event);
                    setSaveButtonState();
                }
            }
        });

        SetViewData();

        return fragmentView;
    }

    private void setSaveButtonState() {

        canSaveOffline = !Service.doesEventExistInCache(event.getEventid());

        if(canSaveOffline){
            saveButton.setBackgroundColor(Color.GREEN);
        }else{
            saveButton.setBackgroundColor(Color.RED);
        }
    }

    private void SetViewData() {
        // Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view
        //  which implements ImageAware interface)
        ImageLoader.getInstance().displayImage(event.getPicture_name(), imageView, new ImageSize(100, 100));
        titleTextView.setText(event.getTitle_english());
        subtitleTextView.setText(event.getSubtitle_english());
        startTimeTextView.setText(event.getStartTimeFormated());
        endTimeTextView.setText(event.getEndTimeFormated());
        descriptionTextView.setText(event.getDescription_english());
        urlTextView.setText(event.getUrl());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getContext()));

        Bundle bundle = this.getArguments();
        eventIndex = bundle.getInt("eventIndex", 0);
        event = Service.getEventAtIndex(eventIndex);
    }
}

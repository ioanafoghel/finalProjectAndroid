package foghel.ioana.com.jsonevents.model;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alin on 25-May-16.
 */
public class Event {

    private String eventid;
    private String subtitle_english;
    private String description_english;
    private String title_english;
    private String url;
    private String picture_name;
    private long startTime;
    private long endTime;
    private String startTimeFormated;
    private String endTimeFormated;

    public Event(String eventid, String subtitle_english, String description_english, String title_english, String url, String picture_name, long startTime, long endTime) {
        this.eventid = eventid;
        this.subtitle_english = subtitle_english;
        this.description_english = description_english;
        this.title_english = title_english;
        this.url = url;
        this.picture_name = picture_name;
        this.startTime = startTime;
        this.endTime = endTime;

        //convert the startTime and endTime to formated date strings here
        this.startTimeFormated = convertFromLongToDateTime(startTime);
        this.endTimeFormated = convertFromLongToDateTime(endTime);
    }

    private String convertFromLongToDateTime(long timeMilliseconds){
        Date date = new Date(timeMilliseconds * 1000);
        Format format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return format.format(date);
    }

    public String getStartTimeFormated() {
        return startTimeFormated;
    }

    public String getEndTimeFormated() {
        return endTimeFormated;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getDescription_english() {
        return description_english;
    }

    public void setDescription_english(String description_english) {
        this.description_english = description_english;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getPicture_name() {
        return picture_name;
    }

    public void setPicture_name(String picture_name) {
        this.picture_name = picture_name;
    }

    public String getSubtitle_english() {
        return subtitle_english;
    }

    public void setSubtitle_english(String subtitle_english) {
        this.subtitle_english = subtitle_english;
    }

    public String getTitle_english() {
        return title_english;
    }

    public void setTitle_english(String title_english) {
        this.title_english = title_english;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

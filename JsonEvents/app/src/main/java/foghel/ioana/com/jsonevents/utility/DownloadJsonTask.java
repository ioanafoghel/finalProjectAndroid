package foghel.ioana.com.jsonevents.utility;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import foghel.ioana.com.jsonevents.service.Service;
import foghel.ioana.com.jsonevents.storage.Storage;

/**
 * Created by Ioana on 25-May-16.
 */
public class DownloadJsonTask extends AsyncTask<String, String, Void> {

    private Fragment eventListFragment;
    InputStream inputStream = null;
    String result = "";
    private static final String TAG_EVENTS = "events";

    private static final String EVENT_ID = "eventid";
    private static final String SUBTITLE_ENGLISH = "subtitle_english";
    private static final String DESCRIPTION_ENGLISH = "description_english";
    private static final String TITLE_ENGLISH = "title_english";
    private static final String URL = "url";
    private static final String PICTURE_NAME = "picture_name";
    private static final String DATE_LIST = "datelist";

    Handler onFinishHandler;
    Storage storage;

    ProgressDialog progressDialog;

    public DownloadJsonTask(Storage storage, Fragment eventListFragment, Handler onFinishHandler) {
        this.eventListFragment = eventListFragment;
        this.storage = storage;
        progressDialog = new ProgressDialog(eventListFragment.getContext());
        this.onFinishHandler = onFinishHandler;
    }

    @Override
    protected void onPreExecute() {

        progressDialog.setMessage("Downloading your data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(String... params) {
        String url_select = "http://events.makeable.dk/api/getEvents";

        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        try {
            // Set up HTTP post

            // HttpClient is more then less deprecated. Need to change to URLConnection
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url_select);
            httpPost.setEntity(new UrlEncodedFormEntity(param));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            // Read content & Log
            inputStream = httpEntity.getContent();
        } catch (UnsupportedEncodingException e1) {
            //Log.e("UnsupportedEncodingException", "");
            e1.printStackTrace();
        } catch (ClientProtocolException e2) {
            Log.e("ClientProtocolException", e2.toString());
            e2.printStackTrace();
        } catch (IllegalStateException e3) {
            Log.e("IllegalStateException", e3.toString());
            e3.printStackTrace();
        } catch (IOException e4) {
            Log.e("IOException", e4.toString());
            e4.printStackTrace();
        }
        // Convert response to string using String Builder
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();
            Toast.makeText(eventListFragment.getContext(), "Got json", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            //Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //parse JSON data
        try {

            if (result == null || result.isEmpty()) {
                onFinishHandler.sendEmptyMessage(1);
                storage.dataHasBeenLoaded = false;
                this.progressDialog.dismiss();
                return;
            }

            JSONObject jsonObj = new JSONObject(result);

            JSONArray events = jsonObj.getJSONArray(TAG_EVENTS);
            for (int i = 0; i < events.length(); i++) {
                JSONObject event = events.getJSONObject(i);
                String eventid = event.getString(EVENT_ID);
                String subtitle_english = event.getString(SUBTITLE_ENGLISH);
                String description_english = event.getString(DESCRIPTION_ENGLISH);
                String title_english = event.getString(TITLE_ENGLISH);
                String url = event.getString(URL);
                String picture_name = event.getString(PICTURE_NAME);

                JSONArray dateList = event.getJSONArray(DATE_LIST);
                JSONObject date = dateList.getJSONObject(0);

                long startTime = date.getLong("start");
                long endTime = date.getLong("end");

                if (!title_english.isEmpty()) {
                    Service.CreateEvent(eventid, subtitle_english, description_english, title_english, url, picture_name, startTime, endTime);
                }
            }

            Log.i("Service Array Length:", Service.getEvents().size() + "");
            onFinishHandler.sendEmptyMessage(0);
            storage.dataHasBeenLoaded = true;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //jsonTextView.setText(result);
        this.progressDialog.dismiss();
    } // protected void onPostExecute(Void v)
}

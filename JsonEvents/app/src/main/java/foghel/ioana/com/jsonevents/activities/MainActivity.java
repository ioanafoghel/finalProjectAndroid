package foghel.ioana.com.jsonevents.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import foghel.ioana.com.jsonevents.R;
import foghel.ioana.com.jsonevents.utility.DownloadJsonTask;

public class MainActivity extends AppCompatActivity {

    private Button getJsonButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getJsonButton = (Button) findViewById(R.id.get_json_Button);

        getJsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Get Json", Toast.LENGTH_SHORT).show();
            }
        });

        new DownloadJsonTask(this).execute();
    }
}

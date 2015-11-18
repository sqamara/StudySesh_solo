package team8.studysesh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EnterGroupInfo extends AppCompatActivity {

    // Defining key values for data
    public final static String TITLE = "title";
    public final static String START_TIME = "start_time";
    public final static String LOCATION = "location";
    public final static String CAPACITY = "capacity";
    public final static String DESCRIPTION = "description";
    public final static String ID = "id";
    public final static String OWNER = "owner";
    public final static String MEMBERS = "members";


    // ASyncTask TAG log
    private static final String TAG = "post_event";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_group_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postEventData(view);
            }
        });
    }

    public void postEventData(View view) {
        EditText theClass = (EditText) findViewById(R.id.theClass);
        if (theClass.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), R.string.retry_enter_group_info, Toast.LENGTH_SHORT).show();
            return;
        }
        EditText location = (EditText) findViewById(R.id.location);
        EditText capacity = (EditText) findViewById(R.id.capacity);
        EditText description = (EditText) findViewById(R.id.description);

        String str_owner = LoginActivity.DUMMY_CREDENTIALS.get(LoginActivity.userIndex).split("@")[0];
        String str_courseTitle = theClass.getText().toString();
        String str_location;
        if (location.getText().toString().length() > 0)
            str_location = location.getText().toString();
        else
            str_location = "RIGHT HERE!";
        int inputCapacity;
        if (capacity.getText().toString().length() > 0)
            inputCapacity = Integer.parseInt(capacity.getText().toString());
        else
            inputCapacity = 5;
        String str_capacity = Integer.toString(inputCapacity);
        String str_description;
        if (description.getText().toString().length() > 0)
            str_description = description.getText().toString();
        else
            str_description = "Lets study ";// + str_courseTitle;

        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        Date theDate = new Date();
        String str_startTime = dateFormat.format(theDate).toString();

        System.out.println( str_owner );

        Ion.with(this)
                .load("http://198.199.98.53/scripts/post_event_data.php")
                .setBodyParameter(TITLE, str_courseTitle)
                .setBodyParameter(START_TIME, str_startTime)
                .setBodyParameter(LOCATION, str_location)
                .setBodyParameter(CAPACITY, str_capacity)
                .setBodyParameter(DESCRIPTION, str_description)
                .setBodyParameter(OWNER, str_owner)
                .asString();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Success.");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();

        ListGroups.adapter.notifyDataSetChanged();
        //finish();
    }

}

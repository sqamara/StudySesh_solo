package team8.studysesh;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayGroupInfo extends AppCompatActivity {
    final List<String> event_users = new ArrayList<>();

    public static int selectedGroup = -1;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_group_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(ListGroups.listItems.get(selectedGroup).theClass);

        FloatingActionButton join = (FloatingActionButton) findViewById(R.id.Join);
        FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.Delete);

        //TODO: figure out how to make buttons not show
        if (ListGroups.listItems.get(selectedGroup).owner.equals(
                LoginActivity.DUMMY_CREDENTIALS.get(LoginActivity.userIndex).split("@")[0])){
            // allow to delete but not join
            join.hide();
        }
        else {
            // allow to join but not delete
            delete.hide();

        }
        context = this;
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setCancelable(true);
                builder1.setPositiveButton("Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setCancelable(true);
                builder2.setPositiveButton("Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });
                AlertDialog alert11;
                if (event_users.size()+1 >= ListGroups.listItems.get(selectedGroup).cap) {
                    builder1.setMessage("Unable to join group, group is full.");
                    alert11 = builder1.create();

                }
                else if (event_users.contains(LoginActivity.DUMMY_CREDENTIALS.get(LoginActivity.userIndex).split("@")[0])) {
                    builder1.setMessage("Unable to join group, you have already joined.");
                    alert11 = builder1.create();
                }
                else {
                builder2.setMessage("Success.");
                Ion.with(context)
                        .load("http://198.199.98.53/scripts/post_join_event.php")
                        .setBodyParameter("id", ListGroups.listItems.get(selectedGroup).id)
                        .setBodyParameter("username", LoginActivity.DUMMY_CREDENTIALS.get(LoginActivity.userIndex).split("@")[0])
                        .asString();
                    alert11 = builder2.create();
                }
                alert11.show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ListGroups.listItems.get(selectedGroup).owner.equals(LoginActivity.DUMMY_CREDENTIALS.get(LoginActivity.userIndex).split("@")[0])) {
                    System.err.println(ListGroups.listItems.get(selectedGroup).id);
                    Ion.with(context)
                            .load("http://198.199.98.53/scripts/delete_event.php")
                            .setBodyParameter("id", ListGroups.listItems.get(selectedGroup).id)
                            .asString();

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
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

                    try {
                        System.err.println("sleeping thread");
                        Thread.currentThread().sleep(500);
                    } catch (Exception ex){
                        System.err.println(ex.getMessage());
                    }

                    ListGroups.updateList();
                    Search.foundItems.clear();
                    if (Search.adapter != null)
                        Search.adapter.notifyDataSetChanged();
                    alert11.show();
                }
            }
        });

        String[] startEnd = ListGroups.listItems.get(selectedGroup).time.split("@");
        String[] start = startEnd[0].split(";");
        String[] end = startEnd[1].split(";");

        TextView tv1 = (TextView)findViewById(R.id.Started);
        tv1.setText(start[0]+ " " + start[1]);

        TextView tv2 = (TextView)findViewById(R.id.Location);
        tv2.setText(ListGroups.listItems.get(selectedGroup).location);

        TextView tv3 = (TextView)findViewById(R.id.Capacity);
        tv3.setText(ListGroups.listItems.get(selectedGroup).members + "/" +
                ListGroups.listItems.get(selectedGroup).cap);

        TextView tv4 = (TextView)findViewById(R.id.Description);
        tv4.setText(ListGroups.listItems.get(selectedGroup).description);

        TextView tv5 = (TextView)findViewById(R.id.Owner);
        tv5.setText(ListGroups.listItems.get(selectedGroup).owner);

        TextView tv6 = (TextView)findViewById(R.id.End);
        tv6.setText(end[0] + " " + end[1]);


        // get memebrs
        Ion.with(this)
                .load("http://198.199.98.53/scripts/get_event_users.php?id=" + ListGroups.listItems.get(selectedGroup).id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            String json_str = result.getAsJsonArray("event_users").toString();


                            JSONArray jsonArray = new JSONArray(json_str);
                            event_users.clear();
                            System.err.println("length of array is : " + jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                event_users.add(obj.get("user").toString());
                            }
                            System.err.println("length of event users is : " + event_users.size());


                        } catch (Exception ex) {
                            System.err.println(ex.getMessage());
                        }
                        String user_members = "";
                        if (event_users.size() != 0) {
                            user_members += event_users.get(0);
                            System.err.println("user members : " + user_members);
                            for (int i = 1; i < event_users.size(); i++) {

                                user_members += "\n" + event_users.get(i);
                                System.err.println("user members : " + user_members);
                            }
                        }

                        System.err.println("printing members : " + user_members);

                        TextView tv7 = (TextView) findViewById(R.id.Members);
                        tv7.setText(user_members);

                        ListGroups.updateList();

                    }
                });
    }

}

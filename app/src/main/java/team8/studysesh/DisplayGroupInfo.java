package team8.studysesh;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class DisplayGroupInfo extends AppCompatActivity {

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //TODO: figure out structure for joining
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

        TextView tv1 = (TextView)findViewById(R.id.Started);
        tv1.setText(ListGroups.listItems.get(selectedGroup).time);

        TextView tv2 = (TextView)findViewById(R.id.Location);
        tv2.setText(ListGroups.listItems.get(selectedGroup).location);

        TextView tv3 = (TextView)findViewById(R.id.Capacity);
        tv3.setText(ListGroups.listItems.get(selectedGroup).members + "/" +
                ListGroups.listItems.get(selectedGroup).cap);

        TextView tv4 = (TextView)findViewById(R.id.Description);
        tv4.setText(ListGroups.listItems.get(selectedGroup).description);

        TextView tv5 = (TextView)findViewById(R.id.Owner);
        tv5.setText(ListGroups.listItems.get(selectedGroup).owner);
    }

}

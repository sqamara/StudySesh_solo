package team8.studysesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.List;

public class EnterGroupInfo extends AppCompatActivity {
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
                EditText theClass = (EditText) findViewById(R.id.theClass);
                if (theClass.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), R.string.retry_enter_group_info, Toast.LENGTH_SHORT).show();
                    return;
                }
                EditText location = (EditText) findViewById(R.id.location);
                EditText capacity = (EditText) findViewById(R.id.capacity);
                EditText description = (EditText) findViewById(R.id.description);

                String inputOwner = LoginActivity.DUMMY_CREDENTIALS.get(LoginActivity.userIndex).split("@")[0];
                String inputClass = theClass.getText().toString();
                String inputLocation;
                if (location.getText().toString().length() > 0)
                    inputLocation = location.getText().toString();
                else
                    inputLocation = "RIGHT HERE!";
                int inputCapacity;
                if (capacity.getText().toString().length() > 0)
                    inputCapacity = Integer.parseInt(capacity.getText().toString());
                else
                    inputCapacity = 5;
                String inputDescription;
                if (description.getText().toString().length() > 0)
                    inputDescription = description.getText().toString();
                else
                    inputDescription = "Let's study " + inputClass;


                ListGroups.listItems.add(new StudyGroupModel(
                        inputOwner,
                        inputClass,
                        inputLocation,
                        inputCapacity,
                        inputDescription
                ));

                ListGroups.adapter.notifyDataSetChanged();
                finish();
            }
        });
    }

}

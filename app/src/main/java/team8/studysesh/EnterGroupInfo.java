package team8.studysesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

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
                EditText location = (EditText) findViewById(R.id.location);
                EditText capacity = (EditText) findViewById(R.id.capacity);
                EditText description = (EditText) findViewById(R.id.description);

                ListGroups.listItems.add(new StudyGroupModel(
                        LoginActivity.DUMMY_CREDENTIALS.get(LoginActivity.userIndex).split("@")[0],
                        theClass.getText().toString(),
                        location.getText().toString(),
                        Integer.parseInt(capacity.getText().toString()),
                        description.getText().toString()
                        ));
                ListGroups.adapter.notifyDataSetChanged();
                finish();
            }
        });
    }

}

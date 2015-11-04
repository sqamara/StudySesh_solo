package team8.studysesh;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListGroups extends ListActivity {

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    public static final ArrayList<StudyGroupModel> listItems=new ArrayList<StudyGroupModel>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    public static ArrayAdapter<StudyGroupModel> adapter;

    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_groups);

        adapter=new ArrayAdapter<StudyGroupModel>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEnterGroupInfo(); // using new intent
                //addNewStudyGroup(view); // using dialog box
            }
        });
    }
    @Override
    protected void onListItemClick (ListView l, View v, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Description:");
        builder.setMessage(listItems.get(position).description + "\nOwner: " + listItems.get(position).owner);

        // Set up the buttons
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteGroup(position);
                dialog.cancel();
            }
        });


        builder.show();
    }

// old impl using dialog box
//    private void addNewStudyGroup(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Class Name");
//
//        // Set up the input
//        final EditText input = new EditText(this);
//        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//        input.setInputType(InputType.TYPE_CLASS_TEXT);
//        builder.setView(input);
//
//        // Set up the buttons
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                goToEnterGroupInfo();
//                m_Text = input.getText().toString();
//                listItems.add(0, new StudyGroupModel(LoginActivity.DUMMY_CREDENTIALS.get(LoginActivity.userIndex)));
//                adapter.notifyDataSetChanged();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        builder.show();
//    }

    public void goToEnterGroupInfo() {
        Intent intent = new Intent(this, EnterGroupInfo.class);
        startActivity(intent);
    }

    private void deleteGroup(int position) {
        if (listItems.get(position).owner.equals(LoginActivity.DUMMY_CREDENTIALS.get(LoginActivity.userIndex).split("@")[0])) {
            listItems.remove(position);
            ListGroups.adapter.notifyDataSetChanged();
        }
    }

}

package team8.studysesh;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Search extends ListActivity {
    public static final ArrayList<StudyGroupModel> foundItems=new ArrayList<StudyGroupModel>();
    public static ArrayAdapter<StudyGroupModel> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        adapter=new ArrayAdapter<StudyGroupModel>(this,
                android.R.layout.simple_list_item_1,
                foundItems) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                String[] pieces = foundItems.get(position).toString().split("<br>", 2);
                text.setText(Html.fromHtml("<b>" + pieces[0] + "<br>" + "</b>" + "<small>" + pieces[1] + "</small>"));
                if (position%2 == 0)
                    view.setBackgroundColor(Color.LTGRAY);
                else
                    view.setBackgroundColor(Color.TRANSPARENT);
                return view;
            }

        };
        setListAdapter(adapter);
        foundItems.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onListItemClick (ListView l, View v, final int position, long id) {
        StudyGroupModel currentModel = foundItems.get(position);
        int i = 0;
        for (StudyGroupModel m : ListGroups.listItems) {
            if (m.owner.equals(currentModel.owner) &&
                    m.description.equals(currentModel.description) &&
                    m.location.equals(currentModel.location)
                    )
                break;
            i++;
        }
        System.err.println(i + " : " + ListGroups.listItems.size());
        DisplayGroupInfo.selectedGroup = i;
        goToDisplayGroupInfo();
    }

    public void searchContent(View v) {
        TextView text = (TextView) findViewById(R.id.search_entry);
        String theSearch = text.getText().toString().toLowerCase();
        foundItems.clear();
        for (StudyGroupModel m : ListGroups.listItems) {
            if (m.owner.toLowerCase().contains(theSearch)
                    || m.theClass.toLowerCase().contains(theSearch)
                    || m.description.toLowerCase().contains(theSearch)
                    || m.location.toLowerCase().contains(theSearch))
                foundItems.add(m);
        }
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        adapter.notifyDataSetChanged();
    }

    public void goToDisplayGroupInfo() {
        Intent intent = new Intent(this, DisplayGroupInfo.class);
        startActivity(intent);
    }

}

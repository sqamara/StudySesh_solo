package team8.studysesh;

import android.app.Instrumentation;
import android.support.design.widget.FloatingActionButton;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by sebastian on 12/3/15.
 */
public class DisplayGroupInfoTest
        extends ActivityInstrumentationTestCase2<LoginActivity> {

    // LoginActivity UI
    private LoginActivity testLoginActivity;
    private TextView email;
    private TextView pswd;
    private Button signIn;
    // Monitoring and control
    private Wait w = new Wait();
    private Instrumentation testInstrumentation;
    private Instrumentation.ActivityMonitor monitor;
    // ListGroups UI
    private ListGroups testListGroups;
    private FloatingActionButton makeGroupFab;
    private ListView listGroupsList;
    // EnterGroupInfo UI
    private EnterGroupInfo testEnterGroupInfo;
    private EditText theClass;
    private EditText location;
    private EditText startDateInput;
    private EditText startTimeInput;
    private EditText endDateInput;
    private EditText endTimeInput;
    private EditText capacity;
    private EditText description;
    private FloatingActionButton submitGroupInfoFab;
    public String startTime, endTime;
    // Display GroupInfo UI
    public DisplayGroupInfo testDisplayGroupInfo;
    public TextView Started;
    public TextView End;


    public DisplayGroupInfoTest() {
        super(LoginActivity.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // login
        testLoginActivity = getActivity();
        email =
                (TextView) testLoginActivity
                        .findViewById(R.id.email);
        pswd =
                (TextView) testLoginActivity
                        .findViewById(R.id.password);
        signIn = (Button) testLoginActivity.findViewById(R.id.email_sign_in_button);

        testLoginActivity.runOnUiThread(new Runnable() {
            public void run() {
                email.setText("testMakeGroup@ucsd.edu");
                pswd.setText("qqqqq");
                signIn.performClick();
            }
        });
        //List iteraction
        testInstrumentation = getInstrumentation();
        monitor = testInstrumentation.addMonitor(ListGroups.class.getName(), null, false);
        testListGroups = (ListGroups) testInstrumentation.waitForMonitor(monitor);
        listGroupsList = testListGroups.getListView();
        makeGroupFab = (FloatingActionButton) testListGroups.findViewById(R.id.fab);
        testListGroups.runOnUiThread(new Runnable() {
            public void run() {
                makeGroupFab.performClick();
            }
        });
        //EnterGroupInfo Setup
        monitor = testInstrumentation.addMonitor(EnterGroupInfo.class.getName(), null, false);
        testEnterGroupInfo = (EnterGroupInfo) testInstrumentation.waitForMonitor(monitor);
        submitGroupInfoFab = (FloatingActionButton) testEnterGroupInfo.findViewById(R.id.fab);
        theClass = (EditText) testEnterGroupInfo.findViewById(R.id.theClass);
        location = (EditText) testEnterGroupInfo.findViewById(R.id.autocomplete_places);
        startDateInput = (EditText) testEnterGroupInfo.findViewById(R.id.startDateInput);
        startTimeInput = (EditText) testEnterGroupInfo.findViewById(R.id.startTimeInput);
        endDateInput = (EditText) testEnterGroupInfo.findViewById(R.id.endDateInput);
        endTimeInput = (EditText) testEnterGroupInfo.findViewById(R.id.endTimeInput);
        capacity = (EditText) testEnterGroupInfo.findViewById(R.id.capacity);
        description = (EditText) testEnterGroupInfo.findViewById(R.id.description);
        testEnterGroupInfo.runOnUiThread(new Runnable() {

            public void run() {
                System.err.println("entering info");
                theClass.setText("Test Group built by DisplayGroupInfoTest");
                location.setText("AndroidStudio");
                startDateInput.setText(new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()));
                startTime = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                startTimeInput.setText(startTime);
                Calendar enddate = Calendar.getInstance();
                enddate.add(Calendar.YEAR, 999);
                endDateInput.setText(new SimpleDateFormat("MM/dd/yyyy").format(enddate.getTime()));
                Calendar endCal = Calendar.getInstance();
                endTime = new SimpleDateFormat("HH:mm").format(endCal.getTime());
                endTimeInput.setText(endTime);
                capacity.setText("123");
                description.setText("This test was built to test the following scenario: "
                );
                submitGroupInfoFab.performClick();
            }
        });
        w.customWait(1500);
        testEnterGroupInfo.finish();
    }

    @Override
    protected void tearDown() throws Exception {
        testListGroups.finish();
        testLoginActivity.finish();
        super.tearDown();
    }

    /*  Given the list of groups
        when I clicked a group in the list
        then I am shown the details for that group
     */
    public boolean isCorrectGroupInfo = false;
    public void testDisplayGroupInfoOfNewlyCreated() {
        // Given the list of groups (done in setup)
        testEnterGroupInfo.runOnUiThread(new Runnable() {
            public void run() {
                listGroupsList.performItemClick(listGroupsList.getAdapter().getView(
                                listGroupsList.getAdapter().getCount()-1, null, null),
                        listGroupsList.getAdapter().getCount()-1,
                        listGroupsList.getAdapter().getItemId(
                                listGroupsList.getAdapter().getCount()-1));
            }
        });

        //then I am shown the details for that group
        monitor = testInstrumentation.addMonitor(DisplayGroupInfo.class.getName(), null, false);
        testDisplayGroupInfo = (DisplayGroupInfo) testInstrumentation.waitForMonitor(monitor);
        Started = (TextView) testDisplayGroupInfo.findViewById(R.id.Started);
        End = (TextView) testDisplayGroupInfo.findViewById(R.id.End);
        testDisplayGroupInfo.runOnUiThread(new Runnable() {
            public void run() {
                if (!(Started.getText().toString().contains(startTime))) {
                    System.err.println( Started.getText().toString() + " :: " + startTime);
                    isCorrectGroupInfo = false;
                }
                else if (!(End.getText().toString().contains(endTime))) {
                    System.err.println(End.getText().toString() + " :: " + endTime);
                    isCorrectGroupInfo = false;
                }
                else
                    isCorrectGroupInfo = true;
            }
        });
        w.customWait(1000);

        assertTrue(isCorrectGroupInfo);
        testDisplayGroupInfo.finish();
    }


    private class Wait {
        public void customWait(int i){
            try {
                // give time to load and wait for server interaction
                Thread.sleep(i);
            } catch (InterruptedException e) {}
        }
    }

}
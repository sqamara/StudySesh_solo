package team8.studysesh;

import android.app.Instrumentation;
import android.support.design.widget.FloatingActionButton;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by sebastian on 12/3/15.
 */
public class EnterGroupInfoTest
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

    public EnterGroupInfoTest() {
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
        w.customWait(1000);
    }

    @Override
    protected void tearDown() throws Exception {
        testEnterGroupInfo.finish();
        testListGroups.finish();
        testLoginActivity.finish();
        super.tearDown();
    }



    /* Given that the user fills all the fields
       when presses the add button
       then a new Study Group is made
       and shown in the List
     */
    public String startTime, endTime;
    public void testCorrectEntry() {
        testEnterGroupInfo.runOnUiThread(new Runnable() {

            public void run() {
                System.err.println("entering info");
                theClass.setText("Test Group built by EnterGroupInfoTest");
                location.setText("AndroidStudio");
                startDateInput.setText(new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()));
                startTime = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                startTimeInput.setText(startTime);
                endDateInput.setText(new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()));
                Calendar endCal = Calendar.getInstance();
                endCal.add(Calendar.MINUTE, 5);
                endTime = new SimpleDateFormat("HH:mm").format(endCal.getTime());
                endTimeInput.setText(endTime);
                capacity.setText("123");
                description.setText("This test was built to test the following scenario: " +
                        "Given that the user fills all the fields " +
                        "when presses the add button " +
                        "then a new study group is made " +
                        "and shown in the List");
                submitGroupInfoFab.performClick();
            }
        });
        w.customWait(1500); // wait for UI thread
        //search for the created group in the arraylist
        boolean pass = false;
        for (StudyGroupModel m : ListGroups.listItems) {
            if (m.time.contains(endTime) && m.time.contains(startTime))
                pass = true;

        }
        assertTrue(pass);
    }


    /* Given that the user does not fill all fields appropriately
       when presses the add button
       then a new Study Group is not made
       and the user is informed
    */
    int beforeIncorrectEntry, afterIncorrectEntry;
    public void testIncorrectEntry() {
        testEnterGroupInfo.runOnUiThread(new Runnable() {

            public void run() {
                beforeIncorrectEntry = ListGroups.listItems.size();
                submitGroupInfoFab.performClick();
                afterIncorrectEntry = ListGroups.listItems.size();

            }
        });
        w.customWait(1500); // wait for UI thread
        assertTrue(beforeIncorrectEntry == afterIncorrectEntry);
        //unable to check toasts from junit
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
package team8.studysesh;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by sebastian on 12/2/15.
 */
public class LoginActivityTest
        extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity testLoginActivity;
    private TextView email;
    private TextView pswd;
    private Button signIn;
    private Button register;
    private Instrumentation testInstrumentation;
    private Instrumentation.ActivityMonitor monitor;
    private ListGroups testListGroups;
    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testLoginActivity = getActivity();
        email =
                (TextView) testLoginActivity
                        .findViewById(R.id.email);
        pswd =
                (TextView) testLoginActivity
                        .findViewById(R.id.password);
        signIn = (Button) testLoginActivity.findViewById(R.id.email_sign_in_button);
        register = (Button) testLoginActivity.findViewById(R.id.email_register_button);
    }

    @Override
    protected void tearDown() throws Exception {
        testLoginActivity.finish();
        super.tearDown();
    }

    public void testPreconditions() {
        assertNotNull("mFirstTestActivity is null", testLoginActivity);
        assertNotNull("email is null", email);
        assertNotNull("pswd is null", pswd);
        assertNotNull("signIn is null", signIn);
        assertNotNull("register is null", register);

    }
    /* given appropriate credentials
       when the login button is pressed
       then log me in and take me to the list
     */
    public void testSqamara() {
        // additional setup for sucessful login
        testInstrumentation = getInstrumentation();
        monitor = testInstrumentation.addMonitor(ListGroups.class.getName(), null, false);


        //Given correct email and
        email.setText("sqamara@ucsd.edu");
        pswd.setText("qqqqq");
        TouchUtils.clickView(this, signIn);
        assertTrue(testLoginActivity.userIndex >= 0);
        assertEquals(testLoginActivity.DUMMY_CREDENTIALS.get(testLoginActivity.userIndex),
                "sqamara@ucsd.edu:qqqqq");


        // additional teardown for successful login
        testListGroups = (ListGroups) testInstrumentation.waitForMonitor(monitor);
        testListGroups.finish();
        testInstrumentation.removeMonitor(monitor);
    }

    /* given a correct user name
       and an incorrect password
       when the login button is pressed
       then don't let me log in
       and inform me that the password is wrong
     */
    public void testSqamaraWrongPassword() {
        // given a correct user name and an incorrect password
        email.setText("sqamara@ucsd.edu");
        pswd.setText("zzzzz");
        //when the login button is pressed
        TouchUtils.clickView(this, signIn);
        // then don't let me log in and inform me that the password is wrong
        assertTrue(testLoginActivity.userIndex == -1);
        assertEquals("This password is incorrect", pswd.getError());

    }
    /* given a new credentials
       when the register button is pressed
       then save the credentials
     */
    public void testRegisterRandom() {
        // given a new credentials
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        email.setText(timeStamp + "@ucsd.edu");
        pswd.setText("zzzzz");
        // when the register button is pressed
        TouchUtils.clickView(this, register);
        // then save the credentials
        try {
            // give time to load and wait for server interaction
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        assertTrue(testLoginActivity.DUMMY_CREDENTIALS.contains(timeStamp + "@ucsd.edu:zzzzz"));
    }

    /* given an existing email
       and an arbitrary password
       when the register button is pressed
       then inform me that that email is already registered
     */
    public void testRegisterAlreadyRegistered() {
        // given an existing email and an arbitrary password
        email.setText("sqamara@ucsd.edu");
        pswd.setText("zzzzz");
        // when the register button is pressed
        TouchUtils.clickView(this, register);

        try {
            // needs to wait for server interaction
            Thread.sleep(1000);
        } catch (InterruptedException e) {}

        //then inform me that that email is already registered
        assertEquals("An account with this email already exists", pswd.getError());
        assertEquals("This email is already registered", email.getError());
    }

}
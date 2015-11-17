package team8.studysesh;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sebastian on 11/2/15.
 */
public class StudyGroupModel {

    public String owner = "";
    public String theClass = "";
    private DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
    public String location = "";
    public int cap = 0;
    public String description = "";
    public int members = 0;
    private Date theDate = new Date();

    StudyGroupModel(String inputOwner, String inputClass, String inputLocation, int inputCapacity, String inputDescription) {
        owner = inputOwner;
        theClass = inputClass;
        location = inputLocation;
        cap = inputCapacity;
        members = 1;
        description = inputDescription;
    }

    public String toString() {
        String toReturn = theClass + "\n" + "Started: "+ dateFormat.format(theDate).toString()
                + "\nLocation: " + location + "\nCapacity: " + members +"/" + cap;
        return toReturn;
    }

}

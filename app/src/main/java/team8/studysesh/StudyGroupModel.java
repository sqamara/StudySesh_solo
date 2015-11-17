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
    static DateFormat dateFormat;
    public String location = "";
    public int cap = 0;
    public String description = "";
    int members = 0;

    StudyGroupModel(String inputOwner, String inputClass, String inputLocation, int inputCapacity, String inputDescription) {
        owner = inputOwner;
        theClass = inputClass;
        dateFormat = new SimpleDateFormat("hh:mm a");
        location = inputLocation;
        cap = inputCapacity;
        members = 1;
        description = inputDescription;
    }

    public String toString() {
        String toReturn = theClass + "\n" + "Started: "+ dateFormat.format(new Date()).toString()
                + "\nLocation: " + location + "\nCapacity: " + members +"/" + cap;
        return toReturn;
    }

}

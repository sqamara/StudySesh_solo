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
    public String location = "";
    public int cap = 0;
    public String description = "";
    public int members = 0;
    public String time = ""; // time created
    public String id = "";

    public String startTime = "";
    public String endTime = "";

    StudyGroupModel(String inputOwner, String inputClass, String inputLocation, int inputCapacity,
                    String inputDescription, String inputTime, int numMembers) {
        owner = inputOwner;
        theClass = inputClass;
        location = inputLocation;
        cap = inputCapacity;
        members = numMembers;
        description = inputDescription;
        time = inputTime; // looks like  MM/dd/yyyy;HH:mm@MM/dd/yy;HH:mm
    }

    public String toString() {
        String[] startEnd = time.split("@");
        String[] start = startEnd[0].split(";");
        String[] end = startEnd[1].split(";");

        String toReturn = theClass + "<br>" + "Start: "+ start[0] + " " + start[1] + "<br>End: " +
                end[0] + " " + end[1] + "<br>Location: " + location + "<br>Capacity: "
                + members +"/" + cap;
        return toReturn;
    }
}

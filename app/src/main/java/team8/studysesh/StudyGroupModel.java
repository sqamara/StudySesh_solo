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
    DateFormat dateFormat;
    public String location = "";
    public int cap = 0;
    public String description = "";
    int members;

    StudyGroupModel(String name) {
        owner = "you";
        theClass = name;
        dateFormat = new SimpleDateFormat("hh:mm a");
        location = "Here";
        cap = 6;
        members = 1;
        description = "let's study " + theClass + "!";
    }

    public String toString() {
        String toReturn = theClass + "\n" + "Started: "+ dateFormat.format(new Date()).toString()
                + "\nLocation: " + location + "\nCapacity: " + members +"/" + cap;
        return toReturn;
    }

}

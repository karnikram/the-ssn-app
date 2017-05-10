package karnix.the.ssn.app.utils;

import java.util.HashMap;

/**
 * Created by Karnik on 10/05/2017.
 */

public class CategoryNames
{
    public static HashMap<String, String> notiTitle;

    static
    {
        notiTitle = new HashMap<String, String>();

        notiTitle.put("admin", "Administrative Block");
        notiTitle.put("busdept", "Bus Department");
        notiTitle.put("clubs", "Clubs");
        notiTitle.put("examcell", "Exam Cell");
        notiTitle.put("biomed", "BME Department");
        notiTitle.put("chem", "Chemical Engineering Department");
        notiTitle.put("civil", "Civil Engineering Department");
        notiTitle.put("cse", "CSE Department");
        notiTitle.put("ece", "ECE Department");
        notiTitle.put("eee", "EEE Department");
        notiTitle.put("human", "Humanities Block");
        notiTitle.put("it", "IT Department");
        notiTitle.put("mech", "Mechanical Engineering Department");

    }
}

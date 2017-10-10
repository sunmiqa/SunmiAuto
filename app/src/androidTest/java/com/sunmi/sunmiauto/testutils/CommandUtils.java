package com.sunmi.sunmiauto.testutils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fengy on 2017/9/14.
 */

public class CommandUtils {
    public static List<String> match(String s, String s1, String s2) {
        List<String> results = new ArrayList<String>();
        Pattern p = Pattern.compile(s1 + "(.*?)" + s2);
        Matcher m = p.matcher(s);
        while (!m.hitEnd() && m.find()) {
            results.add(m.group(1));
        }
        return results;
    }
}

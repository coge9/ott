package com.nbcuni.test.cms.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static boolean isMatch(final String testString, final String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(testString);
        return matcher.matches();
    }

    public static String getGroup(final String testString, final String patternString, final int groupNumber) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(testString);
        matcher.matches();
        return matcher.group(groupNumber);
    }

    public static String getFirstSubstringByRegex(String source, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            throw new RuntimeException("Matches was no found. Source: [" + source + "]. Regex [" + regex + "]");
        }
    }

    public static String getSubstringByRegex(String source, String regex, int groupNumber) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group(groupNumber);
        } else {
            throw new RuntimeException("Matches was no found. Source: [" + source + "]. Regex [" + regex + "]");
        }
    }

    public static int getFirstMatcherIndex(String source, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(source);
        if (m.find()) {
            return m.start();
        }
        throw new RuntimeException("Matches was no found. Source: [" + source + "]. Regex [" + regex + "]");
    }
}
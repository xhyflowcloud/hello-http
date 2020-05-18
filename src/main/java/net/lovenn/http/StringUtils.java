package net.lovenn.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static final Pattern BLANK = Pattern.compile("[\\S]+");

    public static boolean isBlank(String input) {
        if(input == null) return true;
        Matcher matcher = BLANK.matcher(input);
        if(matcher.find()) return false;
        return true;
    }


    public static void main(String[] args) {
        String blank = "              ";
        System.out.println(isBlank(blank));
    }
}

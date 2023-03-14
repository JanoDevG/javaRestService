package cl.janodevg.restService.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d).+$");

    public static boolean isValid(String email) {
        Matcher matcher = PASSWORD_REGEX.matcher(email);
        return matcher.matches();
    }

}

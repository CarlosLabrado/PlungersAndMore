package us.petrolog.plungersandmore.utils;

/**
 * Created by Vazh on 13/5/2016.
 */
public class Utils {

    /**
     * Encode user email to use it as a Firebase key (Firebase does not allow "." in the key name)
     * Encoded email is also used as "userEmail", list and item "owner" value
     */
    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}

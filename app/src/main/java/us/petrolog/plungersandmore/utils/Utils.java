package us.petrolog.plungersandmore.utils;

import us.petrolog.plungersandmore.model.SplitTime;

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

    /**
     * Splits seconds into hours, minutes, seconds
     *
     * @param seconds
     * @return split time
     */
    public static SplitTime splitTimeUtil(String seconds) {
        SplitTime splitTime = new SplitTime();
        try {
            long time = Long.parseLong(seconds);

            int hours = (int) time / 3600;
            int remainder = (int) time - hours * 3600;
            int mins = remainder / 60;
            remainder = remainder - mins * 60;
            int secs = remainder;

            splitTime.setHours(String.valueOf(hours));
            splitTime.setMinutes(String.valueOf(mins));
            splitTime.setSeconds(String.valueOf(secs));

            return splitTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return splitTime;
    }

    /**
     * Gets split time and unifies it
     *
     * @param splitTime split time
     * @return string with seconds
     */
    public static String unifyTime(SplitTime splitTime) {
        long finalSeconds;
        int hours;
        if (splitTime.getHours().equalsIgnoreCase("0") || splitTime.getHours().isEmpty()) {
            hours = 0;
        } else {
            hours = Integer.parseInt(splitTime.getHours()) * 3600;
        }
        int minutes;
        if (splitTime.getMinutes().equalsIgnoreCase("0") || splitTime.getMinutes().isEmpty()) {
            minutes = 0;
        } else {
            minutes = Integer.parseInt(splitTime.getMinutes()) * 60;
        }
        int seconds = Integer.parseInt(splitTime.getSeconds());

        finalSeconds = hours + minutes + seconds;

        return String.valueOf(finalSeconds);
    }
}

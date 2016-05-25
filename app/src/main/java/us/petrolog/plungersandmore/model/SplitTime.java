package us.petrolog.plungersandmore.model;

/**
 * Created by Vazh on 25/5/2016.
 */
public class SplitTime {
    String mHours;
    String mMinutes;
    String mSeconds;

    public SplitTime(String hours, String minutes, String seconds) {
        mHours = hours;
        mMinutes = minutes;
        mSeconds = seconds;
    }

    public SplitTime() {

    }

    public String getHours() {
        return mHours;
    }

    public void setHours(String hours) {
        mHours = hours;
    }

    public String getMinutes() {
        return mMinutes;
    }

    public void setMinutes(String minutes) {
        mMinutes = minutes;
    }

    public String getSeconds() {
        return mSeconds;
    }

    public void setSeconds(String seconds) {
        mSeconds = seconds;
    }
}

package us.petrolog.plungersandmore.model;

/**
 * Created by Vazh on 9/5/2016.
 */
public class Well {

    String mName;
    String mState;
    String mGroupName;
    double mLatitude;
    double mLongitude;
    boolean isActive;
    String mTimeZone;
    String userId;

    public Well() {
    }

    public Well(String name, String state, String groupName, double latitude, double longitude, boolean isActive, String timeZone, String userId) {
        mName = name;
        mState = state;
        mGroupName = groupName;
        mLatitude = latitude;
        mLongitude = longitude;
        this.isActive = isActive;
        mTimeZone = timeZone;
        this.userId = userId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String groupName) {
        mGroupName = groupName;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

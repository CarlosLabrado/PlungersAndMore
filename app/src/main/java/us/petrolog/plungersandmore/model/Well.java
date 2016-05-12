package us.petrolog.plungersandmore.model;

import java.util.List;

/**
 * Created by Vazh on 9/5/2016.
 */
public class Well {

    private String mName;
    private CurrentStatus mCurrentStatus;
    private List<Cycle> mCycles;
    private Location mLocation;
    private List<User> mUsers;

    public Well() {
    }

    public Well(String name, CurrentStatus currentStatus, List<Cycle> cycles, Location location, List<User> users) {
        mName = name;
        mCurrentStatus = currentStatus;
        mCycles = cycles;
        mLocation = location;
        mUsers = users;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public CurrentStatus getCurrentStatus() {
        return mCurrentStatus;
    }

    public void setCurrentStatus(CurrentStatus currentStatus) {
        mCurrentStatus = currentStatus;
    }

    public List<Cycle> getCycles() {
        return mCycles;
    }

    public void setCycles(List<Cycle> cycles) {
        mCycles = cycles;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public void setUsers(List<User> users) {
        mUsers = users;
    }
}

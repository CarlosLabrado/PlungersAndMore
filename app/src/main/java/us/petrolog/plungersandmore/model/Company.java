package us.petrolog.plungersandmore.model;

import java.util.List;

/**
 * Created by Vazh on 11/5/2016.
 */
public class Company {

    private String mName;
    private Location mLocation;
    private List<User> mUsers;

    public Company() {
    }

    public Company(String name, Location location, List<User> users) {
        mName = name;
        mLocation = location;
        mUsers = users;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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

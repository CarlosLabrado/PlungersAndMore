package us.petrolog.plungersandmore.model;

/**
 * Created by Vazh on 11/5/2016.
 */
public class Lease {
    private Location location;

    private String mName;

    public Lease() {
    }

    public Lease(Location location, String name) {
        this.location = location;
        mName = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}

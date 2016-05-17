package us.petrolog.plungersandmore.model;

/**
 * Created by Vazh on 11/5/2016.
 */
public class Company {

    private Location location;
    private String name;

    public Company() {
    }

    public Company(Location location, String name) {
        this.location = location;
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

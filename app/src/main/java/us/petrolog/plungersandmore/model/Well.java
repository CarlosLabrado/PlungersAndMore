package us.petrolog.plungersandmore.model;

/**
 * Created by Vazh on 9/5/2016.
 */
public class Well {

    private CurrentStatus currentStatus;
    private Location location;
    private String name;

    public Well() {
    }

    public Well(CurrentStatus currentStatus, Location location, String name) {
        this.currentStatus = currentStatus;
        this.location = location;
        this.name = name;
    }

    public CurrentStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(CurrentStatus currentStatus) {
        this.currentStatus = currentStatus;
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

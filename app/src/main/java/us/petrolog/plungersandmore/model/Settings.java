package us.petrolog.plungersandmore.model;

/**
 * Created by Vazh on 25/5/2016.
 */
public class Settings {
    private String closeValve;
    private String cp;
    private String fallTime;
    private String openTime;
    private String openValve;
    private String openValveWith;
    private String recoveryTime;
    private String salesTime;
    private String shutInTime;
    private String tp;

    public Settings() {
    }

    public Settings(String closeValve, String cp, String fallTime, String openTime, String openValve, String openValveWith, String recoveryTime, String salesTime, String shutInTime, String tp) {
        this.closeValve = closeValve;
        this.cp = cp;
        this.fallTime = fallTime;
        this.openTime = openTime;
        this.openValve = openValve;
        this.openValveWith = openValveWith;
        this.recoveryTime = recoveryTime;
        this.salesTime = salesTime;
        this.shutInTime = shutInTime;
        this.tp = tp;
    }

    public String getCloseValve() {
        return closeValve;
    }

    public void setCloseValve(String closeValve) {
        this.closeValve = closeValve;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getFallTime() {
        return fallTime;
    }

    public void setFallTime(String fallTime) {
        this.fallTime = fallTime;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getOpenValve() {
        return openValve;
    }

    public void setOpenValve(String openValve) {
        this.openValve = openValve;
    }

    public String getOpenValveWith() {
        return openValveWith;
    }

    public void setOpenValveWith(String openValveWith) {
        this.openValveWith = openValveWith;
    }

    public String getRecoveryTime() {
        return recoveryTime;
    }

    public void setRecoveryTime(String recoveryTime) {
        this.recoveryTime = recoveryTime;
    }

    public String getSalesTime() {
        return salesTime;
    }

    public void setSalesTime(String salesTime) {
        this.salesTime = salesTime;
    }

    public String getShutInTime() {
        return shutInTime;
    }

    public void setShutInTime(String shutInTime) {
        this.shutInTime = shutInTime;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }
}

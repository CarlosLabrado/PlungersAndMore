package us.petrolog.plungersandmore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Vazh on 11/5/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentStatus {
    private int cyclesCompleted;
    private int cyclesMissed;
    private int pc;
    private int pl;
    private int pt;
    private int state;
    private String timeStamp;

    public CurrentStatus() {
    }

    public CurrentStatus(int cyclesCompleted, int cyclesMissed, int pc, int pl, int pt, int state, String timeStamp) {
        this.cyclesCompleted = cyclesCompleted;
        this.cyclesMissed = cyclesMissed;
        this.pc = pc;
        this.pl = pl;
        this.pt = pt;
        this.state = state;
        this.timeStamp = timeStamp;
    }

    public int getCyclesCompleted() {
        return cyclesCompleted;
    }

    public void setCyclesCompleted(int cyclesCompleted) {
        this.cyclesCompleted = cyclesCompleted;
    }

    public int getCyclesMissed() {
        return cyclesMissed;
    }

    public void setCyclesMissed(int cyclesMissed) {
        this.cyclesMissed = cyclesMissed;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getPl() {
        return pl;
    }

    public void setPl(int pl) {
        this.pl = pl;
    }

    public int getPt() {
        return pt;
    }

    public void setPt(int pt) {
        this.pt = pt;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}





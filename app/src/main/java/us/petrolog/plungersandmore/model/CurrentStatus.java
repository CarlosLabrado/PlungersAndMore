package us.petrolog.plungersandmore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Vazh on 11/5/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentStatus {
    private int CyclesCompleted;
    private int CyclesMissed;
    private int Pc;
    private int Pl;
    private int Pt;
    private int State;
    private String timeStamp;

    public CurrentStatus() {
    }

    public CurrentStatus(int cyclesCompleted, int cyclesMissed, int pc, int pl, int pt, int state, String timeStamp) {
        CyclesCompleted = cyclesCompleted;
        CyclesMissed = cyclesMissed;
        Pc = pc;
        Pl = pl;
        Pt = pt;
        State = state;
        this.timeStamp = timeStamp;
    }

    public int getCyclesCompleted() {
        return CyclesCompleted;
    }

    public void setCyclesCompleted(int cyclesCompleted) {
        CyclesCompleted = cyclesCompleted;
    }

    public int getCyclesMissed() {
        return CyclesMissed;
    }

    public void setCyclesMissed(int cyclesMissed) {
        CyclesMissed = cyclesMissed;
    }

    public int getPc() {
        return Pc;
    }

    public void setPc(int pc) {
        Pc = pc;
    }

    public int getPl() {
        return Pl;
    }

    public void setPl(int pl) {
        Pl = pl;
    }

    public int getPt() {
        return Pt;
    }

    public void setPt(int pt) {
        Pt = pt;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}





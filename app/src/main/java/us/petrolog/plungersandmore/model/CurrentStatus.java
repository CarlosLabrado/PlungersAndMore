package us.petrolog.plungersandmore.model;


/**
 * Created by Vazh on 11/5/2016.
 */
public class CurrentStatus {
    private String cyclesCompleted;
    private String cyclesMissed;
    private String pc;
    private String pl;
    private String pt;
    private String state;
    private String timeStamp;

    public CurrentStatus() {
    }

    public CurrentStatus(String cyclesCompleted, String cyclesMissed, String pc, String pl, String pt, String state, String timeStamp) {
        this.cyclesCompleted = cyclesCompleted;
        this.cyclesMissed = cyclesMissed;
        this.pc = pc;
        this.pl = pl;
        this.pt = pt;
        this.state = state;
        this.timeStamp = timeStamp;
    }

    public String getCyclesCompleted() {
        return cyclesCompleted;
    }

    public void setCyclesCompleted(String cyclesCompleted) {
        this.cyclesCompleted = cyclesCompleted;
    }

    public String getCyclesMissed() {
        return cyclesMissed;
    }

    public void setCyclesMissed(String cyclesMissed) {
        this.cyclesMissed = cyclesMissed;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getPl() {
        return pl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}





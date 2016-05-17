package us.petrolog.plungersandmore.model;

/**
 * Created by Vazh on 11/5/2016.
 */
public class Cycle {
    private Open mOpen;
    private ShutIn mShutIn;
    private long mTimeStamp;


    public Cycle() {
    }

    public Cycle(Open open, ShutIn shutIn, long timeStamp) {
        mOpen = open;
        mShutIn = shutIn;
        mTimeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        mTimeStamp = timeStamp;
    }

    public Open getOpen() {
        return mOpen;
    }

    public void setOpen(Open open) {
        mOpen = open;
    }

    public ShutIn getShutIn() {
        return mShutIn;
    }

    public void setShutIn(ShutIn shutIn) {
        mShutIn = shutIn;
    }
}

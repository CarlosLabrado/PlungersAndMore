package us.petrolog.plungersandmore.model;

/**
 * Created by Vazh on 11/5/2016.
 */
public class Open {
    private int mCasingPressure;
    private int mLinePressure;
    private int mTubingPressure;
    private long mTimeStamp;

    public Open() {
    }

    public Open(int casingPressure, int linePressure, int tubingPressure, long timeStamp) {
        mCasingPressure = casingPressure;
        mLinePressure = linePressure;
        mTubingPressure = tubingPressure;
        mTimeStamp = timeStamp;
    }

    public int getCasingPressure() {
        return mCasingPressure;
    }

    public void setCasingPressure(int casingPressure) {
        mCasingPressure = casingPressure;
    }

    public int getLinePressure() {
        return mLinePressure;
    }

    public void setLinePressure(int linePressure) {
        mLinePressure = linePressure;
    }

    public int getTubingPressure() {
        return mTubingPressure;
    }

    public void setTubingPressure(int tubingPressure) {
        mTubingPressure = tubingPressure;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        mTimeStamp = timeStamp;
    }
}

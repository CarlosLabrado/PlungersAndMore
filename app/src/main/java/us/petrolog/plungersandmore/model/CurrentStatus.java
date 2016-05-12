package us.petrolog.plungersandmore.model;

/**
 * Created by Vazh on 11/5/2016.
 */
public class CurrentStatus {
    private int mCasingPressure;
    private int mCyclesCompleted;
    private int mCyclesMissed;
    private int mLinePressure;
    private int mTubingPressure;

    public CurrentStatus() {
    }

    public CurrentStatus(int casingPressure, int cyclesCompleted, int cyclesMissed, int linePressure, int tubingPressure) {
        mCasingPressure = casingPressure;
        mCyclesCompleted = cyclesCompleted;
        mCyclesMissed = cyclesMissed;
        mLinePressure = linePressure;
        mTubingPressure = tubingPressure;
    }

    public int getCasingPressure() {
        return mCasingPressure;
    }

    public void setCasingPressure(int casingPressure) {
        mCasingPressure = casingPressure;
    }

    public int getCyclesCompleted() {
        return mCyclesCompleted;
    }

    public void setCyclesCompleted(int cyclesCompleted) {
        mCyclesCompleted = cyclesCompleted;
    }

    public int getCyclesMissed() {
        return mCyclesMissed;
    }

    public void setCyclesMissed(int cyclesMissed) {
        mCyclesMissed = cyclesMissed;
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
}



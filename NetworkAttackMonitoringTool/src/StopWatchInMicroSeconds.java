/**
 *  Sarah Virr
 *  101146506
 *  Jawad Kadri
 *  101147056
 *  Last modified: December 5th, 2021
 *
 */

public class StopWatchInMicroSeconds {

    private long elapsedTime, startTime;
    private boolean isRunning;

    public StopWatchInMicroSeconds() {reset();}

    public void reset()
    { isRunning = false;
        elapsedTime = 0;
    }

    public void start() {
        if (isRunning) return;
        isRunning = true;
        startTime = System.nanoTime();
    }

    public void stop() {
        if (!isRunning) return;
        isRunning = false;
        long endTime = System.nanoTime();
        elapsedTime = elapsedTime + endTime - startTime;
    }

    public long getElapsedTime() {
        if (isRunning) {
            long endTime = System.nanoTime();
            return elapsedTime + endTime - startTime;
        }
        else return elapsedTime;
    }
}

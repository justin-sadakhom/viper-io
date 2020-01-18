package snake.evolution;

import javafx.animation.AnimationTimer;

/*
// Game clock used to keep track of elapsed time.
*/
public abstract class Clock extends AnimationTimer {
    
    // Delay, in nanoseconds.
    private long delayNS = 0;

    // Reference time.
    private long prevTime = 0;
    
    /*
    // Default constructor.
    //
    // @param delayMS: delay time, in milliseconds
    */
    public Clock(long delayMS) {
        
        // Convert to nanoseconds.
        this.delayNS = delayMS * 1000000;
    }
    
    /*
    // Prevents no-arg handle() from being
    // invoked until delay time has elapsed.
    //
    // @param now: current time
    */
    @Override
    public void handle(long now) {

        // If time elapsed is less than sleep time, stops method.
        if (now - prevTime < delayNS)
            return;

        // Method continues and sets reference time equal to current time.
        prevTime = now;

        handle();
    }

    public abstract void handle();
}
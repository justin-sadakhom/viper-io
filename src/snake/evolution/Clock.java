/*
// Author: Justin Sadakhom
// Date: June 11, 2019
// File: Clock.java
*/

package snake.evolution;

import javafx.animation.AnimationTimer;

/*
// Game clock used to keep track of time.
// Subclass of Java FX's AnimationTimer.
*/
public abstract class Clock extends AnimationTimer {

    /* FIELDS */
    
    // Delay, in nanoseconds.
    private long delayNS = 0;

    // Reference time.
    private long prevTime = 0;

    /* CONSTRUCTOR */
    
    /*
    // Default constructor.
    //
    // @param delayMS - Delay time (in milliseconds)
    */
    public Clock(long delayMS) {
        
        // Delay time converted to nanoseconds.
        this.delayNS = delayMS * 1000000;
    }

    /* METHODS */
    
    /*
    // Method that prevents handle() method
    // from being invoked until a certain time
    // delay has elapsed.
    */
    
    /*
    // Overloaded handle() method.
    // Prevents no-arg handle() from being
    // invoked until delay time has elapsed.
    //
    // @param now - Current time.
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

    /*
    // Abstract method for Game class to override.
    */
    public abstract void handle();
}
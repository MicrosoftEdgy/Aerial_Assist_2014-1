package org.firstrobotics1923.event;

import org.firstrobotics1923.Components;

/**
 * Event that stops the shooter
 * 
 * @author Pavan Hegde
 * @version 1.0
 * @since Jan 26, 2014
 */
public class ShooterStopEvent extends Event{
    
    /**
     * Runs one time only
     */
    public ShooterStopEvent() {
        super(true);
    }
    
    /**
     * Stops the motors on the shooter
     */
    public void event() {
        Components.shooterSystem.stop();
    }
}
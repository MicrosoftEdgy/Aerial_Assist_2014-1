package org.firstrobotics1923.event;

import org.firstrobotics1923.Components;

/**
 * An event that starts up the Shooter's wheels
 * 
 * @author Pavan Hegde
 * @version 1.0
 * @since Jan 26, 2014
 */
public class ShooterStartEvent extends Event {
    
    /**
     * Runs only once
     */
    public ShooterStartEvent(){
        super(true);
    }
    
    /**
     * Starts up the shooter motors
     */
    public void event() {
        Components.shooterSystem.activate();
    }
}
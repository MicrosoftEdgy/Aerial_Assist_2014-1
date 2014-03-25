package org.firstrobotics1923.event;

import org.firstrobotics1923.Components;

/**
 * Event that extends the pistons on the Intake, angling the bar out
 * 
 * @author Pavan Hegde
 * @version 1.0
 * @since Jan 26, 2014
 */
public class IntakeAngleOutEvent extends Event{
    
    /**
     * Runs only once
     */
    public IntakeAngleOutEvent() {
        super(true);
    }
    
    /**
     * Extends the pistons connected to the intake system
     */
    public void event() {
        Components.intakeSystem.activate();
    }
}
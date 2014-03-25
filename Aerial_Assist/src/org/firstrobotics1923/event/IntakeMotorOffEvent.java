package org.firstrobotics1923.event;

import org.firstrobotics1923.Components;

/**
 * Event that turns off the Intake motor
 * 
 * @author Pavan Hegde
 * @version 1.0
 * @since Jan 26, 2014
 */
public class IntakeMotorOffEvent extends Event{
    
    /**
     * Runs only once
     */
    public IntakeMotorOffEvent() {
        super(true);
    }
    
    /**
     * Turns off the intake wheels
     */
    public void event() {
        Components.intakeSystem.deactivateMotor();
    }
    
}
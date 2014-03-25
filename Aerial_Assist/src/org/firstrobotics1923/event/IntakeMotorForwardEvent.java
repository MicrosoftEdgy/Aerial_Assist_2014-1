package org.firstrobotics1923.event;

import org.firstrobotics1923.Components;

/**
 * Event that turns on the Intake motors
 * 
 * @author Pavan Hegde
 * @version 1.0
 * @since Jan 26, 2014
 */
public class IntakeMotorForwardEvent extends Event{
    
    /**
     * Runs only once
     */
    public IntakeMotorForwardEvent() {
        super(true);
    }
    
    /**
     * Turns on the motor on the intake system
     */
    public void event() {
        Components.intakeSystem.forwardMotor();
    }
}
package org.firstrobotics1923.event;

import edu.wpi.first.wpilibj.Relay;
import org.firstrobotics1923.Components;

/**
 * Event that turns on the Intake motors
 * 
 * @author Pavan Hegde
 * @version 1.0
 * @since Jan 26, 2014
 */
public class IntakeMotorOnEvent extends Event{
    
    /**
     * Runs only once
     */
    public IntakeMotorOnEvent() {
        super(true);
    }
    
    /**
     * Turns on the motor on the intake system
     */
    public void event() {
        Components.intakeSystem.activateMotor();
    }
}

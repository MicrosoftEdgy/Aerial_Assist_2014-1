package org.firstrobotics1923.event;

import org.firstrobotics1923.Components;

/**
 *
 * @author FRC1923
 */
public class IntakeMotorReverseEvent extends Event{
    
    public IntakeMotorReverseEvent() {
        super(true);
    }
    
    protected void event() {
        Components.intakeSystem.reverseMotor();
    }
}
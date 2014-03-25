package org.firstrobotics1923.event;

import org.firstrobotics1923.Components;

/**
 * Event that retracts pistons connected to the intake, thus retracting the system
 * 
 * @author Pavan Hegde
 * @version 1.0
 * @since Jan 26, 2014
 */
public class IntakeAngleInEvent extends Event{
    
    public IntakeAngleInEvent() {
        super(true);
    }
    
    public void event() {
        Components.intakeSystem.deactivate();
    }
}
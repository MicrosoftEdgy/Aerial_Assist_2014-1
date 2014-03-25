package org.firstrobotics1923.event;

import org.firstrobotics1923.Components;

/**
 * An event that deactivates the shooter angle system
 * 
 * @author Kartik Vaidya, Prasanth Yedlapalli, Saikiran Nakka, Hamza Mustafa
 * @version 1.0
 * @since Jan 24, 2014
 */
public class ShooterLowerAngleEvent extends Event {
    
public ShooterLowerAngleEvent(){
        super(true);
    }
    
    /**
     * Constructor that deactivates shooterAngleSystem
     */
    public void event() {
      Components.shooterAngleSystem.deactivate();
    }
}
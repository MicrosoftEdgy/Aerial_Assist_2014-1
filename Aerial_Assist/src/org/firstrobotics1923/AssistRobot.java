package org.firstrobotics1923;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.firstrobotics1923.event.CompressorOffEvent;
import org.firstrobotics1923.event.CompressorOnEvent;
import org.firstrobotics1923.event.IntakeAngleInEvent;
import org.firstrobotics1923.event.IntakeAngleOutEvent;
import org.firstrobotics1923.event.IntakeMotorOffEvent;
import org.firstrobotics1923.event.IntakeMotorOnEvent;
import org.firstrobotics1923.event.ShooterLowerAngleEvent;
import org.firstrobotics1923.event.ShooterRaiseAngleEvent;
import org.firstrobotics1923.event.ShooterSpeedDownEvent;
import org.firstrobotics1923.event.ShooterSpeedUpEvent;
import org.firstrobotics1923.event.ShooterStartEvent;
import org.firstrobotics1923.event.ShooterStopEvent;
import org.firstrobotics1923.util.XboxController;

/**
 * The Core Code for FRC Team 1923's "Aerial Assist" Robot 
 * 
 * @author Pavan Hegde
 * @version 1.0
 * @since Jan. 26, 2014
 */
public class AssistRobot extends IterativeRobot{
    
    private XboxController xbc = Components.operatorControl;
    
    private boolean[] justPressed = new boolean[14];       //Array to store Xbox button input
    private boolean[] triggers = new boolean[2]; //Array to store Xbox trigger input
        
    private boolean compressorOn = false;
    
    public void robotInit(){
        //Components.rightDriveEncoder.setDistancePerPulse(256); //TODO: update
        //Components.leftDriveEncoder.setDistancePerPulse(256); //TODO: update
    }
    
    /**
     * Ensures that all systems are disabled
     */
    public void disabledInit() {
        Components.shooterSystem.stop();
        //Components.shooterAngleSystem.stop();
        //Components.robotDrive.stop();
        //Components.intakeSystem.stop();
       // EventBus.instance.clear();
    }
    
    public void disabledPeriodic() {
        //... ... ... ... ... Yes ... ... ...
    }
    
    /**
     * Called once at the start of Auton
     */
    public void autonomousInit() {
        
    }
    
    /**
     * Called periodically in auton
     */
    public void autonomousPeriodic(){
        
    }
    
    /**
     * Initializes required things before teleop
     */
    public void teleopInit() {
       //Not'in' yet
    }
    
    /**
     * All of the periodically called teleop-functions (eg. input)
     */
    public void teleopPeriodic() {
        
        { //Driving Scope
           Components.robotDrive.drive(Components.leftStick.getCoalescedY(), Components.rightStick.getCoalescedY());
           System.out.println("Left: " + Components.leftStick.getCoalescedY() + " Right: " + Components.rightStick.getCoalescedY());
        } //End Driving Scope
        
        {  //Shooter Scope
           if (Components.operatorControl.getButton(XboxController.Button.Start) & !justPressed[XboxController.Button.Start.value]) {    //Start button turns on shooter
               EventBus.instance.push(new ShooterStartEvent());
               justPressed[XboxController.Button.Start.value] = true;
           } else {
               justPressed[XboxController.Button.Start.value] = false;
           }
           
           if (Components.operatorControl.getButton(XboxController.Button.Back) & !justPressed[XboxController.Button.Back.value]) {     //Back Button stops the shooter
               EventBus.instance.push(new ShooterStopEvent());
               justPressed[XboxController.Button.Back.value] = false;
           } else {
               justPressed[XboxController.Button.Back.value] = false;
           }
           
           if (Components.operatorControl.getButton(XboxController.Button.LB) & !justPressed[XboxController.Button.LB.value]) {         //Left Bumper Slows down the shooter
               EventBus.instance.push(new ShooterSpeedDownEvent());
               justPressed[XboxController.Button.LB.value] = true;
           } else {
               justPressed[XboxController.Button.LB.value] = false;
           }
           
           if (Components.operatorControl.getButton(XboxController.Button.RB) & !justPressed[XboxController.Button.RB.value]) {         //Right Bumper speeds up the shooter
               EventBus.instance.push(new ShooterSpeedUpEvent());
               justPressed[XboxController.Button.RB.value] = true;
           } else {
               justPressed[XboxController.Button.RB.value] = false;
           }
        } //End Shooter Scope
        
        { // Shooter Angle Scope
           if (Components.operatorControl.getTrigger(-1) & !triggers[0]) {         //Left Trigger lowers the shooter angle
               EventBus.instance.push(new ShooterLowerAngleEvent());
               triggers[0] = true;
           } else {
               triggers[0] = false;
           } 
           
           if (Components.operatorControl.getTrigger(1) & !triggers[1]) {         //Right Trigger raises the shooter angle
               EventBus.instance.push(new ShooterRaiseAngleEvent());
               triggers[1] = true;
           } else {
               triggers[1] = false;
           }
        } // End Shooter Angle Scope
        
        { //Intake Scope
           if (Components.operatorControl.getButton(XboxController.Button.B) & !justPressed[XboxController.Button.B.value]) {         //B angles intake in
               EventBus.instance.push(new IntakeAngleInEvent());
               justPressed[XboxController.Button.B.value] = true;
           } else {
               justPressed[XboxController.Button.B.value] = false;
           }
           
           if (Components.operatorControl.getButton(XboxController.Button.X) & !justPressed[XboxController.Button.X.value]) {         //X angles intake out
               EventBus.instance.push(new IntakeAngleOutEvent());
               justPressed[XboxController.Button.X.value] = true;
           } else {
               justPressed[XboxController.Button.X.value] = false;
           }
           
           if (Components.operatorControl.getButton(XboxController.Button.A) & !justPressed[XboxController.Button.A.value]) {         //A turns on the intake motor
               EventBus.instance.push(new IntakeMotorOnEvent());
               justPressed[XboxController.Button.A.value] = true;
           } else {
               justPressed[XboxController.Button.A.value] = false;
           }
           
           if (Components.operatorControl.getButton(XboxController.Button.Y) & !justPressed[XboxController.Button.Y.value]) {         //Y turns off the intake motor
               EventBus.instance.push(new IntakeMotorOffEvent());
               justPressed[XboxController.Button.Y.value] = true;
           } else {
               justPressed[XboxController.Button.Y.value] = false;
           }
        } //End Intake Scope
        
        { //Compressor Scope
            if (!Components.compressorSafety.get() && !compressorOn) {
                EventBus.instance.push(new CompressorOnEvent());
                compressorOn = true;
            }else {
                EventBus.instance.push(new CompressorOffEvent());
                compressorOn = false;
            }
        } //End Compressor Scope
  
        { //Event Bus Scope
            EventBus.instance.next();
            EventBus.instance.clean();
        } //End EvntBus Scope
    }
}

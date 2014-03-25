package org.firstrobotics1923;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.firstrobotics1923.event.CompressorOffEvent;
import org.firstrobotics1923.event.CompressorOnEvent;
import org.firstrobotics1923.event.IntakeAngleInEvent;
import org.firstrobotics1923.event.IntakeAngleOutEvent;
import org.firstrobotics1923.event.IntakeMotorForwardEvent;
import org.firstrobotics1923.event.IntakeMotorOffEvent;
import org.firstrobotics1923.event.IntakeMotorReverseEvent;
import org.firstrobotics1923.event.ShooterLowerAngleEvent;
import org.firstrobotics1923.event.ShooterRaiseAngleEvent;
import org.firstrobotics1923.event.ShooterStartEvent;
import org.firstrobotics1923.event.ShooterStopEvent;
import org.firstrobotics1923.routines.ButylRoutine;
import org.firstrobotics1923.routines.EthylRoutine;
import org.firstrobotics1923.routines.MethylRoutine;
//import org.firstrobotics1923.util.Dashboard;
import org.firstrobotics1923.util.XboxController;
//import org.firstrobotics1923.Components;

/**
 * The Core Code for FRC Team 1923's "Aerial Assist" Robot 
 * 
 * @author Pavan Hegde & whoever else touched this without commenting
 * @version 1.3
 * @since Mar. 11, 2014
 */
public class AssistRobot extends IterativeRobot{ 
    
    private XboxController xbc = Components.operatorControl;
    
    private boolean[] justPressed = new boolean[14];       //Array to store Xbox button input
    private boolean[] triggers = new boolean[2]; //Array to store Xbox trigger input
    
    private double shooterReady = 2.5;
    
    private String currentAutonRoutine = "butyl";

    private boolean compressorOn = false;
    
    private NetworkTable table;
    
    public void robotInit(){
        this.table = Components.table;
        this.compressorOn = false;
        Components.shooterAngleSystem.stop();
        Components.intakeSystem.stop();
       // Components.sfxDashboard.initSDVariables();
      //  Components.sfxDashboard.smartDashboardUpdate();
    }
    
    
    /**
     * Ensures that all systems are disabled
     */
    public void disabledInit() {
        Components.shooterSystem.stop();
        Components.shooterAngleSystem.stop();
        Components.robotDrive.stop();
        Components.intakeSystem.stop();
        EventBus.instance.clear();
    }
    
    public void disabledPeriodic() {
        //... ... ... ... ... Yes ... ... ...
    }

    /**
     * Called once at the start of Auton
     */
    public void autonomousInit() {
      //  Components.sfxDashboard.smartDashboardUpdate();
        if (currentAutonRoutine.equalsIgnoreCase("methyl")){ //UNUSABLE!!!!! ROBOREALM NO LONGER PASSES TARGET DISTANCE
            MethylRoutine.start();
        } else if (currentAutonRoutine.equalsIgnoreCase("ethyl")){
            EthylRoutine.start();
        } else if (currentAutonRoutine.equalsIgnoreCase("butyl")) {
            ButylRoutine.initialize();
        }
    }
    
    /**
     * Called periodically in auton
     */
    public void autonomousPeriodic(){                
        if (currentAutonRoutine.equalsIgnoreCase("methyl")){ //UNUSABLE!!!! ROBOREALM NO LONGER PASSED TARGET DISTANCE
            MethylRoutine.routine();
        } else if (currentAutonRoutine.equalsIgnoreCase("ethyl")){
            EthylRoutine.routine();
        } else if (currentAutonRoutine.equalsIgnoreCase("butyl")) {
            ButylRoutine.routine();
        }
   // Components.sfxDashboard.smartDashboardUpdate();
    }
    
    /**
     * Initializes required things before teleop
     */
    public void teleopInit() {
        if (currentAutonRoutine.equals("methyl")){     //DO NOT USE METHYL!!!! NO MORE TARGET DISTANCE... No point in it
            MethylRoutine.stop();
        } else if (currentAutonRoutine.equalsIgnoreCase("ethyl")){
            EthylRoutine.stop();
        } else if (currentAutonRoutine.equalsIgnoreCase("butyl")) {
            ButylRoutine.stop();
        }
      //  Components.sfxDashboard.smartDashboardUpdate();
        if (Components.sfxDashboard.useDashboardVar){
            shooterReady = Components.sfxDashboard.var_1Value;
        }
        
        EventBus.instance.clear();        
    }
    
    /**
     * All of the periodically called teleop-functions (eg. input)
     */
    public void teleopPeriodic() {
        { //Driving Scope : Currently Full Joystick Control, no correction
            Components.robotDrive.drive(Components.leftStick.getCoalescedY(), Components.rightStick.getCoalescedY());
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
           
//           if (Components.shooterSystem.getShootTime() >= shooterReady) {
//               Components.sfxDashboard.CompressorRelay = true;
//           } else {
//               Components.sfxDashboard.CompressorRelay = false;
//           }
        } //End Shooter Scope
        
        { // Shooter Angle Scope
           if ((Components.operatorControl.getRawAxis(3) < 0) && !triggers[0]) {         //Left Trigger lowers the shooter angle
               EventBus.instance.push(new ShooterLowerAngleEvent());
               triggers[0] = true;
           } else {
               triggers[0] = false;
           } 
           
           if ((Components.operatorControl.getRawAxis(3) > 0) && !triggers[1]) {         //Right Trigger raises the shooter angle
               EventBus.instance.push(new ShooterRaiseAngleEvent());
               System.out.println("RT pressed");
               triggers[1] = true;
           } else {
               triggers[1] = false;
            }  
        } // End Shooter Angle Scope
         
        { //Intake Scope 
           if (Components.operatorControl.getButton(XboxController.Button.B) & !justPressed[XboxController.Button.B.value]) {         //B angles intake in
               EventBus.instance.push(new IntakeAngleInEvent());
               justPressed[XboxController.Button.B.value] = true;
               Components.shooterSystem.resetTimer();
           } else {
               justPressed[XboxController.Button.B.value] = false;
           }
           
           if (Components.operatorControl.getButton(XboxController.Button.X) & !justPressed[XboxController.Button.X.value]) {         //X angles intake out
               EventBus.instance.push(new IntakeAngleOutEvent());
               justPressed[XboxController.Button.X.value] = true;
           } else {
               justPressed[XboxController.Button.X.value] = false;
           }
           
           if (Components.operatorControl.getButton(XboxController.Button.Y) & !justPressed[XboxController.Button.Y.value]) {         //Y turns off the intake motor
               EventBus.instance.push(new IntakeMotorOffEvent());
               justPressed[XboxController.Button.Y.value] = true;
           } else {
               justPressed[XboxController.Button.Y.value] = false;
           }
           
           if (Components.operatorControl.getButton(XboxController.Button.LB) & !justPressed[XboxController.Button.LB.value]) {         //Left Bumper starts the intake in reverse
               EventBus.instance.push(new IntakeMotorForwardEvent());
               justPressed[XboxController.Button.LB.value] = true;
           } else {
               justPressed[XboxController.Button.LB.value] = false;
           }
           
           if (Components.operatorControl.getButton(XboxController.Button.RB) & !justPressed[XboxController.Button.RB.value]) {         //Right Bumper starts the intake forward
               EventBus.instance.push(new IntakeMotorReverseEvent());
               justPressed[XboxController.Button.RB.value] = true;
           } else {
               justPressed[XboxController.Button.RB.value] = false;
           }           
        } //End Intake Scope
        
        { //Compressor Scope
            boolean safety = Components.compressorSafety.get();
            if (!safety && !compressorOn) {
                EventBus.instance.push(new CompressorOnEvent());
                compressorOn = true;
            }else if (safety && compressorOn) {
                EventBus.instance.push(new CompressorOffEvent());
                compressorOn = false;
            }
        } //End Compressor Scope
  
        { //Event Bus Scope
            EventBus.instance.next();
            EventBus.instance.clean();
        } //End EvntBus Scope
        { //SFX Dashboard Scope
           //sfxDashboard.smartDashboardUpdate();
           // Components.sfxDashboard.smartDashboardUpdate();
           
        } // SFX End Dashboard Scope
        //{ //Dashboard Scope
           // Dashboard.update();
        //} //End Dashboard Scope
    }
}

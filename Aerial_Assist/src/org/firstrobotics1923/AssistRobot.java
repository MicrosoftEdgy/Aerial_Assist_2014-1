package org.firstrobotics1923;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
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
import org.firstrobotics1923.routines.AutonomousRoutine;
import org.firstrobotics1923.routines.MethylRoutine;
import org.firstrobotics1923.routines.MethylRoutine2;
import org.firstrobotics1923.util.XboxController;
/**
 * The Core Code for FRC Team 1923's "Aerial Assist" Robot 
 * 
 * @author Pavan Hegde & whoever did the dashboard stuff that is commented out
 * @version 1.0
 * @since Feb. 15, 2014
 */
public class AssistRobot extends IterativeRobot{
    private XboxController xbc = Components.operatorControl;
    
    private boolean[] justPressed = new boolean[14];       //Array to store Xbox button input
    private boolean[] triggers = new boolean[2]; //Array to store Xbox trigger input
        
    private AutonomousRoutine auton;

    private boolean compressorOn = false;
    
    private NetworkTable table;
    
    public void robotInit(){
        this.table = Components.table;
  //      Components.rightDriveEncoder.setDistancePerPulse(1); //TODO: update
//        Components.leftDriveEncoder.setDistancePerPulse(1); //TODO: update
        this.compressorOn = false;
        Components.shooterAngleSystem.stop();
        Components.intakeSystem.stop();
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
        MethylRoutine2.start();
        //auton = new MethylRoutine();
        //auton.run();
    }
    
    /**
     * Called periodically in auton
     */
    public void autonomousPeriodic(){
        MethylRoutine2.doEvent();
       // EventBus.instance.clean();
       // EventBus.instance.next();
    }
    
    /**
     * Initializes required things before teleop
     */
    public void teleopInit() {
        MethylRoutine2.stop();
        EventBus.instance.clear();        
    }
    
    /**
     * All of the periodically called teleop-functions (eg. input)
     */
    public void teleopPeriodic() {
        { //Driving Scope
                    System.out.println("Hot Target " + table.getString("Hot_Target"));
                    System.out.println("Distance: " + table.getNumber("TARGET_DISTANCE"));

            double idealLeft = Components.leftStick.getCoalescedY();
            double idealRight = Components.rightStick.getCoalescedY();

 //           System.out.println("Encoder Rate L, R: " + Components.leftDriveEncoder.getRate() + ", " + Components.rightDriveEncoder.getRate());
 //           double realLeft = Components.leftDriveEncoder.getRate() / 1024; //TODO Update Values to Rate at 1.0
 //           double realRight = Components.rightDriveEncoder.getRate() / 1024; //TODO Update Values to Rate at 1.0
            
  //          double errorLeft = realLeft - idealLeft;
  //          double errorRight = realRight - idealRight;

            double newLeft = idealLeft;// - errorLeft;
            double newRight = idealRight;// - errorRight;

            double maxNewLeftRight = Math.max(1, Math.max(Math.abs(newLeft), Math.abs(newRight)));
            
            newLeft = newLeft / maxNewLeftRight;
            newRight = newRight / maxNewLeftRight;            
            
           Components.robotDrive.drive(newLeft, newRight);
        } //End Driving Scope
        
        {//Targeting Scope
            //System.out.println("Distance: " + table.getNumber("/SmartDashboard/Distance"));
        } //End Targeting Scope
        
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
        } //End Shooter Scope
        
        { // Shooter Angle Scope
           //System.out.println("Triggers: " + Components.operatorControl.getRawAxis(3)); //TODO DEBUG
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
        /*if (Components.operatorControl.getButton(XboxController.Button.A)) {         //B angles intake in
              // EventBus.instance.push(new CompressorOnEvent());
             
               System.out.println("A button pressed and just pressed: ");
               Components.compressorSpike.set(Relay.Value.kForward);
               System.out.println("Relay Value: " + Components.compressorSpike.get().value);
               
       } else {
               Components.compressorSpike.set(Relay.Value.kOff);
               System.out.println("Relay Value: " + Components.compressorSpike.get().value);
        }*/
        { //Compressor Scope
            boolean safe = Components.compressorSafety.get();
            System.out.println("Safety: " + safe);
            if (!Components.compressorSafety.get() && !compressorOn) {
                EventBus.instance.push(new CompressorOnEvent());
                System.out.println("In here at least once");
                System.out.println("IF1: " + safe);
                compressorOn = true;
            }else if (Components.compressorSafety.get() && compressorOn) {
                EventBus.instance.push(new CompressorOffEvent());
                System.out.println("In other here at least once");
                System.out.println("IF2: " + safe);
                compressorOn = false;
            }
        } //End Compressor Scope
  
        { //Event Bus Scope
            EventBus.instance.next();
            EventBus.instance.clean();
        } //End EvntBus Scope

        //updateDashboard();
    }
    
    /*public void updateDashboard() {
        Dashboard lowDashData = DriverStation.getInstance().getDashboardPackerLow();
        lowDashData.addCluster();
        {
            lowDashData.addCluster();
            {     //analog modules
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(2).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
            }
            lowDashData.finalizeCluster();

            lowDashData.addCluster();
            { //digital modules
                lowDashData.addCluster();
                {
                    lowDashData.addCluster();
                    {
                        int module = 1;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

                lowDashData.addCluster();
                {
                    lowDashData.addCluster();
                    {
                        int module = 2;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayReverse());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

            }
            lowDashData.finalizeCluster();

            lowDashData.addByte(Solenoid.getAllFromDefaultModule());
        }
        lowDashData.finalizeCluster(); 
        lowDashData.commit();
    }*/
}


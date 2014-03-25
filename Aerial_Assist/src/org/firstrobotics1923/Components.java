package org.firstrobotics1923;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.firstrobotics1923.system.DriveSystem;
import org.firstrobotics1923.system.IntakeSystem;
import org.firstrobotics1923.system.ShooterAngleSystem;
import org.firstrobotics1923.system.ShooterSystem;
import org.firstrobotics1923.util.MotorGroup;
import org.firstrobotics1923.util.SmartDashboardInterface;
import org.firstrobotics1923.util.StickShift;
import org.firstrobotics1923.util.XboxController;

/**
 * All Robotic components used in the code compiled in one place
 * 
 * @author Pavan Hegde, Prasanth Yedlapalli, Aryak Pande
 * @version 1.5
 * @since Jan. 13, 2014
 */
public class Components {
    /* Joysticks and Xbox controller */
    public static final StickShift leftStick = new StickShift(1);         // Left Joystick in port 1
    public static final StickShift rightStick = new StickShift(2);        //Right Joystick in port 2
    public static final XboxController operatorControl = new XboxController(3); //Xbox Controller in 3
    
    /* Vision */
    public static NetworkTable table = NetworkTable.getTable("SmartDashboard");
    // SmartDashboard Interface
    public static SmartDashboardInterface sfxDashboard = new SmartDashboardInterface(table);
     
    /* Relays (Spikes)*/
    public static final Relay compressorSpike = new Relay(5);
    public static final Victor intakeVictor = new Victor(10); 
    
    /* Sensors (eg Encoders)*/
    public static final DigitalInput compressorSafety = new DigitalInput(7);
    
    /* Speed controllers */
    public static final Victor frontLeftDrive = new Victor(9);  
    public static final Victor rearLeftDrive = new Victor(1);    
    
    public static final Victor frontRightDrive = new Victor(4); 
    public static final Victor rearRightDrive = new Victor(3);
   
    public static final Victor shooterFrontRight = new Victor(5);
    public static final Victor shooterBackRight = new Victor(6);
    
    public static final Victor shooterFrontLeft = new Victor(8);
    public static final Victor shooterBackLeft = new Victor(7);
   
    //Pneumatics
    public static final Solenoid shooterAngleControllerOne = new Solenoid(1);    
    public static final Solenoid shooterAngleControllerTwo = new Solenoid(2);
    
    public static final Solenoid intakeAngleControllerOne = new Solenoid(3);
    public static final Solenoid intakeAngleControllerTwo = new Solenoid(4);
    
    /* Motor Group Init */
    public static final MotorGroup driveLeftSide = new MotorGroup(frontLeftDrive, rearLeftDrive);
    public static final MotorGroup driveRightSide = new MotorGroup(frontRightDrive, rearRightDrive);
    
    public static final MotorGroup shooterRightWheels = new MotorGroup(shooterBackRight, shooterFrontRight);
    public static final MotorGroup shooterLeftWheels = new MotorGroup(shooterBackLeft, shooterFrontLeft);
    
    /* System Init */
    public static final IntakeSystem intakeSystem = new IntakeSystem(intakeAngleControllerOne,intakeAngleControllerTwo, intakeVictor); 
    public static final DriveSystem robotDrive = new DriveSystem(driveLeftSide, driveRightSide);
    public static final ShooterAngleSystem shooterAngleSystem = new ShooterAngleSystem(shooterAngleControllerOne, shooterAngleControllerTwo);
    public static final ShooterSystem shooterSystem = new ShooterSystem(shooterLeftWheels, shooterRightWheels);
}
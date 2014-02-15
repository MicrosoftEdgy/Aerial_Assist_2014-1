package org.firstrobotics1923;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import org.firstrobotics1923.system.DriveSystem;
import org.firstrobotics1923.system.IntakeSystem;
import org.firstrobotics1923.system.ShooterAngleSystem;
import org.firstrobotics1923.system.ShooterSystem;
import org.firstrobotics1923.util.MotorGroup;
import org.firstrobotics1923.util.ShooterMotorGroup;
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
    
    public static final boolean[] justPressed = new boolean[14];
    public static final boolean[] triggers = new boolean[4];
    
    /* Relays (Spikes)*/
    public static final Relay compressorSpike = new Relay(1);               //TODO Update
    public static final Relay intakeSpike = new Relay(2);
    
    /* Sensors (eg Encoders)*/
    public static final Encoder leftDriveEncoder = new Encoder(1, 2);                           //TODO update
    public static final Encoder rightDriveEncoder = new Encoder(3, 4);
    
    public static final DigitalInput compressorSafety = new DigitalInput(5);
    
    /* Speed controllers */
    public static final SpeedController frontLeftDrive = new Victor(5);  
    public static final SpeedController centerLeftDrive = new Victor(3);                         
    public static final SpeedController rearLeftDrive = new Victor(1);    
    
    public static final SpeedController frontRightDrive = new Victor(6); 
    public static final SpeedController centerRightDrive = new Victor(4);                         
    public static final SpeedController rearRightDrive = new Victor(2);
   
    public static final Victor shooterFrontRight = new Victor(10);
    public static final Victor shooterBackRight = new Victor(8);
    public static final Victor shooterFrontLeft = new Victor(9);
    public static final Victor shooterBackLeft = new Victor(7);
   
    //Pneumatics
    public static final Solenoid shooterRightPiston = new Solenoid(1);    //TODO update
    public static final Solenoid shooterLeftPiston = new Solenoid(2);
    public static final Solenoid intakeRightPiston = new Solenoid(3);
    public static final Solenoid intakeLeftPiston = new Solenoid(4);
    
    /* Motor Group Init */
    public static final MotorGroup driveLeftSide = new MotorGroup(frontLeftDrive, centerLeftDrive, rearLeftDrive);
    public static final MotorGroup driveRightSide = new MotorGroup(frontRightDrive, centerRightDrive, rearRightDrive);
    
    public static final ShooterMotorGroup shooterFrontWheels = new ShooterMotorGroup(shooterFrontLeft, shooterFrontRight);
    public static final ShooterMotorGroup shooterBackWheels = new ShooterMotorGroup(shooterBackLeft, shooterBackRight);
    
    /* System Init */
    public static final IntakeSystem intakeSystem = new IntakeSystem(intakeRightPiston,intakeLeftPiston, intakeSpike); 
    public static final DriveSystem robotDrive = new DriveSystem(driveLeftSide, driveRightSide);
    public static final ShooterAngleSystem shooterAngleSystem = new ShooterAngleSystem(shooterRightPiston, shooterLeftPiston);
    public static final ShooterSystem shooterSystem = new ShooterSystem(shooterBackWheels, shooterFrontWheels);
}

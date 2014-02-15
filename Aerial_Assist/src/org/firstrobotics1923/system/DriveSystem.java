package org.firstrobotics1923.system;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import org.firstrobotics1923.Components;
import org.firstrobotics1923.util.MotorGroup;

/**
 * The Drive System
 * 
 * @author Pavan Hegde
 * @version 1.0
 * @since Jan 20, 2014
 */
public class DriveSystem extends RobotDrive{

    public DriveSystem(SpeedController frontLeft, SpeedController rearLeft, SpeedController frontRight, SpeedController rearRight) {
        super(frontLeft, rearLeft, frontRight, rearRight);
    }
    
    public DriveSystem(MotorGroup leftSide, MotorGroup rightSide) {
        super(leftSide, rightSide);
    }
    
    public void drive(double leftMag, double rightMag) {
        this.tankDrive(leftMag, rightMag);
        Components.rightDriveEncoder.start();
    }
    
    public void setSafety(boolean enabled) {
        this.setSafetyEnabled(enabled);
    }
    
    public boolean getRightEncoderDirection() {
        return Components.rightDriveEncoder.getDirection();
    }
    
    public boolean getLeftEncoderDirection() {
        return Components.leftDriveEncoder.getDirection();
    }
    
    public double getRightEncoderDistance() {
        return Components.rightDriveEncoder.getDistance();
    }
    
    public double getLeftEncoderDistance() {
        return Components.leftDriveEncoder.getDistance();
    }
    
    public double getRightEncoderRate() {
        return Components.rightDriveEncoder.getRate();
    }
    
    public double getLeftEncoderRate() {
        return Components.leftDriveEncoder.getRate();
    }
    
    public void resetEncoders() {
        Components.rightDriveEncoder.reset();
        Components.leftDriveEncoder.reset();
    }
    
    public void stop() {
        super.stopMotor();
        Components.rightDriveEncoder.stop();
        Components.leftDriveEncoder.stop();
    }   
}


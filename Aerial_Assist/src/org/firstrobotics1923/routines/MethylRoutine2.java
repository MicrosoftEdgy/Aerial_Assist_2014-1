/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.firstrobotics1923.routines;

import edu.wpi.first.wpilibj.Timer;
import org.firstrobotics1923.Components;
import org.firstrobotics1923.EventBus;
import org.firstrobotics1923.event.Event;
import org.firstrobotics1923.event.IntakeAngleInEvent;
import org.firstrobotics1923.event.IntakeAngleOutEvent;
import org.firstrobotics1923.event.IntakeMotorForwardEvent;
import org.firstrobotics1923.event.ShooterLowerAngleEvent;
import org.firstrobotics1923.event.ShooterStartEvent;
import org.firstrobotics1923.event.ShooterStopEvent;
/**
 *
 * @author FRC1923 - Thomas Bruestle
 */
public class MethylRoutine2 {
    private static final Timer AutonTimer = new Timer();
    
    private static boolean robotStartForwardExecuted = false;
    private static boolean robotStopForwardExecuted = false;
    private static boolean intakeAngleOutExecuted = false;
    private static boolean shooterStartExecuted = false;
    private static boolean shooterRaiseAngleExecuted = false;
    private static boolean intakeMotorForwardExecuted = false;
    private static boolean intakeAngleInExecuted = false;
    private static boolean shooterLowerAngleExecuted = false;
    private static boolean intakeAngleOut2Executed = false;
    private static boolean shooterStopExecuted = false;
    private static boolean targetIsHot = false;
    private static double timeWhenFired = 100000;
    
    public static void start()
    {
        robotStartForwardExecuted = false;
        robotStopForwardExecuted = false;
        intakeAngleOutExecuted = false;
        shooterStartExecuted = false;
        shooterRaiseAngleExecuted = false;
        intakeMotorForwardExecuted = false;
        intakeAngleInExecuted = false;
        shooterLowerAngleExecuted = false;
        intakeAngleOut2Executed = false;
        shooterStopExecuted = false;
        targetIsHot = false;
        timeWhenFired = 100000;
        AutonTimer.stop();
        AutonTimer.reset();
        AutonTimer.start();
    }
    public static void stop()
    {
        AutonTimer.stop();
        AutonTimer.reset();
    }    
    public static void doEvent()
    {
        if (AutonTimer.get() >= 5000) //TODO - Use camera instead
        { targetIsHot = true; }
        else
        { targetIsHot = false; }
                   
//        if(Components.table.getString("Hot_Target").equals("LEFT")) {
//            HotTargetDelay = 1500;            
//        } else {
//            HotTargetDelay = 5500;
//        }

        if(false == robotStartForwardExecuted && AutonTimer.get() >= 0) //TODO use camera distance instead
            {
                Components.robotDrive.drive(1.0,1.0);
                robotStartForwardExecuted = true;
            }
        
        if(false == robotStopForwardExecuted && AutonTimer.get() >= 2000) //TODO use camera distance instead
            {
                Components.robotDrive.drive(0.0,0.0);
                robotStopForwardExecuted = true;
            }        
        
        if(false == intakeAngleOutExecuted && AutonTimer.get() >= 0)
            {
                Components.intakeSystem.activate();
                intakeAngleOutExecuted = true;
            }
        
        if(false == shooterStartExecuted && AutonTimer.get() >= 500)
            {
                Components.shooterSystem.activate();
                shooterStartExecuted = true;
            }
        
        if(false == shooterRaiseAngleExecuted && AutonTimer.get() >= 1000)
            {
                Components.shooterSystem.activate();
                shooterRaiseAngleExecuted = true;
            }
        
        if(false == intakeMotorForwardExecuted && AutonTimer.get() >= 1000)
            {
                Components.intakeSystem.reverseMotor();
                intakeMotorForwardExecuted = true;
            }
        
        if(false == intakeAngleInExecuted && AutonTimer.get() >= 2500 && targetIsHot)
            {
                Components.intakeSystem.deactivate();
                intakeAngleInExecuted = true;
                timeWhenFired = AutonTimer.get();
            }

        if(false == shooterLowerAngleExecuted && intakeAngleInExecuted && AutonTimer.get() >= timeWhenFired + 1000)
            {
                Components.shooterSystem.stop();
                shooterLowerAngleExecuted = true;
            }
        
        if(false == intakeAngleOut2Executed && intakeAngleInExecuted && AutonTimer.get() >= timeWhenFired + 1000)
            {
                Components.intakeSystem.deactivate();
                intakeAngleOut2Executed= true;
            }
        
        if(false == shooterStopExecuted && intakeAngleInExecuted && AutonTimer.get() >= timeWhenFired + 1000)
            {
                Components.shooterSystem.stop();
                shooterStopExecuted= true;
            }
    }
}

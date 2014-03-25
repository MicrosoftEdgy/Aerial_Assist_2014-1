package org.firstrobotics1923.event;

import edu.wpi.first.wpilibj.Relay;
import org.firstrobotics1923.Components;

/**
 * Turns on Compressor
 * 
 * @author Aryak Pande, Pavan Hegde
 * @version 1.0
 * @since Jan 25, 2014
 */
public class CompressorOnEvent extends Event{

    /**
     * Main Constructor, creates event that runs once
     */
    public CompressorOnEvent(){       
        super(true);
    }
    
    /**
     * Turns on compressor
     */
    public void event(){
       Components.compressorSpike.set(Relay.Value.kForward);
    }
}
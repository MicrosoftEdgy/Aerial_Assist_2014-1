package org.firstrobotics1923.event;

import edu.wpi.first.wpilibj.Relay;
import org.firstrobotics1923.Components;

/**
 * Turns Off the Compressor
 * 
 * @author Aryak Pande, Pavan Hegde
 * @version 1.0
 * @since Jan 25, 2014
 */
public class CompressorOffEvent extends Event {
    
   /**
    * Main Constructor: creates event that runs once
    */
     public CompressorOffEvent(){       
        super(true);
    }
     
    /**
    * Turns off compressor
    */
    public void event() {
        Components.compressorSpike.set(Relay.Value.kOff);
    }
}

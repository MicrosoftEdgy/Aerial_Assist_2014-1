package org.firstrobotics1923;

import java.util.Vector;
import org.firstrobotics1923.event.Event;

/**
 * An event bus for managing events.
 * 
 * @author Nikhil Ramesh
 * @version 1.0
 * @since Jan. 25, 2014
 */
public class EventBus {
    public static EventBus instance = new EventBus();
    private final Vector eventQueue = new Vector();
    private final Vector running = new Vector();
    
    /**
     * Creates an <code> EventBus </code>
     */
    public EventBus(){
        //Hello :D
    }
    
    /**
     * Pushes the event onto the bus.
     * @param e 
     *      An event to be run
     */
    public void push(Event e) {
        this.eventQueue.addElement(e);    
    }
    
    /**
     * Cleans up all non-running events in running vector.
     */
    public void clean(){
        int runningSize = running.size();
        for( int i = (runningSize - 1); i >= 0; i-- ){
            Event e = (Event) this.running.elementAt(i);
            if(!e.isRunning()){
                this.running.removeElementAt(i);
            }
        }
    }
    
    /**
     * Runs next event from event bus.
     */
    public void next(){
        if(this.hasNext()){
            ((Event) this.eventQueue.elementAt(0)).start();
            running.addElement(this.eventQueue.elementAt(0));
            this.eventQueue.removeElementAt(0);
        }
    }
    
    /**
     * Gets whether or not the event bus has any events to be run
     * @return whether or not the event bus has any events
     */
    public boolean hasNext(){
        return (this.eventQueue.size() > 0);
        
    }
    
    /**
     * Clears event bus completely.
     */
    public void clear(){
        int runningSize2 = running.size();
        for(int i = 0; i < runningSize2; i++){
            ((Event)(this.running.elementAt(i))).stop();
        }
        this.running.removeAllElements();
        this.eventQueue.removeAllElements();
    }
    
    /*
     * Gets the robot's instance of the event bus.
     * @return the robot's event bus
     */
    public static EventBus getInstance(){
        if(instance == null){
            instance = new EventBus();
        }
        return instance;
    }
}
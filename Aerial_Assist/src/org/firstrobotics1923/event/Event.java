package org.firstrobotics1923.event;

/**
 * An abstract robot event (runnable)
 * @author Pavan Hegde
 * @version 1.0
 * @since Jan. 9th, 2014
 */
public abstract class Event implements Runnable{
    public boolean run = false;                //Run or don't run
    public boolean ran = false;                //Has it run once yet?
    Thread thread;
    public final boolean runOnce;              //Run once? Or more
    
    /**
     * Creates an event that will only run once
     */
    protected Event() {
        this(false);
    }
    
    /**
     * Creates an event that may run once or more than once
     * @param oneIteration Whether or not to run the event multiple times
     */
    protected Event(boolean oneIteration){
        this.runOnce = oneIteration;
    }
    
    /**
     * Starts the Event
     */
    public void start() {
        this.run = true; 
        this.thread = new Thread(this);
        this.thread.start();
    }
    
    public void run() {
        while(this.run) {
            if (this.runOnce && !this.ran) {
                this.event();
                this.ran = true;
                this.stop();
            }else if (!this.runOnce) {
                this.event();
                this.stop();
            }
        }
    }
    
    /**
     * Stops the event
     */
    public void stop() {
        this.run = false;    
        //System.out.println("Before: " + this.thread.isAlive());
        thread = null;
    }
    
    /**
     * Returns the current state of event (running vs. not)
     * @return whether the event is running or not
     */
    public boolean isRunning() {               
        return this.run;
    }
    
    /**
     * The Events Contents
     */
    protected abstract void event();
    
}
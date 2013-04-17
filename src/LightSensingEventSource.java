import lejos.nxt.*;
import java.util.*;

/*
 * This class periodically queries the robot's NXT light sensor and generates an
 * event when the value is less than MIN_TRIGGER.
 */

public class LightSensingEventSource implements Runnable{
	

	private static int MIN_TRIGGER = 40;
	boolean event_triggered = false;
	int delay_ms=1000;
	private boolean checkStste= true;
	private ArrayList<LightSensingEventListenerInt> _listeners = new ArrayList<LightSensingEventListenerInt>();
	static LightSensor lightSensor;
	
	private I2CSensor light = new I2CSensor(SensorPort.S1, 1);
	
	LightSensingEventSource(int delay) {
		delay_ms = delay;
		/* Create light sensor object */
		lightSensor = new LightSensor(SensorPort.S1);
	}
	
	public LightSensingEventSource() {
	
	}

	public synchronized void addEventListener(LightSensingEventListenerInt listener)	{
		_listeners.add(listener);
	}
	public synchronized void removeEventListener(LightSensingEventListenerInt listener)	{

		_listeners.remove(listener);
	}
	
	public void run() {
		int dist=0;
		
		while(true) {
			if(checkStste==false){
				break;
			}
			//System.out.println(count++);
			
			dist = lightSensor.getLightValue();
				
			/* if dist > MIN_TRIGGER and event_trigger == true then
			 * reset the trigger.
			 */
			
			if(event_triggered && dist >= MIN_TRIGGER)
				event_triggered = false;			    
			
			/* if dist is less than MIN_TRIGGER send event */
			else if(!event_triggered && dist < MIN_TRIGGER) {
				event_triggered = true;
				System.out.println("Light Sensor Distance: " + dist);
				fireEvent();
				try {
					Thread.sleep(delay_ms);
				} catch (InterruptedException e) {
		
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(delay_ms);
			} catch (InterruptedException e) {
	
				e.printStackTrace();
			}
			

		}
	}
	
	public void stop(){
		
		System.out.println(Thread.currentThread().getName()+" Stopped");
		lightSensor.setFloodlight(false);
		checkStste=false;
	}

public boolean getState(){
	return checkStste;
}

	// call this method whenever you want to notify
	//the event listeners of the particular event
	private synchronized void fireEvent(){
		LightSensingEvent event = new LightSensingEvent(this);
		Iterator<LightSensingEventListenerInt> i = _listeners.iterator();
		while(i.hasNext())	{
			i.next().handleLightSensingEvent(event);
		}
	}
}



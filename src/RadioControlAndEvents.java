import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import lejos.nxt.*;
import lejos.pc.comm.*;

/*
 * This program is a simple program that
 * will drive a motor when the user presses a
 * arrow key form in the keyboard.
 *
 */
public class RadioControlAndEvents extends JFrame implements Runnable, KeyListener, SwingConstants, LightSensingEventListenerInt  {

	private static final long serialVersionUID = 1L;
	public static final int FRAME_HEIGHT = 400;
	public static final int FRAME_WIDTH = 400;
	public static final int DELAY_MS = 100;
	public static final int COMMAND_NONE = 1;
	public static final int COMMAND_FORWARDS = 2;
	public static final int COMMAND_BACKWARDS = 3;
	public static JCheckBox checkBox;
	private int command;
	 static Thread thread = new Thread();
	 Thread[] ac = new Thread[1];
	static NXTComm NXTConn;

	/*
	 * Constructor.
	 */
	public RadioControlAndEvents() {
		this.setBounds(500, 300, 500, 500);
		this.addKeyListener(this);
		setResizable(false);
		this.setSize(400, 400);

		command = COMMAND_NONE;
		

		// Create a GRID panel to hold the user I/O.
		JPanel panel = new JPanel();
		panel.setSize(300, 400);
		panel.setLayout(new GridLayout(2,2));
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		panel.setBorder(blackBorder);
		panel.add(new Label("Light Sensing event: "));
		
		checkBox = new JCheckBox("Check if get sensor value", false);
		checkBox.setFocusable(false);

		checkBox.addActionListener(new ActionListener() {
		
			public void handleLightSensingEvent(EventObject e) {
				
				if(checkBox.isSelected()){
				LightSensingEventSource s =new LightSensingEventSource(100);
				Thread tt= new Thread(s);
				tt.start();
				ac[0] =tt;
				System.out.println(tt.getName() + " Started." +ac[0]);
				
				}else{
			
				LightSensingEventSource s =new LightSensingEventSource(100);
				s.stop();
				System.out.println(ac[0]+" Stopped");
				
				System.out.println("Action happened");

				}
				
				
			}

	
			public void actionPerformed(ActionEvent a) {
				
				handleLightSensingEvent(a);
			}
		});
		panel.add(checkBox);
	
		
		setContentPane(panel);
		pack();
		setVisible(true);
		
		
	}

	public void run() {		
		while (true) { /* loop forever */
			switch (command) {
			case COMMAND_NONE:
				setTitle("None");
				Motor.A.stop();
				break;
			case COMMAND_FORWARDS:
				setTitle("Forwards");
				Motor.A.forward();
				break;
			case COMMAND_BACKWARDS:
				setTitle("Backwards");
				Motor.A.backward();
				break;
			default:
				System.out.println("unknown command " + command);
				System.exit(1);
			}

			try {
				Thread.sleep(DELAY_MS);
			} catch (Exception e) {
				System.out.println(e);
				System.exit(1);
			}
		}
	}

	public void keyPressed(KeyEvent e) {

		int	kc = e.getKeyCode();

		switch (kc) {
		case java.awt.event.KeyEvent.VK_UP:
			command = COMMAND_FORWARDS;
			break;
		case java.awt.event.KeyEvent.VK_DOWN:
			command = COMMAND_BACKWARDS;
			break;
		default:
			command = COMMAND_NONE;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		command = COMMAND_NONE;
	}

	public void keyTyped(KeyEvent e) { /* do nothing */
	}

	
	/*
	 * Window closing event.
	 */
	protected void processWindowEvent(WindowEvent e) {		
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}

	 /*
	 * Main.
	 */

	
	public static void main(String[] args) throws InterruptedException {
		
		try {
			NXTConn = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
		}
		catch(NXTCommException e) {
			e.printStackTrace();
			System.out.println("Error - creating NXT connection");
		}			

		RadioControlAndEvents s = new RadioControlAndEvents();
		Thread t = new Thread(s);
		t.start();
		Thread.sleep(1000);

	}
	
	public void startServicing() {
		

		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	

	public void handleLightSensingEvent(EventObject e) {
		
		System.out.println("Action happened");
	}
	
	
	class MyTextActionListener implements ActionListener {
	    /** Handle the text field Return. */
	    public void actionPerformed(ActionEvent e) {
	    	if(checkBox.isSelected()){
	    		System.out.println("Checkbox checked....");
	    		handleLightSensingEvent(e);
	    	}else{
	    		LightSensingEventSource he = new LightSensingEventSource();
	    		he.stop();
	    		
	    		if(thread.getName().equalsIgnoreCase("lightEvent")){            		
	        		System.out.println("The thread name is "+thread.getName());
	        		System.out.println("Checkbox unchecked.");
	        		
	        		Thread.currentThread().interrupt(); 
	        		           		
	    		}else{
	    			System.out.println("Incorrect Thread Name.. Crashing..");
	    		}
	    		
	    		if(thread.getName().equalsIgnoreCase("lightEvent")){            		
	        		System.out.println("The thread name is "+thread.getName());
	        		System.out.println("Checkbox unchecked.");
	        		
	        		Thread.currentThread().interrupt(); 
	        		           		
	    		}else{
	    			System.out.println("Incorrect Thread Name.. Crashing..");
	    		}
	    		
	    	}
	    }
	    
	    
	}
	
	

	
}


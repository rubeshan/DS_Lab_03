import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.border.*;


public class MyGUI extends JFrame implements SwingConstants, LightSensingEventListenerInt {

	private static final long serialVersionUID = 1L;
	static final int MAX_ROW = 3;
	static final int MAX_COL = 2;
	static final int MAX_HEIGHT = 150;
	static final int MAX_WIDTH = 400;
	 static Thread thread = new Thread();
	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	TextArea outputText;
	JCheckBox inputText;
	

	// Create a GUI Input Output interface to handle user input ad output

	public MyGUI() {
		super("Simple GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(300,300);
		setPreferredSize(new Dimension(MAX_WIDTH,MAX_HEIGHT));
		setResizable(false);

		// Set menus to be HeavyWeight to prevent the Panel from overlapping it.
		
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);

		// Add the menu to support Undo operation
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the File menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("The File menu");
		menuBar.add(menu);

		//Add the Exit option.

		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Exits the application");
		menuItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.exit(0); }});

		menu.add(menuItem);


		// Create a GRID panel to hold the user I/O.
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(MAX_ROW,MAX_COL));
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		panel.setBorder(blackBorder);
		panel.add(new Label("Light Sensing event: "));
		
		inputText = new JCheckBox("Check if get sensor value", false);
		inputText.setFocusable(false);
		inputText.addActionListener(new MyTextActionListener());
		panel.add(inputText);
	

		
		setContentPane(panel);
		setJMenuBar(menuBar);
		pack();
		setVisible(true);
	}
	
    class MyTextActionListener implements ActionListener {
        /** Handle the text field Return. */
        public void actionPerformed(ActionEvent e) {
        	if(inputText.isSelected()){
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

	    
	public void handleLightSensingEvent(EventObject e) {
		
		if(inputText.isSelected()){
    		System.out.println("Checkbox checked....");
    		handleLightSensingEvent(e);
    	}else{
    		LightSensingEventSource he = new LightSensingEventSource();
    		he.stop();        		
  
    		
    	}
		
//		System.out.println("Getting the light sensor values");
//		int delay= 10;
//		thread = new Thread(new LightSensingEventSource(delay));
//		thread.start();
//		thread.setName("lightEvent");
//		
	}

}

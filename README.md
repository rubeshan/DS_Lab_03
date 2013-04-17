DS_Lab_03
=========


Lab#3 – RMI Distributed Application.
Objective
In this lab you will extend the multi-threading and custom events lab to monitor and control an NXT sensing and robot controller. The application that you will develop can be considered a Robot Service. This Robot Service will utilize RMI to communicate to remote clients. 
A. The Software System
Below is an architecture diagram of the distributed software system that you will develop in the lab.

 
B. Testing the Robot Communications
1.  Review the instructions in Lab 0 pertaining to establishing a communication session with the NXT brick and being able to read the light sensor value as well as command a motor to drive.
 
C. Creating a Light Sensor Event
The objective in this section is to modify last week’s lab files to create a set of classes to create an event based on when the light sensor reading is less than a defined value.

1.	Copy MyEventClass.java from the last lab into your project and rename it LightSensingEvent. java. This will be the class that defines the light sensing event.
2.	Copy MyEventClassListener.java from the last lab into your project and rename it LightSensingEventListenerInt.java. Also change the method name from handleMyEventClassEvent(…) to handleLightSensingEvent(…). This will be the Interface definition for the light sensing event.
3.	The LightSensingEventSource is similar to the MyEventSource class but it needs to be modified to read from the NXT light sensor and generate an event when the light level reading is less than a minimum trigger value. It is best if this class can be executed as a separate thread. Therefore it is also necessary to write a run() method to start the reading of the values after the listeners have been registered. To the LightSensingEventSource we will also add a delay so that the thread reading from the robot will sleep to allow other threads to execute. The source code for the class LightSensingEventSource is available with the lab. Include it into your project.
4.	Create a ServiceLightSensingEvent class to service a light sensing event and print out the event when it occurs. You should be able to connect the light sensor to the NXT brick and move it back and forth until the distance values is less than MIN_TRIGGER at which point an event will occur (Note: you will have to experiment with the values returned by the light sensor based on height to determine a reasonable value for MIN_TRIGGER.) In the next section you will be required to do this but service the event with a graphical user interface so writing the code and simply printing the result will help for the next section.
D. Servicing Light Sensing Events and Robot Commands
Similar to last week’s lab you will create a GUI that will allow a user to input robot commands and service the light sensing events. A good place to commence is to simply modify the RadioControl class so that it can service the light sensing events.
1.	Copy the RadioControl class and rename it to RadioControlAndEvent.
2.	Replace the text input of the GUI with a Checkbox that you can set the state to true when the event is generated for the light sensor. Note!! That when a graphic component like a Checkbox is added to the original plain GUI window that RadioControl used, the ability for the RadioControlAndEvent class to manage the Key events is lost because control of theses is released to the Checkbox component. In order to gain this ability back you need to disable the focus on the Checkbox by calling the setFocusable(false) method on the Checkbox object.
3.	This class should implement the LightSensingEventListenerInt interface and add the appropriate code to service the event from the sensor. You should be able to leverage the code from the ServiceLightSensingEvent class that you preciously developed. The GUI of this program should appear like that shown below, and the robot should react to the arrow keys.

 

Presentation to the TA:
Show these results to the TA.
E. Creating a Distributed Robot Controller
The final step is to convert the program above to work remotely using RMI calls. The RadioControlAndEvent class was not designed as a service but contained both the GUI and called the robot APIs directly. Since we are using the Lejos API and we do not want to modify those classes it is simpler to create a class that is specific to the application and creates a wrapper to some of the commands that Lejos provides. Two approaches are possible:
•	Create remote classes that wrap each individual class as specified in the Lejos API.
•	Create one object that can capture all the methods defined in the Lejos API. 

The RobotService Class
For the lab a single class was created that captures the essence of the methods that are required to execute the RadioControl application remotely. This class is the NXTRobotService class and its corresponding NXTRobotServiceServer and NXTRobotServiceInt Interface RMI classes.

1.	Look through the NXTRobotService class and comment on how the Lejos API classes and methods have been implemented for remote access.

The Remote RadioControl Class (RadioControlRMIClient)
It is relatively straight forward to convert the RadioControl class to execute remotely since most of the code will stay the same. The only changes required is to add to the main method the RMI code required to lookup the NXTRobotService located at the server. 

2.	Convert the RadioControl class to an RMI class that can invoke the RobotService methods to drive the robot remotely. Call this class RadioControlRMIClient. Test your code to make certain that when the user presses the up and down arrow keys that the motor, controlled remotely through the NXTRobotService behaves as expected (the same as the RadioControl behavior.)

The Remote RadioControlAndEvent Class (RadioControlAndEventRMIClient and changes to NXTRobotService)
In order to accommodate the light sensing event it is necessary to add to the NXTRobotService the RMI callback capability. 

3.	Use the RMICallback examples included with the lab code to modify the NXTRobotService Class and its associated NXTRobotServiceInt Interface class to support the LightSensingEvent that you previously defined in section C. Note that you will have to also define a call back interface for the client that remotely defines the callback method for the client (see example in CallbackClientInterface). To simplify matters you can use a modified interface as that used in section D (LightSensingEventListenerInt) which defines the callback method handleLightSensingEvent as a template. Go ahead and implement the remote callback functionality into the NXTRobotService.



For the last experiment try to connect two clients to the RobotService. What occurs? Comment on the feasibility of this approach in the control of a robot. Offer some suggestions on a more suitable approach.

The current implementation triggers an event when the distance is less than a minimum distance but does not create a notification when it is clear therefore limiting its use, i.e. because once it is triggered there is no notification of when it is cleared. Comment on how to modify the LightSensingEvent and LightSensingEventSource classes to manage several trigger states. For example a clear state, an unknown state, and a too close state. 


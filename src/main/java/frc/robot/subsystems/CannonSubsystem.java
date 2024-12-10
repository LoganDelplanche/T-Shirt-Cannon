package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class CannonSubsystem extends SubsystemBase{
    private DigitalOutput m_relay; 
    private Constants.cannonConstants.statusStates m_status; //This is an enum
    private Timer m_timer; //For 

    public CannonSubsystem(int outputPin, boolean loaded) {
        m_relay = new DigitalOutput(outputPin);
        if (loaded){m_status = Constants.cannonConstants.statusStates.READYTOSHOOT;} else {m_status = Constants.cannonConstants.statusStates.EMPTY;}//Set status to the status defined by the constants
        m_timer = new Timer();
    }
    
    @Override
    public void periodic(){

    }

    public void waitFunction(double waitTime){
        m_timer.reset();
        m_timer.start();
        while(!m_timer.hasElapsed(waitTime)){}
        m_timer.stop();
        m_timer.reset();
    }

    public void closeSelenoid(){
            m_relay.close();
            m_status = Constants.cannonConstants.statusStates.EMPTY;
        }

    public void launchShirt(){
        if (m_status == Constants.cannonConstants.statusStates.READYTOSHOOT){
            m_relay.set(true);
            m_status = Constants.cannonConstants.statusStates.SHOOTING;
        }   
    }

    public Constants.cannonConstants.statusStates getStatus(){return m_status;} //Returns current status of the cannon

    public void reloadStatus(){m_status = Constants.cannonConstants.statusStates.READYTOSHOOT;} //Allows for the reloading of the cannon without resarting the code
}

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class CannonSubsystem extends SubsystemBase{
    private Relay m_relay; //This is a spike, but a WPI example for the spike uses "Relay" and there is not "Spike" avalible
    private Constants.cannonConstants.statusStates m_status; //This is an enum
    private Timer m_timer; //For 

    public CannonSubsystem(int outputPin, boolean loaded) {
        m_relay = new Relay(outputPin);
        if (loaded){m_status = Constants.cannonConstants.statusStates.READYTOSHOOT;} else {m_status = Constants.cannonConstants.statusStates.EMPTY;}//Set status to the status defined by the constants
        m_timer = new Timer();
    }
    
    @Override
    public void periodic(){
        if (m_timer.hasElapsed(Constants.cannonConstants.waitTime)){
            m_relay.set(Value.kOff);
            m_status = Constants.cannonConstants.statusStates.EMPTY;
            m_timer.stop();
            m_timer.reset();
        }
    }

    public void launchShirt(){
        if (m_status == Constants.cannonConstants.statusStates.READYTOSHOOT){
            m_relay.set(Value.kOn);
            m_status = Constants.cannonConstants.statusStates.SHOOTING;
            m_timer.reset(); //I have not been able to figure out if this is needed, I do not know if the 
            m_timer.start();
        }   
    }

    public Constants.cannonConstants.statusStates getStatus(){return m_status;} //Returns current status of the cannon

    public void reloadStatus(){m_status = Constants.cannonConstants.statusStates.READYTOSHOOT;} //Allows for the reloading of the cannon without resarting the code
}

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class CannonSubsystem extends SubsystemBase{
    //I don't know if these need to be public but time will tell...
    public Talon relay;
    public int status; //0=Loaded, 1=Being Shot, 2=Shot/Empty
    private Timer m_timer;

    public CannonSubsystem(int outputPin, boolean loaded) {
        relay = new Talon(outputPin);
        if (loaded){status = 0;} else {status = 2;}//Set status to the status defined by the constants
        m_timer = new Timer();
    }
    
    @Override
    public void periodic(){
        if (m_timer.hasElapsed(Constants.cannonConstants.waitTime)){
            relay.setVoltage(12.0);
            status = 2;
            m_timer.stop();
            m_timer.reset();
        }
    }

    public void launchShirt(){
        if (status == 0){
            relay.setVoltage(12.0);
            status = 1;
            m_timer.start();
        }   
    }

    public int getStatus(){return status;} //Returns current status of the cannon

    public void reloadStatus(){status = 0;} //Allows for the reloading of the cannon without resarting the code
}

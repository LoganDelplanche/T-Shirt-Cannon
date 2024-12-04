package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class CannonManagerSubsystem extends SubsystemBase{
    private CannonSubsystem[] m_shooters;
    public int currentShooterIndex = 0; //Public to allow for it to be used in auto aiming later. 
    private int m_numOfTests = 0;
    private int m_numOfBarrels;

    public CannonManagerSubsystem(int numberOfShooters, int[] cannonPorts, boolean[] isLoaded) {
        m_shooters = new CannonSubsystem[numberOfShooters];
        m_numOfBarrels = numberOfShooters;

        //Initialize each shooter subsystem by indexing through each one. 
        for (int i = 0; i < m_numOfBarrels; i++) {
            m_shooters[i] = new CannonSubsystem(cannonPorts[i], isLoaded[i]);
        }
    }

    private int getNextBarrel(){
        int nextBarrel = currentShooterIndex + 1;
        if (nextBarrel > m_numOfBarrels){nextBarrel = 0;}
        return nextBarrel;
    }

    public void cycleAndShoot() {
        if (m_shooters[currentShooterIndex].getStatus() == Constants.cannonConstants.statusStates.READYTOSHOOT){
            m_shooters[currentShooterIndex].launchShirt();
            currentShooterIndex = getNextBarrel();
            m_numOfTests = 0;
        } else if (m_numOfTests < m_numOfBarrels){
            currentShooterIndex = getNextBarrel();
            m_numOfTests = m_numOfTests + 1;
            cycleAndShoot();
        } else {DriverStation.reportWarning("!All Barrels Empty!", null);}
    }
    public void reloadCannon(int cannonIndex){m_shooters[cannonIndex].reloadStatus();}
}

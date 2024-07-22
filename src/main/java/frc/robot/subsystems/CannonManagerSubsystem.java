package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class CannonManagerSubsystem extends SubsystemBase{
    private CannonSubsystem[] m_shooters;
    private int m_currentShooterIndex = 0;

    public CannonManagerSubsystem(int numberOfShooters, int[] cannonPorts, boolean[] isLoaded) {
        m_shooters = new CannonSubsystem[numberOfShooters];

        //Initialize each shooter subsystem by indexing through each one. 
        for (int i = 0; i < numberOfShooters; i++) {
            m_shooters[i] = new CannonSubsystem(cannonPorts[i], isLoaded[i]);
        }
    }

    public void cycleAndShoot() {
        m_shooters[m_currentShooterIndex].launchShirt();
        m_currentShooterIndex = (m_currentShooterIndex + 1) % m_shooters.length;
    }
}

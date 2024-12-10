package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class CannonManagerSubsystem extends SubsystemBase{
    private CannonSubsystem[] m_shooters;
    public int currentShooterIndex = 0; //Public to allow for it to be used in auto aiming later. 
    private int m_numOfTests = 0;
    private int m_numOfBarrels;
    private NetworkTableInstance m_networkTable;
    private NetworkTable m_cannonsNetworkTable;
    private NetworkTableEntry m_barrelsLoadedNetworkTableEntry;
        



    public CannonManagerSubsystem(int numberOfShooters, int[] cannonPorts, boolean[] isLoaded) {
        m_shooters = new CannonSubsystem[numberOfShooters];
        m_numOfBarrels = numberOfShooters;

        //Set up NetworkTables
        m_networkTable = NetworkTableInstance.getDefault();
        m_cannonsNetworkTable = m_networkTable.getTable("cannons");
        m_barrelsLoadedNetworkTableEntry = m_cannonsNetworkTable.getEntry("barrelsLoaded");

        //Initialize each shooter subsystem by indexing through each one. 
        for (int i = 0; i < m_numOfBarrels; i++) {
            m_shooters[i] = new CannonSubsystem(cannonPorts[i], isLoaded[i]);
        }
    }

    @Override
    public void periodic() {
        Boolean[] barrelsLoaded = new Boolean[m_shooters.length]; //Construct a new boolean array with the same length as the amount of shooters. 

        for (int i = 0; i < m_shooters.length; i++){
            //If the barrel is currently shooting or is empty then set it to not being loaded
            if(m_shooters[i].getStatus() == Constants.cannonConstants.statusStates.SHOOTING || m_shooters[i].getStatus() == Constants.cannonConstants.statusStates.EMPTY){
                barrelsLoaded[i] = false;
            }else{
                barrelsLoaded[i] = true;
            }
        }
        m_barrelsLoadedNetworkTableEntry.setBooleanArray(barrelsLoaded); //Send the array to network tables. 
    }

    //This funtion helps with the "cycleAndShoot" function
    private int getNextBarrel(){
        int nextBarrel = currentShooterIndex + 1;
        if (nextBarrel > m_numOfBarrels){nextBarrel = 0;}
        return nextBarrel;
    }

    public CannonSubsystem getCannonSubsystem(){
        return m_shooters[currentShooterIndex];
    }
    

    //Shoot specific barrel, this ignores if the barrel is empty. 
    public void shootSpecificBarrel(int barrel){m_shooters[barrel].launchShirt(); }

    //This function finds the next loaded barrel and fires it, this includes wrapping to the 1st barrel if needed. 
    public void cycleAndShoot() {
        if (m_shooters[currentShooterIndex].getStatus() == Constants.cannonConstants.statusStates.READYTOSHOOT){
            currentShooterIndex = getNextBarrel();
            m_shooters[currentShooterIndex].launchShirt();
            m_numOfTests = 0;
        } else if (m_numOfTests < m_numOfBarrels){
            currentShooterIndex = getNextBarrel();
            m_numOfTests = m_numOfTests + 1;
            cycleAndShoot(); //I know this has recursion but the number of tests should mitigate the risk of anything bad happening. 
        } else {DriverStation.reportWarning("All Barrels Empty!", null);} //Tell the DS when it thinks it is empty. 
    }

    public void reloadCannon(int cannonIndex){m_shooters[cannonIndex].reloadStatus();}
}

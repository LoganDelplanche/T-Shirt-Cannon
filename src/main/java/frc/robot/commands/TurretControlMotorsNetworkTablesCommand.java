package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.TurretSubsytem;

public class TurretControlMotorsNetworkTablesCommand extends Command{
    private final TurretSubsytem m_turretSubsytem;
    private NetworkTableInstance m_networkTable;
    private NetworkTable m_turretNetworkTable;
    private NetworkTableEntry m_xValueNetworkTableEntry;
    private NetworkTableEntry m_yValueNetworkTableEntry;

    public TurretControlMotorsNetworkTablesCommand(TurretSubsytem turretSubsytem) {
        m_networkTable = NetworkTableInstance.getDefault();
        m_turretNetworkTable = m_networkTable.getTable("turret");
        m_xValueNetworkTableEntry = m_turretNetworkTable.getEntry("joystickX");
        m_yValueNetworkTableEntry = m_turretNetworkTable.getEntry("joystickY");

        m_turretSubsytem = turretSubsytem;
        addRequirements(m_turretSubsytem);
    }
    
    @Override
    public void execute() {
        double joystickX = m_xValueNetworkTableEntry.getDouble(0.0);
        long lastChangeTime = m_xValueNetworkTableEntry.getLastChange();

        // Calculate the time elapsed since the last change
        long currentTimeMillis = System.currentTimeMillis();
        long elapsedTimeMillis = currentTimeMillis - lastChangeTime;

        // Convert elapsed time to seconds
        double joystickXElapsedTimeSeconds = elapsedTimeMillis / 1000.0;

        double joystickY = m_yValueNetworkTableEntry.getDouble(0.0);
        lastChangeTime = m_yValueNetworkTableEntry.getLastChange();

        // Calculate the time elapsed since the last change
        currentTimeMillis = System.currentTimeMillis();
        elapsedTimeMillis = currentTimeMillis - lastChangeTime;

        // Convert elapsed time to seconds
        double joystickYElapsedTimeSeconds = elapsedTimeMillis / 1000.0;

        if(joystickXElapsedTimeSeconds<=Constants.turretConstants.maximumAgeOfNetworkTableEntry && joystickYElapsedTimeSeconds <= Constants.turretConstants.maximumAgeOfNetworkTableEntry){
        m_turretSubsytem.setMotorSpeeds(joystickX, joystickY);
        }else{
            m_turretSubsytem.setMotorSpeeds(0, 0);
            DriverStation.reportError("Joystick data from Network Tables is too old!", false);
        }
        
    }
}

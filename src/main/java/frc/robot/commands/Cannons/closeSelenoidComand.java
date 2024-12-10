package frc.robot.commands.Cannons;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CannonManagerSubsystem;

public class closeSelenoidComand extends Command{
    private final CannonManagerSubsystem m_CannonManagerSubsystem;

    public closeSelenoidComand(CannonManagerSubsystem shooterSystem) {
        m_CannonManagerSubsystem = shooterSystem;
        addRequirements(m_CannonManagerSubsystem);
    }
    
    @Override
    public void execute() {
        m_CannonManagerSubsystem.getCannonSubsystem().closeSelenoid();
    }
}

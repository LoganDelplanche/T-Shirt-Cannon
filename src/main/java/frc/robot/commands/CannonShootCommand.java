package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CannonManagerSubsystem;

public class CannonShootCommand extends Command{
    private final CannonManagerSubsystem m_CannonManagerSubsystem;

    public CannonShootCommand(CannonManagerSubsystem shooterSystem) {
        m_CannonManagerSubsystem = shooterSystem;
        addRequirements(m_CannonManagerSubsystem);
    }
    
    @Override
    public void execute() {
        m_CannonManagerSubsystem.cycleAndShoot();
    }
}

package frc.robot.commands.Cannons;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CannonManagerSubsystem;

public class CannonShootCommand extends Command{
    private final CannonManagerSubsystem m_CannonManagerSubsystem;
    private final XboxController m_XboxController;

    public CannonShootCommand(CannonManagerSubsystem shooterSystem, XboxController xboxController) {
        m_CannonManagerSubsystem = shooterSystem;
        m_XboxController = xboxController;
        addRequirements(m_CannonManagerSubsystem);
    }
    
    @Override
    public void execute() {
        while(!m_XboxController.getRightBumperPressed()){}
        m_CannonManagerSubsystem.cycleAndShoot();
    }
}

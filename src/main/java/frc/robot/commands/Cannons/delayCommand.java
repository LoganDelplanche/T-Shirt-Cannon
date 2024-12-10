package frc.robot.commands.Cannons;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CannonManagerSubsystem;

public class delayCommand extends Command{
    private final CannonManagerSubsystem m_CannonManagerSubsystem;

    public delayCommand(CannonManagerSubsystem shooterSystem) {
        m_CannonManagerSubsystem = shooterSystem;
        addRequirements(m_CannonManagerSubsystem);
    }
    
    @Override
    public void execute() {
        m_CannonManagerSubsystem.getCannonSubsystem().waitFunction(Constants.cannonConstants.waitTime);
    }
}

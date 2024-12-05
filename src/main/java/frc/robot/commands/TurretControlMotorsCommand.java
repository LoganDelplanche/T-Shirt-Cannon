package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.TurretSubsytem;

public class TurretControlMotorsCommand extends Command{
    private final TurretSubsytem m_turretSubsytem;
    private final CommandXboxController m_Controller;

    public TurretControlMotorsCommand(TurretSubsytem turretSubsytem, CommandXboxController controller) {
        m_turretSubsytem = turretSubsytem;
        m_Controller = controller;
        addRequirements(m_turretSubsytem);
    }
    
    @Override
    public void execute() {
        m_turretSubsytem.setMotorSpeeds(m_Controller.getRightX(), m_Controller.getRightY());
    }
}

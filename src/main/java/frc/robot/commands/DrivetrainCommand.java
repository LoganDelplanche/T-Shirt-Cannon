package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DrivetrainSubsystem;

public class DrivetrainCommand extends Command{
    private final DrivetrainSubsystem m_DrivetrainSubsystem;
    private final CommandXboxController m_Controller;
    

    public DrivetrainCommand(DrivetrainSubsystem driveSystem, CommandXboxController controller) {
        m_Controller = controller;
        m_DrivetrainSubsystem = driveSystem;
        addRequirements(m_DrivetrainSubsystem);
    }
    
    @Override
    public void execute() {
        m_DrivetrainSubsystem.arcadeDrive(m_Controller.getLeftY(), m_Controller.getLeftX());
    }
}

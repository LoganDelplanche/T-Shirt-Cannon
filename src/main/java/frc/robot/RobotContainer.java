// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.CannonShootCommand;
import frc.robot.commands.DrivetrainCommand;
import frc.robot.commands.TurretControlMotors;
import frc.robot.commands.TurretMoveSetpoint;
import frc.robot.subsystems.CannonManagerSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.TurretSubsytem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class RobotContainer {
  public final DrivetrainSubsystem m_drivetrain = new DrivetrainSubsystem(Constants.drivetrainConstants.motorPorts, Constants.drivetrainConstants.motorInverts);

  private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);

  //Declare and set Cannon Manager Subsystem and command. 
  //The CannonManager creates the the barrel objects. 
  private final CannonManagerSubsystem m_CannonManagerSubsystem = new CannonManagerSubsystem(Constants.cannonConstants.numberOfShooters, Constants.cannonConstants.cannonPorts, Constants.cannonConstants.loadedBarrels);
  private final CannonShootCommand m_CannonShootCommand = new CannonShootCommand(m_CannonManagerSubsystem);

  //Declare and set Turret Subsytem and Commands. 
  private final TurretSubsytem m_turretSubsytem = new TurretSubsytem();
  private final TurretMoveSetpoint m_turretMoveSetpoint = new TurretMoveSetpoint(m_turretSubsytem, m_driverController);
  private final TurretControlMotors m_turretControlMotors = new TurretControlMotors(m_turretSubsytem, m_driverController);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureBindings();
    
    m_drivetrain.setDefaultCommand(new DrivetrainCommand(m_drivetrain, m_driverController)); //TODO: Figure out which method is better, this method or the below method
    if (Constants.turretConstants.pidEnabled){
      m_turretSubsytem.setDefaultCommand(m_turretMoveSetpoint);
    }else {
      m_turretSubsytem.setDefaultCommand(m_turretControlMotors);
    }
  }


  private void configureBindings() {
    m_driverController.rightTrigger().onTrue(m_CannonShootCommand); //Run the CannonShootCommand when the right trigger is pressed. 
  }

  public Command getAutonomousCommand() {return null;}
}

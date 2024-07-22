package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DrivetrainSubsystem extends SubsystemBase{
    private Spark m_frontLeft;
    private Spark m_frontRight;
    private Spark m_rearRight;
    private Spark m_rearLeft;
    private DifferentialDrive m_DifferentialDrive;

    public DrivetrainSubsystem(int[] motorPorts, Boolean[] motorInverts){
        m_frontLeft = new Spark(motorPorts[0]);
        m_rearLeft = new Spark(motorPorts[1]);
        m_frontLeft.addFollower(m_rearLeft);
        m_frontLeft.setInverted(motorInverts[0]);
        

        m_frontRight = new Spark(motorPorts[2]);
        m_rearRight = new Spark(motorPorts[3]);
        m_frontRight.addFollower(m_rearRight);
        m_frontRight.setInverted(motorInverts[1]);

        m_DifferentialDrive = new DifferentialDrive(m_frontLeft, m_frontRight);

    }

    public void arcadeDrive(double fowardSpeed, double rotateSpeed){
        m_DifferentialDrive.arcadeDrive(fowardSpeed, rotateSpeed);
        m_DifferentialDrive.feed();
    }
}

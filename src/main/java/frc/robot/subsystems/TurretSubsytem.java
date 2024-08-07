package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TurretSubsytem extends SubsystemBase{
    private AnalogEncoder m_traverseEncoder;
    private CANcoder m_tiltCANcoder;
    
    private Spark m_traverseMotor;
    private Spark m_tiltMotor;
    
    private double m_traverseMaxAngle;
    private double m_traverseMinAngle;

    private double m_tiltMaxAngle;
    private double m_tiltMinAngle;

    private PIDController m_tiltPidController;
    private PIDController m_traversePidController;

    public double traverseSetpoint;
    public double tiltSetpoint;

    public TurretSubsytem(){
        //Set up traverse encoder
        m_traverseEncoder = new AnalogEncoder(Constants.turretConstants.traverseEncoderPort);
        m_traverseEncoder.setPositionOffset(Constants.turretConstants.traverseEncoderOffest);
        
        //Set up CANcoder and configure
        m_tiltCANcoder = new CANcoder(Constants.turretConstants.tiltCANcoderID);
        CANcoderConfiguration CANcoderConfig = new CANcoderConfiguration();
        CANcoderConfig.MagnetSensor.MagnetOffset = Units.radiansToRotations(Constants.turretConstants.tiltCANcoderOffset);
        CANcoderConfig.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Signed_PlusMinusHalf;
        m_tiltCANcoder.getConfigurator().apply(CANcoderConfig);

        //Set up Motors
        m_traverseMotor = new Spark(Constants.turretConstants.traverseMotorPort);
        m_tiltMotor = new Spark(Constants.turretConstants.tiltMotorPort);

        //Set up PIDs
        m_tiltPidController = new PIDController(Constants.turretConstants.tiltkP, Constants.turretConstants.tiltkI, Constants.turretConstants.tiltkD);
        m_traversePidController = new PIDController(Constants.turretConstants.traversekP, Constants.turretConstants.traversekI, Constants.turretConstants.traversekD);

        m_tiltMaxAngle = Constants.turretConstants.tiltMaxAngle;
        m_tiltMinAngle = Constants.turretConstants.tiltMinAngle;

        m_traverseMaxAngle = Constants.turretConstants.traverseMaxAngle;
        m_traverseMinAngle = Constants.turretConstants.traverseMinAngle;
    }

    public double getTraverseAngle(){ return m_traverseEncoder.getAbsolutePosition(); }
    public double getTiltAngle(){ return m_tiltCANcoder.getPosition().getValue(); }

    
    //For setting motor speeds
    //TODO: Implement reversing out of endstop. 
    //TODO: Implement velocitys to stop. 
    //TODO: Reformat to look pretty.     
    public void setMotors(double traverse, double tilt){
        //Tilt
        if (getTiltAngle()<m_tiltMaxAngle && getTiltAngle() > m_tiltMinAngle){
            m_tiltMotor.set(tilt); 
        } else {
            m_tiltMotor.set(0);
        }
        m_tiltMotor.feed();

        //Traverse
        if (getTraverseAngle() < m_traverseMaxAngle && getTraverseAngle() > m_traverseMinAngle){
            m_traverseMotor.set(traverse);
        } else {
            m_traverseMotor.set(0);
        }
        m_traverseMotor.feed();
    }

    //For setting the setpoint of the PIDs
    public void setSetpoint(double traverse, double tilt){
        traverseSetpoint = traverse;
        if (traverseSetpoint < m_traverseMinAngle){traverseSetpoint = m_traverseMinAngle;}
        else if (traverseSetpoint > m_traverseMaxAngle){traverseSetpoint = m_traverseMaxAngle;}

        tiltSetpoint = tilt;
        if (tiltSetpoint < m_tiltMinAngle){tiltSetpoint = m_tiltMinAngle;}
        else if (tiltSetpoint > m_tiltMaxAngle){tiltSetpoint = m_tiltMaxAngle;}        
    }

    //For changing the setpoint of the PIDs
    public void moveSetpoint(double traverse, double tilt){
        traverseSetpoint = traverseSetpoint + traverse;
        if (traverseSetpoint < m_traverseMinAngle){traverseSetpoint = m_traverseMinAngle;}
        else if (traverseSetpoint > m_traverseMaxAngle){traverseSetpoint = m_traverseMaxAngle;}

        tiltSetpoint = tiltSetpoint + tilt;
        if (tiltSetpoint < m_tiltMinAngle){tiltSetpoint = m_tiltMinAngle;}
        else if (tiltSetpoint > m_tiltMaxAngle){tiltSetpoint = m_tiltMaxAngle;}        
    }

    @Override
    public void periodic() {
        boolean PidEnabled = true;
        if (PidEnabled){
            double tiltMove = m_tiltPidController.calculate(getTiltAngle(), tiltSetpoint);
            double traverseMove = m_traversePidController.calculate(getTraverseAngle(), traverseSetpoint);
            setMotors(traverseMove, tiltMove);
        }
    }
}

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TurretSubsytem extends SubsystemBase{
    private Victor m_traverseMotor;
    private Victor m_tiltMotor;

    private DigitalInput m_leftLimitSwitch;
    private DigitalInput m_rightLimitSwitch;
        

    public TurretSubsytem(){
        //Set up Motors
        m_traverseMotor = new Victor(Constants.turretConstants.traverseMotorPort);
        m_tiltMotor = new Victor(Constants.turretConstants.tiltMotorPort);

        //Set up limit switches
        m_leftLimitSwitch = new DigitalInput(Constants.turretConstants.leftLimitSwitchPort);
        m_rightLimitSwitch = new DigitalInput(Constants.turretConstants.rightLimitSwitchPort);
    }

    public void setMotorSpeeds(double x, double y){
        if(!m_leftLimitSwitch.get() && x<0){ //If the left limit switch is not pressed, and the joystick is going left then set the motor to the value
            m_traverseMotor.set(x);
            m_traverseMotor.feed();
        }else if(!m_rightLimitSwitch.get() && x>0){ // Same thing except with the motor going right and the right limit switch. 
            m_traverseMotor.set(x);
            m_traverseMotor.feed();
        }else{
            m_traverseMotor.set(0);
        }

        m_tiltMotor.set(y);
        m_tiltMotor.feed();
    }


    @Override
    public void periodic() {

    }
}

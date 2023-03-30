package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase{
    
    WPI_TalonFX m_armMotor = new WPI_TalonFX(11);
    DigitalInput m_lowerSwitch = new DigitalInput(0);
    DigitalInput m_upperSwitch = new DigitalInput(1);
    double armMotorDist;
    double armSpeed = 0;

    public void configArmMotor(){
        m_armMotor.configFactoryDefault();
        m_armMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_armMotor.setNeutralMode(NeutralMode.Brake);
        m_armMotor.configFeedbackNotContinuous(false, 10);
        m_armMotor.config_kP(0, 0.05);
        m_armMotor.config_kI(0, 0);
        m_armMotor.config_kD(0, 5);
        m_armMotor.config_kF(0, 0);
        m_armMotor.configIntegratedSensorInitializationStrategy(SensorInitializationStrategy.BootToZero);
        m_armMotor.configPeakOutputForward(.25);
        m_armMotor.configPeakOutputReverse(-.25);
    }

    public void noMoveArm(){
        m_armMotor.set(0);
    }
    public void moveArm(double zSpeed){

            armMotorDist = m_armMotor.getSelectedSensorPosition();
            SmartDashboard.putNumber("zSpeed", zSpeed);
            if(armMotorDist >= -70000.0 && armMotorDist <= -1000.0){
                m_armMotor.set(zSpeed);
                armMotorDist = m_armMotor.getSelectedSensorPosition();

            }
            else if(armMotorDist >= -1000.0){
                if(zSpeed < 0){
                    m_armMotor.set(zSpeed);
                    armMotorDist = m_armMotor.getSelectedSensorPosition();

                }
            }
            else if(armMotorDist <= -70000.0){
                if(zSpeed > 0){
                    m_armMotor.set(zSpeed);
                    armMotorDist = m_armMotor.getSelectedSensorPosition();

                }
            }
            else{
                m_armMotor.set(0);
                armMotorDist = m_armMotor.getSelectedSensorPosition();

            }        
    }
    public void setIntake(){
        m_armMotor.set(TalonFXControlMode.Position, -70000);
    }
    public void setMax(){
        m_armMotor.set(TalonFXControlMode.Position, -10000);
    }
    public void setShoot(){
        m_armMotor.set(TalonFXControlMode.Position, -35000);
    }
    public void setlevel(){
        m_armMotor.set(TalonFXControlMode.Position, -65000);

    }

}

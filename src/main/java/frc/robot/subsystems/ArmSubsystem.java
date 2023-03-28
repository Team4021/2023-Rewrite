package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase{
    
    WPI_TalonFX m_armMotor = new WPI_TalonFX(11);

    public void configArmMotor(){
        m_armMotor.configFactoryDefault();
        m_armMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_armMotor.setNeutralMode(NeutralMode.Brake);
        m_armMotor.configFeedbackNotContinuous(false, 10);
        m_armMotor.config_kP(0, 0);
        m_armMotor.config_kI(0, 0);
        m_armMotor.config_kD(0, 0);
        m_armMotor.config_kF(0, 0);
        m_armMotor.configIntegratedSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);
        m_armMotor.configPeakOutputForward(.3);
        m_armMotor.configPeakOutputReverse(-.3);
    }

    public void moveArm(double zSpeed){
        while(m_armMotor.getSelectedSensorPosition() >= 0.0 && m_armMotor.getSelectedSensorPosition() <= 10000.0){
            m_armMotor.set(zSpeed);
        }
        while(m_armMotor.getSelectedSensorPosition() >= 10000.0){
            while(zSpeed < 0){
                m_armMotor.set(zSpeed);
            }
        }
        while(m_armMotor.getSelectedSensorPosition() <= 0.0){
            while(zSpeed > 0){
                m_armMotor.set(zSpeed);
            }
        }
    }

}

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase{
    
    WPI_TalonFX m_armMotor = new WPI_TalonFX(11);
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

    public void noMoveArm(double xSpeed, double ySpeed){
        if(Math.abs(xSpeed) <= .65 && Math.abs(ySpeed) <= 0.65){
            m_armMotor.set(0);
        }else{
            setTravel();
        }
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
        m_armMotor.set(TalonFXControlMode.Position, -60000);
    }
    public void setMax(){
        m_armMotor.set(TalonFXControlMode.Position, -5000);
    }
    public void setMid(){
        m_armMotor.set(TalonFXControlMode.Position, -30000);
    }
    public void setHigh(){
        m_armMotor.set(TalonFXControlMode.Position, -20000);
    }
    public void setTravel(){
        m_armMotor.set(TalonFXControlMode.Position, -15000);
    }
    public void setLow(){
        m_armMotor.set(TalonFXControlMode.Position, -35000);
    }

}

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase{

    WPI_TalonFX m_armMotor = new WPI_TalonFX(11);
    WPI_TalonSRX m_intakeMotor = new WPI_TalonSRX(12);
    DigitalInput m_limitSwitchL = new DigitalInput(8);
    DigitalInput m_limitSwitchR = new DigitalInput(9);

    public void configintakeMotor(){
        m_intakeMotor.configFactoryDefault();
        m_intakeMotor.setNeutralMode(NeutralMode.Brake);
        m_intakeMotor.configFeedbackNotContinuous(false, 10);
        m_intakeMotor.config_kP(0, 0);
        m_intakeMotor.config_kI(0, 0);
        m_intakeMotor.config_kD(0, 0);
        m_intakeMotor.config_kF(0, 0);
        m_intakeMotor.configPeakOutputForward(1);
        m_intakeMotor.configPeakOutputReverse(-1);

    }
    public void noRunIntake(){
        m_intakeMotor.set(0);
    }
    public void runIntake(){
        SmartDashboard.putBoolean("limitSwitchIntakeL", m_limitSwitchL.get());
        SmartDashboard.putBoolean("limitSwitchIntakeR", m_limitSwitchR.get());
        if(m_armMotor.getSelectedSensorPosition() <= -58000.0 && m_armMotor.getSelectedSensorPosition() >= -70000.0 && m_limitSwitchL.get() != true && m_limitSwitchR.get() != true){
            m_intakeMotor.set(0.3);
        }
        else if(m_armMotor.getSelectedSensorPosition() <= -19000.0 && m_armMotor.getSelectedSensorPosition() >= -31000.0){
            m_intakeMotor.set(-.65);
        }
        else if(m_armMotor.getSelectedSensorPosition() <= -34000 && m_armMotor.getSelectedSensorPosition() >= -36000){
            m_intakeMotor.set(-.2);
        }
        else{
            m_intakeMotor.set(0);
        }
    }
    public void slowOut(){
        m_intakeMotor.set(0.35);
    }
    public void shoot(){
        m_intakeMotor.set(-0.65);
    }

}

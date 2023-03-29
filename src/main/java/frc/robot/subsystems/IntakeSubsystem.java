package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase{

    // WPI_TalonSRX m_intakeMotor = new WPI_TalonSRX(12);

    public void configintakeMotor(){
        // m_intakeMotor.configFactoryDefault();
        // m_intakeMotor.setNeutralMode(NeutralMode.Brake);
        // m_intakeMotor.configFeedbackNotContinuous(false, 10);
        // m_intakeMotor.config_kP(0, 0);
        // m_intakeMotor.config_kI(0, 0);
        // m_intakeMotor.config_kD(0, 0);
        // m_intakeMotor.config_kF(0, 0);
        // m_intakeMotor.configPeakOutputForward(1);
        // m_intakeMotor.configPeakOutputReverse(-1);
    }

    public void runIntake(boolean rightTrigger, boolean leftTrigger, boolean rightButton2){
        SmartDashboard.putBoolean("intake", rightButton2);
        SmartDashboard.putBoolean("shoot", rightTrigger);
        SmartDashboard.putBoolean("drop", leftTrigger);
        if(rightButton2 == true){
            // m_intakeMotor.set(.25);
        }
        else if(rightTrigger == true){
            // m_intakeMotor.set(-.75);
        }
        else if(leftTrigger == true){
            // m_intakeMotor.set(-.25);
        }
        else{
            // m_intakeMotor.set(0);
        }
    }
}

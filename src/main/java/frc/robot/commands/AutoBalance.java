package frc.robot.commands;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveSubsystem;

public class AutoBalance extends CommandBase{

    Pigeon2 m_gyro = new Pigeon2(DriveConstants.kGyro);

    DriveSubsystem m_driveSubsystem;

    public AutoBalance(DriveSubsystem driveSubsystem){
        m_driveSubsystem = driveSubsystem;

        addRequirements(driveSubsystem);
    }

      // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_gyro.getPitch() <= 0){
        m_driveSubsystem.drive(0.045, 0.0, 0.0, true, true, false);
    } else if(m_gyro.getPitch() >= 2){
        m_driveSubsystem.drive(-0.045, 0.0, 0.0, true, true, false);
    } else{
        m_driveSubsystem.setX();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

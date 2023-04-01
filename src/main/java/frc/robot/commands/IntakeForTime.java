// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeForTime extends CommandBase {
  private final IntakeSubsystem m_intakeSub;
  private double target = 0.0;
  private double counter = 0.0;
  /** Creates a new IntakeForTime. */
  public IntakeForTime(IntakeSubsystem m_intakeSub, double seconds) {
    this.m_intakeSub = m_intakeSub;

    target = seconds * 50;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_intakeSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(counter < target){
      counter++;
    }

    m_intakeSub.shoot();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intakeSub.noRunIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return counter >= target;  }
}

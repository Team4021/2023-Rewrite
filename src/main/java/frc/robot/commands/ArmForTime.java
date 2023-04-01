// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class ArmForTime extends CommandBase {

  private final ArmSubsystem m_armSub;

  private double target = 0.0;
  private double counter = 0.0;
  /** Creates a new ArmForTime. */
  public ArmForTime(ArmSubsystem m_armSub, double seconds) {
    this.m_armSub = m_armSub;

    target = seconds * 50;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_armSub);
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

    m_armSub.setHigh();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_armSub.noMoveArm(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return counter >= target;  }
}

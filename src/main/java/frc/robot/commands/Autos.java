// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static CommandBase exampleAuto(ExampleSubsystem subsystem) {
    return Commands.sequence(subsystem.exampleMethodCommand(), new ExampleCommand(subsystem));
  }
  
  public static CommandBase balanceAuto(DriveSubsystem m_robotDrive, ArmSubsystem m_armSub, IntakeSubsystem m_intakeSub){
    return Commands.sequence(m_robotDrive.reverseTillFlatAgain(), m_robotDrive.backToAngled(), new AutoBalance(m_robotDrive));
  }

  public static CommandBase otherBalanceAuto(DriveSubsystem m_robotDrive, ArmSubsystem m_armSub, IntakeSubsystem m_intakeSub){
    return Commands.sequence(
      new DriveForTime(m_robotDrive, 0.0, -0.3, 5.0), 
      new DriveForTime(m_robotDrive, 0.0, 0.2, 3),
      new AutoBalance(m_robotDrive));
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}

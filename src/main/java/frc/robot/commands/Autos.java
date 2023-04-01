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
  public static CommandBase exampleAuto(DriveSubsystem m_robotDrive) {
    return Commands.sequence(
      new DriveForTime(m_robotDrive, -0.1, 0, 5.5)
    );
  }
  
  // public static CommandBase balanceAuto(DriveSubsystem m_robotDrive, ArmSubsystem m_armSub, IntakeSubsystem m_intakeSub){
  //   return Commands.sequence(
  //     m_robotDrive.reverseTillFlatAgain(),
  //     m_robotDrive.backToAngled(),
  //     new AutoBalance(m_robotDrive));
  // }

  public static CommandBase otherBalanceAuto(DriveSubsystem m_robotDrive, ArmSubsystem m_armSub, IntakeSubsystem m_intakeSub){
    return Commands.sequence(
      new ArmForTime(m_armSub, 1.3),
      new IntakeForTime(m_intakeSub, 0.5),
      new DriveForTime(m_robotDrive, -0.4, 0, 1.5),
      new DriveForTime(m_robotDrive, 0, 0, 0.1),
      new DriveForTime(m_robotDrive, -0.2, 0.0, 1.7), 
      new DriveForTime(m_robotDrive, 0.25, 0.0, 2.4),
      new AutoBalance(m_robotDrive));
  }
  public static CommandBase shootAuto(DriveSubsystem m_robotDrive, ArmSubsystem m_armSub, IntakeSubsystem m_intakeSub){
    return Commands.sequence(
      new ArmForTime(m_armSub, 1.5),
      new IntakeForTime(m_intakeSub, .5),
      new DriveForTime(m_robotDrive, -0.2, 0.0, 5)
    );
  }
  public static CommandBase longDriveAuto(DriveSubsystem m_robotDrive, ArmSubsystem m_armSub, IntakeSubsystem m_intakeSub){
    return Commands.sequence(
      new ArmForTime(m_armSub, 1.5),
      new IntakeForTime(m_intakeSub, 0.5),
      new DriveForTime(m_robotDrive, -0.2, 0, 5.5)
    );
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.*;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.*;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();
  private final ArmSubsystem m_armSub = new ArmSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandJoystick m_strafeController =
      new CommandJoystick(OIConstants.kStrafeControllerPort);
  private final GenericHID m_strafeGenericHID = 
      new GenericHID(OIConstants.kStrafeControllerPort);
  private final CommandJoystick m_turnController =
      new CommandJoystick(OIConstants.kTurnControllerPort);
  private final GenericHID m_turnGenericHID = 
      new GenericHID(OIConstants.kTurnControllerPort);

  private final JoystickButton m_rightTrigger =
      new JoystickButton(m_strafeGenericHID, 1);
  private final JoystickButton m_leftTrigger =
      new JoystickButton(m_strafeGenericHID, 1);
  private final JoystickButton m_rightButton2 =
      new JoystickButton(m_turnGenericHID, 2);
  private final JoystickButton m_leftButton2 =
      new JoystickButton(m_strafeGenericHID, 2);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    // Configure default commands, always run unless other commands override

    m_robotDrive.setDefaultCommand(
      // The left stick controls translation of the robot.
      // Turning is controlled by the X axis of the right stick.
      new RunCommand(
          () -> m_robotDrive.drive(
              MathUtil.applyDeadband(m_strafeController.getY(), OIConstants.kJoystickDeadband),
              MathUtil.applyDeadband(m_strafeController.getY(), OIConstants.kJoystickDeadband),
               MathUtil.applyDeadband((m_turnController.getX() * Math.abs(m_turnController.getY() - 1.0)), OIConstants.kJoystickDeadband),
              true, true),
          m_robotDrive));

    m_armSub.setDefaultCommand(
      new RunCommand(
          () -> m_armSub.moveArm(
            (-m_turnController.getY() * Math.abs(m_turnController.getX() - 1.0))),
          m_armSub));
    
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
     m_leftTrigger.whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}

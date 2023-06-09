// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class SwerveModule {
  private final WPI_TalonFX m_drivingMotor;
  private final WPI_TalonSRX m_turningMotor;

  private String prefix;


  private double m_chassisAngularOffset = 0.0;

  private SwerveModuleState m_desiredState = new SwerveModuleState(0.0, new Rotation2d());

  /**
   * Constructs a SwerveModule and configures the driving and turning motor,
   * encoder, and PID controller. This configuration is specific to the REV
   * MAXSwerve Module built with NEOs, SPARKS MAX, and a Through Bore
   * Encoder.
   */
  public SwerveModule(int drivingCANId, int turningCANId, double chassisAngularOffset, String prefix) {
    m_drivingMotor = new WPI_TalonFX(drivingCANId);
    m_turningMotor = new WPI_TalonSRX(turningCANId);
    this.prefix = prefix;
    // Factory reset, so we get the SPARKS MAX to a known state before configuring
    // them. This is useful in case a SPARK MAX is swapped out.
    m_drivingMotor.configFactoryDefault();
    m_turningMotor.configFactoryDefault();
    m_drivingMotor.configPeakOutputForward(.75);
    m_drivingMotor.configPeakOutputReverse(-.75);
    m_turningMotor.configPeakOutputForward(.75);
    m_turningMotor.configPeakOutputReverse(-.75);
    // Setup encoders and PID controllers for the driving and turning SPARKS MAX.
    m_drivingMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    m_turningMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog);
    m_drivingMotor.configFeedbackNotContinuous(false, 10);
    m_turningMotor.configFeedbackNotContinuous(false, 10);
    m_drivingMotor.setNeutralMode(NeutralMode.Brake);
    m_turningMotor.setNeutralMode(NeutralMode.Brake);
    m_drivingMotor.config_kP(0, 0.05);
    m_drivingMotor.config_kI(0, 0.000005);
    m_drivingMotor.config_kD(0, 2.56);
    m_drivingMotor.config_kF(0, 0.048);

    m_turningMotor.config_kP(0, 10.0);
    m_turningMotor.config_kI(0, 0);
    m_turningMotor.config_kD(0, 90.0);
    m_turningMotor.config_kF(0, 0);

    m_chassisAngularOffset = chassisAngularOffset;
    m_desiredState.angle = new Rotation2d(rawUnitsToHeading(m_turningMotor.getSelectedSensorPosition(), 1024.0));

  }

  public double rawUnitsToVelocity(double sensorCountsPer100Ms, double encoderCountsPerRev, double gearRatio){
    double motorRotationsPer100ms = sensorCountsPer100Ms / encoderCountsPerRev;
    double motorRotationsPerSecond = motorRotationsPer100ms * 10.0;
    double wheelRotationsPerSecond = motorRotationsPerSecond / gearRatio; 
    double velocityMetersPerSecond = wheelRotationsPerSecond * 2 * Math.PI * Units.inchesToMeters(2.0);
    return velocityMetersPerSecond;
  }

  public int velocityToRawUnits(double velocityMetersPerSecond, double encoderCountsPerRev, double gearRatio){
    double wheelRotationsPerSecond = velocityMetersPerSecond / (2 * Math.PI * Units.inchesToMeters(2.0));
    double motorRotationsPerSecond = wheelRotationsPerSecond * gearRatio; //gear ratio
    double motorRotationsPer100ms = motorRotationsPerSecond / 10.0;
    int sensorCountsPer100Ms = (int)(motorRotationsPer100ms * encoderCountsPerRev);
    return sensorCountsPer100Ms;
  }

  public double rawUnitsToHeading(double sensorCounts, double encoderCountsPerRev){
    double ticksPerDegree = 360.0 / encoderCountsPerRev;
    double encoderDegrees =  ticksPerDegree * sensorCounts;
    double normalizedHeading = encoderDegrees % 360.0;
    return normalizedHeading;
  }

  public double headingToRawUnits(double normalizedHeading, double encoderCountsPerRev){
    double ticksPerDegree = 360.0 / encoderCountsPerRev;
    double sensorCounts = normalizedHeading / ticksPerDegree;
    return sensorCounts;
  }

  public double rawUnitsToPosition(double sensorCounts, double gearRatio){
    double motorRotations = sensorCounts/ 2048.0;
    double wheelRotations = motorRotations * gearRatio;
    double distanceTraveled = wheelRotations * 2 * Math.PI * Units.inchesToMeters(3.0);
    return distanceTraveled;
  }

  /**
   * Returns the current state of the module.
   *
   * @return The current state of the module.
   */
  public SwerveModuleState getState() {
    // Apply chassis angular offset to the encoder position to get the position
    // relative to the chassis.
    return new SwerveModuleState(
      rawUnitsToVelocity(m_drivingMotor.getSelectedSensorVelocity(), 2048.0, 6.67 / 1.0),
        new Rotation2d(Units.degreesToRadians(rawUnitsToHeading(m_turningMotor.getSelectedSensorPosition(), 1024.0)) - m_chassisAngularOffset));
  }

  /**
   * Returns the current position of the module.
   *
   * @return The current position of the module.
   */
  public SwerveModulePosition getPosition() {
    // Apply chassis angular offset to the encoder position to get the position
    // relative to the chassis.
    return new SwerveModulePosition(
        rawUnitsToPosition(m_drivingMotor.getSelectedSensorPosition(), 6.67 / 1.0),
        new Rotation2d(Units.degreesToRadians(rawUnitsToHeading(m_turningMotor.getSelectedSensorPosition(), 1024.0)) - m_chassisAngularOffset));
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    // Apply chassis angular offset to the desired state.
    SwerveModuleState correctedDesiredState = new SwerveModuleState();
    correctedDesiredState.speedMetersPerSecond = desiredState.speedMetersPerSecond;
    correctedDesiredState.angle = desiredState.angle.plus(Rotation2d.fromRadians(m_chassisAngularOffset));

    // Optimize the reference state to avoid spinning further than 90 degrees.
    SwerveModuleState optimizedDesiredState = SwerveModuleState.optimize(correctedDesiredState,
        new Rotation2d((rawUnitsToHeading(m_turningMotor.getSelectedSensorPosition(), 1024.0) * (Math.PI / 180))));

    // Command driving and turning SPARKS MAX towards their respective setpoints.
    m_drivingMotor.set(TalonFXControlMode.Velocity, velocityToRawUnits(optimizedDesiredState.speedMetersPerSecond, 2048.0, 6.67));
    m_turningMotor.set(TalonSRXControlMode.Position, headingToRawUnits(optimizedDesiredState.angle.getDegrees(), 1024.0));
    m_desiredState = desiredState;
  }


  /** Zeroes all the SwerveModule encoders. */
  public void resetEncoders() {
    m_drivingMotor.setSelectedSensorPosition(0);
  }
}

package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import static frc.robot.Constants.*;

/**
 * A swerve module that uses Talon FX's as both driving motors
 * and steering motors.
 */
public class AllFalconSwerveModule implements ISwerveModule {
  private WPI_TalonFX mDriveMotor;
  private WPI_TalonFX mSteerMotor;

  /** Target angle in degrees. */
  private double targetAngle = 0;
  /** Target speed in meters per second. */
  private double targetSpeed = 0;

  /** Last error from a call to steerModule() in degrees */
  private double lastSteerError = 0;

  public AllFalconSwerveModule(WPI_TalonFX driveMotor, WPI_TalonFX steerMotor) {
    mDriveMotor = driveMotor;
    mSteerMotor = steerMotor;
  }

  @Override
  public void setTargetAngle(double angle) {
    targetAngle = angle;
  }

  @Override
  public void setTargetSpeed(double speed) {
    targetSpeed = speed;
  }

  @Override
  public void zeroSteering() {
    mSteerMotor.setSelectedSensorPosition(0, 0, Misc.kConfigTimeout);
  }

  @Override
  public boolean steerModule() {
    mSteerMotor.set(
      TalonFXControlMode.Position,
      degreesToRaw(targetAngle)
    );
    double errorCounts = mSteerMotor.getClosedLoopError();
    lastSteerError = errorCounts / kTalonSteer.kCountsPerDegree;
    return Math.abs(lastSteerError) < kTalonSteer.kErrorThreshold;
  }

  @Override
  public void driveModule() {
    mDriveMotor.set(
      TalonFXControlMode.Velocity,
      metersPerSecToRaw(targetSpeed)
    );
  }

  public double getLastSteerError() {
    return lastSteerError;
  }

  @Override
  public double getTargetAngle() {
    return targetAngle;
  }

  @Override
  public double getTargetSpeed() {
    return targetSpeed;
  }

  @Override
  public void setSimValues(double steerPosition, double driveVelocity) {
    var driveSim = mDriveMotor.getSimCollection();
    var steerSim = mSteerMotor.getSimCollection();
    driveSim.setIntegratedSensorRawPosition(degreesToRaw(steerPosition));
    steerSim.setIntegratedSensorVelocity(metersPerSecToRaw(driveVelocity));
  }

  /**
   * Calculates the raw units for the given angle to give
   * to a TalonFX steering motor.
   *
   * @param angle in degrees
   * @return target angle in raw units
   */
  private int degreesToRaw(double angle) {
    return (int) Math.round(angle * kTalonSteer.kCountsPerDegree);
  }

  /**
   * Calculates the raw units for the given speed to give
   * to a TalonFX driving motor.
   *
   * @param speed in meters per second
   * @return target speed in raw units per 100ms
   */
  private static int metersPerSecToRaw(double speed) {
    return (int) Math.round(speed * kTalonDrive.kCountsPerMeter * 0.1);
  }

  @Override
  public String toString() {
    return String.format(
      "AllFalconSwerveModule { targetAngle = %.2f, targetSpeed = %.2f, lastSteerError = %.2f }",
      targetAngle, targetSpeed, lastSteerError
    );
  }
}

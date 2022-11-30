package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.filter.SlewRateLimiter;

import static frc.robot.Constants.*;

/**
 * A swerve module that uses Talon FX's as both driving motors
 * and steering motors.
 */
public class AllFalconSwerveModule implements ISwerveModule {
  private WPI_TalonFX mDriveMotor;
  private WPI_TalonFX mSteerMotor;

  private FalconSwerveTargets targets = new FalconSwerveTargets(0, 0);

  private SlewRateLimiter driveLimiter = new SlewRateLimiter(1 / Misc.kSlowDownTime);

  /** Last error from a call to steerModule() in degrees */
  private double lastSteerError = 0;

  public AllFalconSwerveModule(WPI_TalonFX driveMotor, WPI_TalonFX steerMotor) {
    mDriveMotor = driveMotor;
    mSteerMotor = steerMotor;
  }

  @Override
  public void setTargetAngle(double angle) {
    targets.angle = angle;
    targets.optimize(getCurrentAngle());
  }

  @Override
  public void setTargetSpeed(double speed) {
    targets.speed = speed;
    targets.optimize(getCurrentAngle());
  }

  @Override
  public void zeroSteering() {
    mSteerMotor.setSelectedSensorPosition(0, 0, Misc.kConfigTimeout);
  }

  @Override
  public boolean steerModule() {
    mSteerMotor.set(
      TalonFXControlMode.Position,
      degreesToRaw(targets.getOptimalAngle())
    );
    double errorCounts = mSteerMotor.getClosedLoopError();
    lastSteerError = errorCounts / kTalonSteer.kCountsPerDegree;
    return Math.abs(lastSteerError) < Misc.kThresholdToDrive;
  }

  @Override
  public void driveModule(boolean targetMet) {
    double speed = targetMet ? targets.getOptimalSpeed() : 0;
    mDriveMotor.set(
      TalonFXControlMode.PercentOutput,
      driveLimiter.calculate(speed)
    );
  }

  public double getLastSteerError() {
    return lastSteerError;
  }

  public double getCurrentAngle() {
    return mSteerMotor.getSelectedSensorPosition() / kTalonSteer.kCountsPerDegree;
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

  @Override
  public String toString() {
    return String.format(
      "{ type = Falcon, cAng = %.0f, tAng = %.0f, tSpd = %.3f, steerError = %.0f }",
      getCurrentAngle(), targets.getOptimalAngle(), targets.getOptimalSpeed(), lastSteerError
    );
  }
}

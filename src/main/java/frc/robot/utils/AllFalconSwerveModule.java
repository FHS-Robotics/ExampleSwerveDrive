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

  private SwerveTargets targets = new SwerveTargets(0, 0);

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
    return Math.abs(lastSteerError) < kTalonSteer.kErrorThreshold;
  }

  @Override
  public void driveModule() {
    mDriveMotor.set(
      TalonFXControlMode.Velocity,
      metersPerSecToRaw(targets.getOptimalSpeed())
    );
  }

  public double getLastSteerError() {
    return lastSteerError;
  }

  public double getCurrentAngle() {
    return mSteerMotor.getSelectedSensorPosition() / kTalonSteer.kCountsPerDegree;
  }

  @Override
  public double getTargetAngle() {
    return targets.angle;
  }

  @Override
  public double getTargetSpeed() {
    return targets.speed;
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
      targets.getOptimalAngle(), targets.getOptimalSpeed(), lastSteerError
    );
  }
}

package frc.robot.swerve;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

import static frc.robot.Constants.*;

public class SoftwarePIDSwerveModule implements ISwerveModule {
  private final MotorController mDriveMotor;
  private final MotorController mSteerMotor;
  private final Runnable mZeroSteerMotor;
  private final IntFetcher mSteerEncoder;
  private final double mCountsPerDegree;
  private final PIDController mSteerPIDController;
  
  private double mTargetAngle = 0;
  private double mTargetSpeed = 0;
  private boolean mInvertSpeed = false;
  private double mLastSteerError = Double.NaN;

  private SlewRateLimiter driveLimiter = new SlewRateLimiter(1 / kSlowDownTime);

  @FunctionalInterface
  public static interface IntFetcher {
    int fetch();
  }

  public SoftwarePIDSwerveModule(
    MotorController driveMotor,
    MotorController steerMotor,
    Runnable zeroSteerMotor,
    IntFetcher steerEncoderValueFetcher,
    double countsPerDegree,
    PIDController steerPIDController
  ) {
    mDriveMotor = driveMotor;
    mSteerMotor = steerMotor;
    mZeroSteerMotor = zeroSteerMotor;
    mSteerEncoder = steerEncoderValueFetcher;
    mCountsPerDegree = countsPerDegree;
    mSteerPIDController = steerPIDController;
  }

  @Override
  public void setTargetAngle(double angle) {
    double currentAngle = getCurrentAngle();
    double distance = Math.abs(angle - currentAngle);
    if (distance > 90) {
      if (angle < 180) {
        mTargetAngle = angle + 180;
      } else {
        mTargetAngle = angle - 180;
      }
      mInvertSpeed = true;
    } else {
      mTargetAngle = angle;
      mInvertSpeed = false;
    }
    mSteerPIDController.setSetpoint(mTargetAngle);
  }
  
  @Override
  public void setTargetSpeed(double speed) {
    mTargetSpeed = speed;
  }

  @Override
  public void zeroSteering() {
    mZeroSteerMotor.run();
  }

  @Override
  public boolean steerModule() {
    double current = getPIDCurrentAngle();
    mSteerMotor.set(mSteerPIDController.calculate(current));
    mLastSteerError = mTargetAngle - current;
    return Math.abs(mLastSteerError) < kThresholdToDrive;
  }

  @Override
  public void driveModule(boolean targetMet) {
    var speed = targetMet ? mTargetSpeed : 0;
    speed = mInvertSpeed ? -speed : speed;
    mDriveMotor.set(driveLimiter.calculate(speed));
  }

  private double rawToDegrees(int raw) {
    return raw / mCountsPerDegree;
  }

  private double getCurrentAngle() {
    var degrees = rawToDegrees(mSteerEncoder.fetch()) % 360;
    if (degrees >= 0) {
      return degrees;
    } else {
      return 360 + degrees;
    }
  }

  /**
   * Recalculates the current angle according to the target
   * angle.
   * 
   * Usage Example:
   * * currentAngle = 10 degrees
   * * pidCurrentAngle = 370 degrees
   * * targetAngle  = 350 degrees
   * 
   * Normally PID would try to go the long way travelling
   * 340 degrees but if we optimized the current angle we
   * gave to PID it will travel 20 degrees
   */
  private double getPIDCurrentAngle() {
    double realCurrent = getCurrentAngle();
    double distance = Math.abs(mTargetAngle - realCurrent);
    if (distance > 180) {
      if (mTargetAngle > realCurrent) {
        return realCurrent + 360;
      } else {
        return realCurrent - 360;
      }
    }
    return realCurrent;
  }

  @Override
  public String toString() {
    double targetSpeed = mInvertSpeed ? -mTargetSpeed : mTargetSpeed;
    return String.format(
      "{ type = PID, cAng = %.0f, tAng = %.0f, tSpd = %.3f, steerError = %.0f }",
      getCurrentAngle(), mTargetAngle, targetSpeed, mLastSteerError
    );
  }
}

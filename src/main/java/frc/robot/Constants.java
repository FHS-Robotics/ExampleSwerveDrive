package frc.robot;

import frc.robot.config.MotorConfig;

public final class Constants {
  // Order of both steer and drive ports are:
  // {Front Right, Front Left, Back Left, Back Right}.
  public static final int[] kSteerMotorPorts = {5, 4, 7, 6};
  public static final int[] kDriveMotorPorts = {1, 0, 3, 2};

  /**
   * The distance between the center of the front-left wheel
   * and the center of the front-right wheel.
   */
  public static final double kTrackWidthMeters = 0.5461;
  /**
   * The distance between the center of the front-left wheel
   * and the center of the back-left wheel.
   */
  public static final double kWheelBaseMeters = 0.5461;
  /**
   * When the absolute value of the X- or Y-axis of the
   * controller is below kDeadband, that value will be treated
   * as if it were zero.
   */
  public static final double kDeadband = 0.4;
  /**
   * The maximum throttle that the drive TalonFX motors will run at.
   */
  public static final double kMaxSpeed = 0.175;
  /**
   * To prevent hard brakes, the wheels' spin speeds will go
   * from 100% to 0% throttle over a kSlowDownTime seconds
   * long period.
   */
  public static final double kSlowDownTime = 0.25;
  /**
   * If not all swerve modules have steered within
   * kThresholdToDrive degrees yet, all modules' wheels will
   * have their throttle set to zero.
   */
  public static final double kThresholdToDrive = 30;
  /**
   * The time to wait when configuring a TalonFX motor.
   */
  public static final int kConfigureMotorTimeout = 100;

  public static final MotorConfig kSteerMotorConfig = new MotorConfig(
    /* P, I, D, and F Gains */     0.05, 0, 0, 0,
    /* Integral Zone */            0,
    /* Allowable Error */          0,
    /* Max Integral Accumalator */ 0,
    /* Peak Output */              0.5,
    /* Loop Period Milliseconds */ 1
  );
  public static final int kSteerEncoderResolution = 2048;
  public static final double kSteerGearRatio = 12 / 1;
  public static final double kSteerCountsPerDegree =
    kSteerEncoderResolution / 360 * kSteerGearRatio;

  public static final MotorConfig kDriveMotorConfig = new MotorConfig(
    /* P, I, D, and F Gains */     0, 0, 0, 0.5,
    /* Integral Zone */            0,
    /* Allowable Error */          0,
    /* Max Integral Accumalator */ 0,
    /* Peak Output */              0.5,
    /* Loop Period Milliseconds */ 1
  );
}

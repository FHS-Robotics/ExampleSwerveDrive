package frc.robot;

import edu.wpi.first.math.util.Units;
import frc.robot.utils.MotorConfig;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static SteerProps kTalonSteer = new SteerProps(
    new MotorConfig(
      0.05, 0, 0, 0, // PIDF
      0,             // Integral Zone
      0,             // Allowable Error
      0,             // Max Integral Accumulator
      0.5,           // Peak Output
      1              // Loop Period (ms)
    ),
    2048,            // Encoder Resolution
    12 / 1           // Gear Ratio
  );
  public static DriveProps kTalonDrive = new DriveProps(
    new MotorConfig(
      0, 0, 0, 0.5, // PIDF
      0,            // Integral Zone
      0,            // Allowable Error
      0,            // Max Integral Accumulator
      0.5,          // Peak Output
      1             // Loop Period (ms)
    ),
    2048,           // Encoder Resolution
    12 / 1,         // Gear Ratio
    Units.inchesToMeters(4) * Math.PI // Wheel Circumference
  );

  public static final class Misc {
    // TrackWidth and WheelBase are in inches.
    public static final double kTrackWidth = 21.5;
    public static final double kWheelBase = 21.5;

    public static final double kDeadband = 0.4;
    public static final double kMaxSpeed = 0.175;
    /**
     * Time in seconds wheels will take to slow from full
     * throttle to neutral
     */
    public static final double kSlowDownTime = 0.25;

    /**
     * Maximum error (in degrees) before wheels will drive
     */
    public static final double kThresholdToDrive = 30;

    public static final int kConfigTimeout = 100;
  }

  public static final class SteerProps {
    public final MotorConfig kMotorConfig;

    public final double kEncoderResolution;
    public final double kGearRatio;
    public final double kCountsPerDegree;

    public SteerProps(
      MotorConfig kMotorConfig,
      double kEncoderResolution, double kGearRatio
    ) {
      this.kMotorConfig = kMotorConfig;
      this.kEncoderResolution = kEncoderResolution;
      this.kGearRatio = kGearRatio;
      this.kCountsPerDegree = kEncoderResolution / 360 * kGearRatio;
    }
  }

  public static final class DriveProps {
    public final MotorConfig kMotorConfig;

    public final double kEncoderResolution;
    public final double kGearRatio;
    public final double kWheelCircumference;
    /** The number of encoder counts drives the robot one meter */
    public final double kCountsPerMeter;

    public DriveProps(
      MotorConfig kMotorConfig,
      double kEncoderResolution, double kGearRatio, double kWheelCircumference
    ) {
      this.kMotorConfig = kMotorConfig;
      this.kEncoderResolution = kEncoderResolution;
      this.kGearRatio = kGearRatio;
      this.kWheelCircumference = kWheelCircumference;
      kCountsPerMeter = kEncoderResolution * kGearRatio / kWheelCircumference;
    }
  }
}

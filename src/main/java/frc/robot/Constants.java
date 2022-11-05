package frc.robot;

import edu.wpi.first.math.util.Units;

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
    0.05, 0, 0, // PID
    2048,       // Encoder Resolution
    7.85 / 1,   // Gear Ratio
    0.05 * 360  // Steer Error Threshold
  );
  public static DriveProps kTalonDrive = new DriveProps(
    0.1, 0, 0,  // PID
    2048,       // Encoder Resolution
    7.85 / 1,   // Gear Ratio
    Units.inchesToMeters(4) * Math.PI // Wheel Circumference
  );

  public static final class Misc {
    // The unit of measurement used for kTrackWidth and
    // kWheelBase does not matter as long as they are the
    // same since only the ratio between kTrackWidth and
    // kWheelBase matter.
    public static final double kTrackWidth = 1;
    public static final double kWheelBase = 1;

    public static final double kDeadband = 0.05;
    public static final double kMaxSpeed = 3;

    public static final int kConfigTimeout = 100;
  }

  public static final class SteerProps {
    public final double kP;
    public final double kI;
    public final double kD;

    public final double kEncoderResolution;
    public final double kGearRatio;
    public final double kCountsPerDegree;

    /** Maximum error (in degrees) the swerve module can be off by. */
    public final double kErrorThreshold;

    public SteerProps(double kP, double kI, double kD, double kEncoderResolution, double kGearRatio, double kErrorThreshold) {
      this.kP = kP; 
      this.kI = kI; 
      this.kD = kD; 
      this.kEncoderResolution = kEncoderResolution;
      this.kGearRatio = kGearRatio; 
      this.kCountsPerDegree = kEncoderResolution / 360 * kGearRatio;
      this.kErrorThreshold = kErrorThreshold;
    }
  }

  public static final class DriveProps {
    public final double kP;
    public final double kI;
    public final double kD;

    public final double kEncoderResolution;
    public final double kGearRatio;
    public final double kWheelCircumference;
    /** The number of encoder counts drives the robot one meter */
    public final double kCountsPerMeter;

    public DriveProps(double kP, double kI, double kD, double kEncoderResolution, double kGearRatio, double kWheelCircumference) {
      this.kP = kP; 
      this.kI = kI; 
      this.kD = kD; 
      this.kEncoderResolution = kEncoderResolution;
      this.kGearRatio = kGearRatio; 
      this.kWheelCircumference = kWheelCircumference; 
      kCountsPerMeter = kEncoderResolution * kGearRatio / kWheelCircumference;
    }
  }
}

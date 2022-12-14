package frc.robot.subsystems;

import frc.robot.debug.DebugValues;
import frc.robot.swerve.ISwerveModule;

import static frc.robot.Constants.*;
import static frc.robot.utils.Utils.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDriveSubsystem extends SubsystemBase {
  /**
   * Swerve modules for this swerve drive must be in the
   * following order:
   *  * 0 -> Front Right
   *  * 1 -> Front Left
   *  * 2 -> Back Left
   *  * 3 -> Back Right
   */
  private ISwerveModule[] mSwerveModules;

  /**
   * Constructs a SwerveDriveSubsystem.
   *
   * @param swerveModules 4 modules [FR, FL, BL, BR]
   */
  public SwerveDriveSubsystem(ISwerveModule[] swerveModules) {
    mSwerveModules = swerveModules;
  }

	public void zeroSteering() {
		for (var module : mSwerveModules) {
			module.zeroSteering();
		}
	}

  public void holonomicDrive(double forward, double strafe, double rotation) {
    double a = strafe - rotation * (kWheelBaseMeters / kTrackWidthMeters);
		double b = strafe + rotation * (kWheelBaseMeters / kTrackWidthMeters);
		double c = forward - rotation * (kTrackWidthMeters / kWheelBaseMeters);
		double d = forward + rotation * (kTrackWidthMeters / kWheelBaseMeters);

		double[] angles = new double[]{
			mapAngle(Math.atan2(b, c) * 180 / Math.PI),
			mapAngle(Math.atan2(b, d) * 180 / Math.PI),
			mapAngle(Math.atan2(a, d) * 180 / Math.PI),
			mapAngle(Math.atan2(a, c) * 180 / Math.PI)
		};

		double[] speeds = new double[]{
			Math.sqrt(b * b + c * c),
			Math.sqrt(b * b + d * d),
			Math.sqrt(a * a + d * d),
			Math.sqrt(a * a + c * c)
		};
    
    double maxSpeed = speeds[0];
		for (double speed : speeds) {
			if (speed > maxSpeed) {
				maxSpeed = speed;
			}
		}
		if (maxSpeed > kMaxSpeed) {
			for (int i = 0; i < speeds.length; i++) {
				speeds[i] = speeds[i] / maxSpeed * kMaxSpeed;
			}
		}

		boolean changeTargets = Math.abs(forward) > 0.05 ||
			Math.abs(strafe) > 0.05 || Math.abs(rotation) > 0.05;

		DebugValues.put("Drive: Change Targets?", "" + changeTargets);
		DebugValues.put("Drive: Angles", doubleArrayToString(angles, 2));
		DebugValues.put("Drive: Speeds", doubleArrayToString(speeds, 2));

		for (int i = 0; i < 4; i++) {
			if (changeTargets) {
				mSwerveModules[i].setTargetAngle(angles[i]);
				mSwerveModules[i].setTargetSpeed(speeds[i]);
			} else {
				mSwerveModules[i].setTargetAngle(0);
				mSwerveModules[i].setTargetSpeed(0);
			}
		}
  }

	public void goToNeutral() {
		for (var module : mSwerveModules) {
			module.setTargetAngle(0);
			module.setTargetSpeed(0);
		}
	}

	@Override
	public void periodic() {
		boolean allWithinSteerThreshold = true;

		for (int i = 0; i < mSwerveModules.length; i++) {
			var module = mSwerveModules[i];
			if(!module.steerModule()) {
				allWithinSteerThreshold = false;
			}
		}

		DebugValues.put("Periodic: Swerves at Targets", "" + allWithinSteerThreshold);
		DebugValues.put("Periodic: Swerve 0 (FR)", mSwerveModules[0].toString());
		DebugValues.put("Periodic: Swerve 1 (FL)", mSwerveModules[1].toString());
		DebugValues.put("Periodic: Swerve 2 (BL)", mSwerveModules[2].toString());
		DebugValues.put("Periodic: Swerve 3 (BR)", mSwerveModules[3].toString());

		for (var module : mSwerveModules) {
			module.driveModule(allWithinSteerThreshold);
		}
	}
}

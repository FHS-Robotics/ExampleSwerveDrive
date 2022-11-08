package frc.robot.subsystems;

import frc.robot.utils.DebugValues;
import frc.robot.utils.ISwerveModule;

import static frc.robot.Constants.*;
import static frc.robot.utils.Utils.*;

import java.util.Arrays;

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

  public void holonomicDrive(double forward, double strafe, double rotation) {
    double a = strafe - rotation * (Misc.kWheelBase / Misc.kTrackWidth);
		double b = strafe + rotation * (Misc.kWheelBase / Misc.kTrackWidth);
		double c = forward - rotation * (Misc.kTrackWidth / Misc.kWheelBase);
		double d = forward + rotation * (Misc.kTrackWidth / Misc.kWheelBase);

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
		if (maxSpeed > Misc.kMaxSpeed) {
			for (int i = 0; i < speeds.length; i++) {
				speeds[i] = speeds[i] / maxSpeed * Misc.kMaxSpeed;
			}
		}

		boolean shouldChangeTargetAngle = Math.abs(forward) > 0.05 ||
			Math.abs(strafe) > 0.05 || Math.abs(rotation) > 0.05;

		if (shouldChangeTargetAngle) {
			DebugValues.set("Swerve Angles", Arrays.toString(angles));
		}
		DebugValues.set("Swerve Speeds", Arrays.toString(speeds));

		for (int i = 0; i < 4; i++) {
			if (shouldChangeTargetAngle) {
				mSwerveModules[i].setTargetAngle(angles[i]);
			}
			mSwerveModules[i].setTargetSpeed(speeds[i]);
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

		DebugValues.set("All Swerves at Targets", "" + allWithinSteerThreshold);
		DebugValues.set("Swerve Module 0 (FR)", mSwerveModules[0].toString());
		DebugValues.set("Swerve Module 1 (FL)", mSwerveModules[1].toString());
		DebugValues.set("Swerve Module 2 (BL)", mSwerveModules[2].toString());
		DebugValues.set("Swerve Module 3 (BR)", mSwerveModules[3].toString());

		if (allWithinSteerThreshold) {
			for (var module : mSwerveModules) {
				module.driveModule();
			}
		}
	}

	@Override
	public void simulationPeriodic() {
		for (var module : mSwerveModules) {
			double tAngle = module.getTargetAngle();
			double tSpeed = module.getTargetSpeed();
			module.setSimValues(tAngle, tSpeed);
		}
	}
}

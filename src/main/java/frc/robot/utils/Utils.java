package frc.robot.utils;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.RobotContainer;

public class Utils {
  private Utils() {}

  public static double deadband(double value) {
    return Math.abs(value) > Misc.kDeadband ? value : 0;
  }

  /**
   * Maps an angle from (-180, 180) to the equivalent (0, 360) range.
   * @param angle in degrees (-180, 180)
   * @return angle in degrees (0, 360]
   */
  public static double mapAngle(double angle) {
    return angle < 0 ? 360 + angle : angle;
  }

  public static void rumbleController(boolean shouldRumble) {
    double power = shouldRumble ? 0.75 : 0;
    RobotContainer.controller.setRumble(RumbleType.kLeftRumble, power);
    RobotContainer.controller.setRumble(RumbleType.kRightRumble, power);
  }
}

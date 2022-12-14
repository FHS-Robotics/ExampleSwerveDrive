package frc.robot.utils;

import static frc.robot.Constants.*;

public class Utils {
  private Utils() {}

  public static double deadband(double value) {
    if (Math.abs(value) > kDeadband) {
      return Math.min((value - kDeadband) / (1 - kDeadband), 1);
    } else {
      return 0;
    }
  }

  /**
   * Maps an angle from (-180, 180) to the equivalent (0, 360) range.
   * @param angle in degrees (-180, 180)
   * @return angle in degrees (0, 360]
   */
  public static double mapAngle(double angle) {
    return angle < 0 ? 360 + angle : angle;
  }

  public static String doubleArrayToString(double[] array, int decimals) {
    var stringBuilder = new StringBuilder('[');
    for (int i = 0; i < array.length; i++) {
      stringBuilder.append(String.format("%." + decimals + "f", array[i]));
      if (i < array.length - 1) {
        stringBuilder.append(", ");
      }
    }
    stringBuilder.append(']');
    return stringBuilder.toString();
  }
}

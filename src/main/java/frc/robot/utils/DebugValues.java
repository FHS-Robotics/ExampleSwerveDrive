package frc.robot.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps track of values that will be printed to console by
 * the DebugCommand.
 */
public class DebugValues {
  private static final Map<String, String> debuggedValues = new HashMap<>();

  public static void debug(String key, String value) {
    debuggedValues.put(key, value);
  }

  public static Map<String, String> getDebuggedValues() {
    return debuggedValues;
  }
}

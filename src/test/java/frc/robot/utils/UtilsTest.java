package frc.robot.utils;

import org.junit.*;
import static org.junit.Assert.*;

public class UtilsTest {
  @Test
  public void optimizeTargetAngleWorks() {
    /** { ..., { target, current, expected }, ... } */
    double[][] testCases = {
      // --- Don't Move ---
      {-720, -720, -720},
      {-370, -370, -370},
      {-360, -360, -360},
      {-10, -10, -10},
      {0, 0, 0},
      {10, 10, 10},
      {360, 360, 360},
      {370, 370, 370},
      {720, 720, 720},

      // --- Keep Original Target ---
      {10, 0, 10},
      {0, 10, 0},
      {0, 180, 0},
      {180, 0, 180},

      // --- Loop Around Clockwise ---
      {0, -10, 0},
      {10, -10, 10},
      {25, -25, 25},
      {0, 350, 360},
      {10, 350, 370},
      {25, 335, 385},
      {0, 710, 720},
      {10, 710, 730},
      {25, 695, 745},

      // --- Loop Around Counter-Clockwise ---
      {350, -360, -370},
      {350, -350, -370},
      {335, -335, -385},
      {350, 0, -10},
      {350, 10, -10},
      {335, 25, -25},
      {350, 360, 350},
      {350, 370, 350},
      {335, 385, 335},
    };
    for (double[] testCase : testCases) {
      double target = testCase[0];
      double current = testCase[1];
      double expected = testCase[2];
      double got = Utils.optimizeTargetAngle(target, current);
      assertEquals(
        String.format(
          "{ Target %.0f, Current %.0f, Expected %.0f, Got %.0f }",
          target, current, expected, got
        ),
        expected, got, 0.01
      );
    }
  }
}

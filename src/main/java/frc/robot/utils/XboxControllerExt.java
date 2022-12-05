package frc.robot.utils;

import edu.wpi.first.wpilibj.XboxController;

/**
 * Extentions to the XboxController class, for ease of use.
 */
public class XboxControllerExt extends XboxController {
  public XboxControllerExt(final int port) {
    super(port);
  }

  /**
   * Rumbles the left and right sides of the controller at equal power.
   */
  public void rumbleController(boolean rumbleOn) {
    double power = rumbleOn ? 0.75 : 0;
    setRumble(RumbleType.kLeftRumble, power);
    setRumble(RumbleType.kRightRumble, power);
  }
}

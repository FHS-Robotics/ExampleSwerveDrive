package frc.robot.utils;

public class SwerveTargets {
  public double angle;
  public double speed;
  public boolean inverted = false;

  public SwerveTargets(double angle, double speed) {
    this.angle = angle;
    this.speed = speed;
  }

  public double getAngle() {
    if (inverted) {
      return angle > 180 ? angle - 180 : angle + 180;
    } else {
      return angle;
    }
  }

  public double getSpeed() {
    return inverted ? -speed : speed;
  }

  public void optimize(double currentAngle) {
    inverted = Math.abs(currentAngle - angle) > 180;
  }
}

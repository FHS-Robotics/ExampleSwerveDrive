package frc.robot.swerve;

public class FalconSwerveTargets {
  public double angle;
  public double speed;
  private boolean inverted = false;

  public FalconSwerveTargets(double angle, double speed) {
    this.angle = angle;
    this.speed = speed;
  }

  public double getOptimalAngle() {
    if (inverted) {
      return angle > 180 ? angle - 180 : angle + 180;
    } else {
      return angle;
    }
  }

  public double getOptimalSpeed() {
    return inverted ? -speed : speed;
  }

  public void optimize(double currentAngle) {
    inverted = Math.abs(currentAngle - angle) > 180;
  }
}

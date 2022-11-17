package frc.robot.utils;

public interface ISwerveModule {
  /**
   * Sets the angle this swerve module should face.
   *
   * @param angle target angle in degrees
   */
  void setTargetAngle(double angle);

  /**
   * Sets the speed this swerve module should run its motor at.
   *
   * @param speed target speed [0, 1]
   */
  void setTargetSpeed(double speed);

  /**
   * Set the module's steering position to zero.
   */
  void zeroSteering();

  /**
   * Runs the steering motor and returns wether the target
   * angle has been met.
   * 
   * The SwerveDriveSubsystem will
   * afterwards run driveModule() if all modules have been
   * steered to their target angles.
   * 
   * @return wether this module has met the target angle
   */
  boolean steerModule();

  /**
   * Runs the driving motor to move the robot.
   *
   * @param targetMet if true driving motor will spin at
   * target speed, otherwise it will be stopped
   */
  void driveModule(boolean targetMet);
}

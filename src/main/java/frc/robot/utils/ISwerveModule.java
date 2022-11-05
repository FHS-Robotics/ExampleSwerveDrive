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
   * @param speed target speed in meters per second
   */
  void setTargetSpeed(double speed);

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
   * SwerveDriveSubsystem will make sure that this is run if
   * and only if all swerve module have succesessfully
   * steerModule()'ed to their target angles.
   */
  void driveModule();

  /**
   * Get the steer error from the last time steerModule() was called.
   *
   * @return last steerModule() error in degrees
   */
  double getLastSteerError();

  /**
   * Returns the last set target angle of this module.
   *
   * @return angle in degrees
   */
  double getTargetAngle();

  /**
   * Returns the last set target speed of this module.
   *
   * @return speed in meters per second
   */
  double getTargetSpeed();

  /**
   * Sets this module's encoders' position and velocity
   * values during simulation mode.
   *
   * @param steerPosition in degrees
   * @param driveVelocity in meters per second
   */
  void setSimValues(double steerPosition, double driveVelocity);
}

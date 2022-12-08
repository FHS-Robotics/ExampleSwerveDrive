## Example Swerve Drive

Created by FRC Team 5866 for a swerve drive with four steering and four driving TalonFX motors.

### Code Structure

The following files are in the subfolder "/src/main/java/frc/robot/":
  * Main.java - Should be left alone
  * Robot.java - Runs TeleopCommand and DebuggingActions (see below)
  * Constants.java - Settings for the swerve drive including:
    * Motor Ports -- The port numbers for the motors in the order FR, FL, BL, BR.
    * Steering Gear Ratio -- Found ours to be roughly 12.0
    * PID gains -- Numbers that determine how precisely and quickly the swerve drive targets an angle
    * Encoder Resolution -- For a TalonFX it's 2048, or 2048 are reported ticks for each revolution of the steering motor
    * Slow Down Time, Max Speed, Deadband, etc.
  * RobotContainer.java - Defines and Configures: motors and SwerveDriveSubsystem
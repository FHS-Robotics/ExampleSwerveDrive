package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.debug.DebugValues;

public class Robot extends TimedRobot {
  @Override
  public void robotInit() {}

  @Override
  public void robotPeriodic() {
    RobotContainer.debuggingActions.runActions();
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {
    DebugValues.clear();
  }

  @Override
  public void teleopInit() {
    RobotContainer.teleopDriveCommand.schedule();
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {
    RobotContainer.teleopDriveCommand.cancel();
    DebugValues.clear();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }
  
  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {
    DebugValues.clear();
  }

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}

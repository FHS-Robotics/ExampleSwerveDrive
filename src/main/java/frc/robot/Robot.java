package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  @Override
  public void robotInit() {
    RobotContainer.debugCommand.schedule();
  }

  @Override
  public void robotPeriodic() {
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
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    RobotContainer.teleopDriveCommand.schedule();
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {
    RobotContainer.teleopDriveCommand.cancel();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
    RobotContainer.debugCommand.schedule();
  }
  
  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.Misc;
import frc.robot.subsystems.SwerveDriveSubsystem;

import static frc.robot.utils.Utils.*;

public class TeleopDriveCommand extends CommandBase {
  private SwerveDriveSubsystem mSwerveDrive;

  public TeleopDriveCommand(SwerveDriveSubsystem swerveDrive) {
    mSwerveDrive = swerveDrive;
    addRequirements(swerveDrive);
  }

  @Override
  public void execute() {
    if (RobotContainer.controller.getBButton()) {
      for (int steerMotor = 0; steerMotor < RobotContainer.steerMotors.length; steerMotor++) {
        RobotContainer.steerMotors[steerMotor].setSelectedSensorPosition(0, 0, Misc.kConfigTimeout);
      }
    }

    // Note: getLeftY() is inverted because pushing up on
    // the stick gives negative values
    double forward = -RobotContainer.controller.getLeftY();
    double strafe = RobotContainer.controller.getLeftX();
    double rotation = RobotContainer.controller.getRightX();

    forward = deadband(forward);
    strafe = deadband(strafe);
    rotation = deadband(rotation);

    mSwerveDrive.holonomicDrive(forward, strafe, rotation);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

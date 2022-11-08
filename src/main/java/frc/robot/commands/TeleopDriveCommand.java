package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveSubsystem;

import static frc.robot.utils.Utils.*;

public class TeleopDriveCommand extends CommandBase {
  private XboxController mController;
  private SwerveDriveSubsystem mSwerveDrive;

  public TeleopDriveCommand(
    XboxController controller,
    SwerveDriveSubsystem swerveDrive
  ) {
    mController = controller;
    mSwerveDrive = swerveDrive;
    addRequirements(swerveDrive);
  }

  @Override
  public void execute() {
    // Note: getLeftY() is inverted because pushing up on
    // the stick gives negative values
    double forward = -mController.getLeftY();
    double strafe = mController.getLeftX();
    double rotation = mController.getRightX();

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

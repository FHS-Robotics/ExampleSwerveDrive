package frc.robot.debug;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SwerveDriveSubsystem;

import static frc.robot.utils.Utils.*;

public class DebuggingActions {
  private XboxController mController;
  private SwerveDriveSubsystem mSwerveDrive;

  public DebuggingActions(XboxController controller, SwerveDriveSubsystem swerveDrive) {
    mController = controller;
    mSwerveDrive = swerveDrive;
  }

  /**
   * Runs debugging actions like performing a debug dump or
   * zeroing motors when the controller has just pressed the
   * corresponding buttons.
   */
  public void runActions() {
    if (mController.getBButtonPressed()) {
      mSwerveDrive.zeroSteering();
      rumbleController(true);
    } else if (mController.getBButtonReleased()) {
      rumbleController(false);
    }

    if (mController.getXButtonPressed()) {
      System.out.println("=== START DEBUG DUMP ===");
      for (var debugged : DebugValues.getDebuggedValues().entrySet()) {
        System.out.println(debugged.getKey() + ": " + debugged.getValue());
      }
      System.out.println("===  END DEBUG DUMP  ===");
      rumbleController(true);
    } else if (mController.getXButtonReleased()) {
      rumbleController(false);
    }

    for (var debugged : DebugValues.getDebuggedValues().entrySet()) {
      SmartDashboard.putString("debug/" + debugged.getKey(), debugged.getValue());
    }
  }
}
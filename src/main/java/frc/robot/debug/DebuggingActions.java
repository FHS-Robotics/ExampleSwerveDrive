package frc.robot.debug;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.subsystems.SwerveDriveSubsystem;

import static frc.robot.Constants.*;
import static frc.robot.utils.Utils.*;

public class DebuggingActions {
  private XboxController mController;
  private SwerveDriveSubsystem mSwerveDrive;

  private double mCurrentSteerP = kTalonDrive.kP;

  private Timer mButtonHeldTimer = new Timer();

  public DebuggingActions(XboxController controller, SwerveDriveSubsystem swerveDrive) {
    mController = controller;
    mSwerveDrive = swerveDrive;
    mButtonHeldTimer.start();
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

    if (mController.getRightBumperPressed()) {
      setSteerP(mCurrentSteerP + 0.01 * 0.02);
      mButtonHeldTimer.reset();
      rumbleController(true);
    }

    if (
      mController.getRightBumper() &&
      mButtonHeldTimer.hasElapsed(1)
    ) {
      setSteerP(mCurrentSteerP + 0.01 * 0.02);
      rumbleController(true);
    } else if (mController.getRightBumperReleased()) {
      rumbleController(false);
    }

    if (mController.getLeftBumperPressed()) {
      setSteerP(mCurrentSteerP - 0.01 * 0.02);
      mButtonHeldTimer.reset();
      rumbleController(true);
    }

    if (
      mController.getLeftBumper() &&
      mButtonHeldTimer.hasElapsed(1)
    ) {
      setSteerP(mCurrentSteerP - 0.01 * 0.02);
      rumbleController(true);
    } else if (mController.getLeftBumperReleased()) {
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

  private void setSteerP(double steerP) {
    this.mCurrentSteerP = steerP;
    System.out.println("Steer P: " + mCurrentSteerP);
      for (var motor : RobotContainer.steerMotors) {
        motor.config_kP(0, mCurrentSteerP, Misc.kConfigTimeout);
      }
  }
}

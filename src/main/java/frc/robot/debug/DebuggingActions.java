package frc.robot.debug;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.config.Customizable;
import frc.robot.subsystems.SwerveDriveSubsystem;
import frc.robot.utils.XboxControllerExt;

import static frc.robot.Constants.*;

public class DebuggingActions {
  private XboxControllerExt mController;
  private SwerveDriveSubsystem mSwerveDrive;

  private Customizable<Double> gain = kSteerMotorConfig.kP;
  private static final double gainIncrement = 0.001;
  private static final double gainMultiplier = 2;

  private Timer mButtonHeldTimer = new Timer();

  public DebuggingActions(XboxControllerExt controller, SwerveDriveSubsystem swerveDrive) {
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
      mController.rumbleController(true);
    } else if (mController.getBButtonReleased()) {
      mController.rumbleController(false);
    }

    if (mController.getRightStickButtonPressed()) {
      setGain(gain.get() * gainMultiplier);
      mController.rumbleController(true);
    } else if (mController.getRightStickButtonReleased()) {
      mController.rumbleController(false);
    }

    if (mController.getRightBumperPressed()) {
      setGain(gain.get() + gainIncrement);
      mButtonHeldTimer.reset();
      mController.rumbleController(true);
    }

    if (
      mController.getRightBumper() &&
      mButtonHeldTimer.hasElapsed(1)
    ) {
      setGain(gain.get() + gainIncrement * 0.02);
      mController.rumbleController(true);
    } else if (mController.getRightBumperReleased()) {
      mController.rumbleController(false);
    }

    if (mController.getLeftStickButtonPressed()) {
      setGain(gain.get() / gainMultiplier);
      mController.rumbleController(true);
    } else if (mController.getLeftStickButtonReleased()) {
      mController.rumbleController(false);
    }

    if (mController.getLeftBumperPressed()) {
      setGain(gain.get() - gainIncrement);
      mButtonHeldTimer.reset();
      mController.rumbleController(true);
    }

    if (
      mController.getLeftBumper() &&
      mButtonHeldTimer.hasElapsed(1)
    ) {
      setGain(gain.get() - gainIncrement * 0.02);
      mController.rumbleController(true);
    } else if (mController.getLeftBumperReleased()) {
      mController.rumbleController(false);
    }

    if (mController.getXButtonPressed()) {
      System.out.println("=== START DEBUG DUMP ===");
      for (var debugged : DebugValues.getDebuggedValues().entrySet()) {
        System.out.println(debugged.getKey() + ": " + debugged.getValue());
      }
      System.out.println("===  END DEBUG DUMP  ===");
      mController.rumbleController(true);
    } else if (mController.getXButtonReleased()) {
      mController.rumbleController(false);
    }

    for (var debugged : DebugValues.getDebuggedValues().entrySet()) {
      SmartDashboard.putString("debug/" + debugged.getKey(), debugged.getValue());
    }
  }

  private void setGain(double newGain) {
    gain.set(newGain);
    System.out.println("P Gain: " + newGain);
  }
}

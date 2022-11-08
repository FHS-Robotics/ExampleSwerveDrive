package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utils.DebugValues;

public class DebugCommand extends CommandBase {
  private XboxController mController;

  public DebugCommand(XboxController controller) {
    mController = controller;
  }

  @Override
  public void initialize() {
    System.out.println("Debug init");
  }

  @Override
  public void execute() {
    if (mController.getXButtonPressed()) {
      System.out.println("=== START DEBUG DUMP ===");
      for (var debugged : DebugValues.getDebuggedValues().entrySet()) {
        System.out.println(debugged.getKey() + ": " + debugged.getValue());
      }
      System.out.println("===  END DEBUG DUMP  ===");
    }

    for (var debugged : DebugValues.getDebuggedValues().entrySet()) {
      SmartDashboard.putString("debug/" + debugged.getKey(), debugged.getValue());
    }
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

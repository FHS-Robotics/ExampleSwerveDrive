package frc.robot;

import frc.robot.commands.TeleopDriveCommand;
import frc.robot.debug.DebuggingActions;
import frc.robot.subsystems.SwerveDriveSubsystem;
import frc.robot.utils.AllFalconSwerveModule;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.XboxController;

public class RobotContainer {
  public static final XboxController controller = new XboxController(0);

  public static final WPI_TalonFX[] driveMotors = {
    talonDriveMotor(1),
    talonDriveMotor(0),
    talonDriveMotor(3),
    talonDriveMotor(2)
  };

  public static final WPI_TalonFX[] steerMotors = {
    talonSteerMotor(5),
    talonSteerMotor(4),
    talonSteerMotor(7),
    talonSteerMotor(6)
  };

  public static final SwerveDriveSubsystem swerveDriveSubsystem = new SwerveDriveSubsystem(
    new AllFalconSwerveModule[] {
      new AllFalconSwerveModule(driveMotors[0], steerMotors[0]),
      new AllFalconSwerveModule(driveMotors[1], steerMotors[1]),
      new AllFalconSwerveModule(driveMotors[2], steerMotors[2]),
      new AllFalconSwerveModule(driveMotors[3], steerMotors[3]),
    }
  );

  public static final TeleopDriveCommand teleopDriveCommand = new TeleopDriveCommand(controller, swerveDriveSubsystem);
  public static final DebuggingActions debuggingActions = new DebuggingActions(controller, swerveDriveSubsystem);

  private RobotContainer() {}

  private static WPI_TalonFX talonSteerMotor(int port) {
    WPI_TalonFX motor = new WPI_TalonFX(port);
    motor.setInverted(false);
    motor.setSensorPhase(false);
    motor.configureSlot(
      kTalonSteer.kMotorConfig.toCtreSlotConfiguration(),
      0, Misc.kConfigTimeout
    );
    motor.setSelectedSensorPosition(0, 0, Misc.kConfigTimeout);
    return motor;
  }

  private static WPI_TalonFX talonDriveMotor(int port) {
    WPI_TalonFX motor = new WPI_TalonFX(port);
    motor.setInverted(false);
    motor.setSensorPhase(false);
    motor.configureSlot(
      kTalonDrive.kMotorConfig.toCtreSlotConfiguration(),
      0, Misc.kConfigTimeout
    );
    return motor;
  }
}

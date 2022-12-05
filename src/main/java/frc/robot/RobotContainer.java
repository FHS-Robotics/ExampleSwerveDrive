package frc.robot;

import frc.robot.commands.TeleopDriveCommand;
import frc.robot.debug.DebuggingActions;
import frc.robot.subsystems.SwerveDriveSubsystem;
import frc.robot.utils.AllFalconSwerveModule;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.XboxController;

public class RobotContainer {
  public static final XboxController controller = new XboxController(0);

  public static final WPI_TalonFX[] driveMotors = {
    talonDriveMotor(kDriveMotorPorts[0]),
    talonDriveMotor(kDriveMotorPorts[1]),
    talonDriveMotor(kDriveMotorPorts[2]),
    talonDriveMotor(kDriveMotorPorts[3])
  };

  public static final WPI_TalonFX[] steerMotors = {
    talonSteerMotor(kSteerMotorPorts[0]),
    talonSteerMotor(kSteerMotorPorts[1]),
    talonSteerMotor(kSteerMotorPorts[2]),
    talonSteerMotor(kSteerMotorPorts[3])
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
    motor.setNeutralMode(NeutralMode.Coast);
    motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    motor.setSelectedSensorPosition(0, 0, kConfigureMotorTimeout);
    motor.configureSlot(
      kSteerMotorConfig.toCtreSlotConfiguration(),
      0, kConfigureMotorTimeout
    );
    kSteerMotorConfig.kP.consume((kP) -> motor.config_kP(0, kP, kConfigureMotorTimeout));
    kSteerMotorConfig.kI.consume((kI) -> motor.config_kI(0, kI, kConfigureMotorTimeout));
    kSteerMotorConfig.kD.consume((kD) -> motor.config_kD(0, kD, kConfigureMotorTimeout));
    return motor;
  }

  private static WPI_TalonFX talonDriveMotor(int port) {
    WPI_TalonFX motor = new WPI_TalonFX(port);
    motor.setInverted(false);
    motor.setSensorPhase(false);
    motor.setNeutralMode(NeutralMode.Brake);
    motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    motor.configureSlot(
      kDriveMotorConfig.toCtreSlotConfiguration(),
      0, kConfigureMotorTimeout
    );
    kDriveMotorConfig.kP.consume((kP) -> motor.config_kP(0, kP, kConfigureMotorTimeout));
    kDriveMotorConfig.kI.consume((kI) -> motor.config_kI(0, kI, kConfigureMotorTimeout));
    kDriveMotorConfig.kD.consume((kD) -> motor.config_kD(0, kD, kConfigureMotorTimeout));
    return motor;
  }
}

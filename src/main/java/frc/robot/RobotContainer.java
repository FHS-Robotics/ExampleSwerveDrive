package frc.robot;

import frc.robot.commands.TeleopDriveCommand;
import frc.robot.debug.DebuggingActions;
import frc.robot.subsystems.SwerveDriveSubsystem;
import frc.robot.swerve.SoftwarePIDSwerveModule;
import frc.robot.utils.XboxControllerExt;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;

public class RobotContainer {
  public static final XboxControllerExt controller = new XboxControllerExt(0);

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

  public static final SwerveDriveSubsystem swerveDriveSubsystem;
  static {
    SoftwarePIDSwerveModule modules[] = new SoftwarePIDSwerveModule[4];
    var conf = kSteerMotorConfig;
    for (int j = 0; j < 4; j++) {
      // "i" must be final to be accessed from the lambdas
      // (otherwise there'd be a bug)
      final int i = j;
      var pid = new PIDController(conf.kP.get(), conf.kI.get(), conf.kD.get());
      conf.kP.consume((kP) -> pid.setP(kP));
      conf.kI.consume((kI) -> pid.setI(kI));
      conf.kD.consume((kD) -> pid.setD(kD));
      modules[i] = new SoftwarePIDSwerveModule(
        driveMotors[i],
        steerMotors[i],
        () -> { steerMotors[i].setSelectedSensorPosition(0, 0, 0); },
        () -> { return (int) steerMotors[i].getSelectedSensorPosition(); },
        kSteerCountsPerDegree,
        pid
      );
    }
    swerveDriveSubsystem = new SwerveDriveSubsystem(modules);
  }

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
    return motor;
  }
}

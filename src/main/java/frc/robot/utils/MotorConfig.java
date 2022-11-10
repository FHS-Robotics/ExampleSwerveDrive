package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;

public class MotorConfig {
  /** Motor Gain */
  public double kP, kI, kD, kF;
  /** Zone passed which the integral accumulator will be cleared */
  public double integralZone;
  /** Allowable error to count as "close enough" */
  public double allowableClosedloopError;
  /** Max in native units */
  public double maxIntegralAccumulator;
  /** Peak in [0, 1] */
  public double closedLoopPeakOutput;
  /** Period in ms */
  public int closedLoopPeriod;

  public MotorConfig(
    double kP, double kI, double kD, double kF,
    double integralZone,
    double allowableClosedloopError,
    double maxIntegralAccumulator,
    double closedLoopPeakOutput,
    int closedLoopPeriod
  ) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
    this.kF = kF;
    this.integralZone = integralZone;
    this.allowableClosedloopError = allowableClosedloopError;
    this.maxIntegralAccumulator = maxIntegralAccumulator;
    this.closedLoopPeakOutput = closedLoopPeakOutput;
    this.closedLoopPeriod = closedLoopPeriod;
  }

  public SlotConfiguration toCtreSlotConfiguration() {
    SlotConfiguration slotConfiguration = new SlotConfiguration();
    slotConfiguration.kP = kP;
    slotConfiguration.kI = kI;
    slotConfiguration.kD = kD;
    slotConfiguration.kF = kF;
    slotConfiguration.integralZone = integralZone;
    slotConfiguration.allowableClosedloopError = allowableClosedloopError;
    slotConfiguration.maxIntegralAccumulator = maxIntegralAccumulator;
    slotConfiguration.closedLoopPeakOutput = closedLoopPeakOutput;
    slotConfiguration.closedLoopPeriod = closedLoopPeriod;
    return slotConfiguration;
  }
}

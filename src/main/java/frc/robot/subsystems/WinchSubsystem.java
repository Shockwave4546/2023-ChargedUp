package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Winch;
import frc.robot.utils.shuffleboard.DebugMotorCommand;
import frc.robot.utils.shuffleboard.ShuffleboardBoolean;
import frc.robot.utils.shuffleboard.ShuffleboardDouble;
import frc.robot.utils.telemetry.Telemetry;

/**
 * 
 */
public class WinchSubsystem extends SubsystemBase {
  private final ShuffleboardTab tab = Shuffleboard.getTab("WinchSubsystem");
  private final CANSparkMax winchMotor = new CANSparkMax(Winch.MOTOR_ID, MotorType.kBrushless);
  private final ShuffleboardDouble setpoint = new ShuffleboardDouble(tab, "Angle Setpoint");
  private final Encoder encoder = new Encoder(5, 6);
  private final ShuffleboardBoolean enabled = new ShuffleboardBoolean(tab, "Enabled", true);
  private boolean reachedPosition = true;

  /**
   * 
   */
  public WinchSubsystem() {
    winchMotor.setInverted(true); 

    encoder.setDistancePerPulse(360.0 / 2048.0);
    resetEncoder();

    winchMotor.setSmartCurrentLimit(40);

    new DebugMotorCommand(tab, "Run Winch Motor", winchMotor, this);
    tab.addNumber("Encoder Angle", encoder::getDistance);
    tab.addNumber("Encoder Angle", encoder::getDistance);
    tab.add("Apply Angle", new InstantCommand(() -> { setAngle(setpoint.get()); } ));
    tab.add("Reset Winch Encoder", new InstantCommand(this::resetEncoder, this));
    stop();
  }

  /**
   * 
   */
  @Override public void periodic() {
    // Telemetry.logDouble("Winch Motor Output Voltage", winchMotor.getAppliedOutput());
    Telemetry.logDouble("Winch Setpoint", setpoint.get());

    if (!enabled.get() || atSetpoint() || reachedPosition) {
      this.reachedPosition = true;
      stop();
      return;
    }

    winchMotor.set((getAngle() > setpoint.get() ? 1.0 : -1.0) * Winch.DEFAULT_SPEED);
  }

  /**
   * @return
   */
  public boolean atSetpoint() {
    return getAngle() > setpoint.get() - Winch.SETPOINT_TOLERANCE && getAngle() < setpoint.get() + Winch.SETPOINT_TOLERANCE;
  }

  /**
   * @return
   */
  public double getAngle() {
    return encoder.getDistance();
  }

  public void resetEncoder() {
    encoder.reset();
  }

  /**
   * 
   */
  public void stop() {
    winchMotor.stopMotor();
  }

  public double getShuffleboardAngle() {
    return setpoint.get();
  }

  /**
   * 
   */
  public void setAngle(double setpoint) {
    this.reachedPosition = false;
    this.setpoint.set(setpoint);
  }
}
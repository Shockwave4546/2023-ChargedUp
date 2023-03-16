package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
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
  private final AHRS armPivotGyro = new AHRS(SerialPort.Port.kUSB1);
  private final ShuffleboardBoolean enabled = new ShuffleboardBoolean(tab, "Enabled");
  private boolean reachedPosition = true;

  /**
   * 
   */
  public WinchSubsystem() {
    winchMotor.setInverted(true); 
    resetGyro();

    new DebugMotorCommand(tab, "Run Winch Motor", winchMotor, this);
    final var gyroLayout = tab.getLayout("Gyro", BuiltInLayouts.kList);
    gyroLayout.addNumber("Gyro Pitch", () -> armPivotGyro.getPitch());
    gyroLayout.addNumber("Gyro Yaw", () -> armPivotGyro.getYaw());
    gyroLayout.addNumber("Gyro Roll (Angle)", this::getAngle);
    tab.add("Apply Angle", new InstantCommand(() -> { setAngle(setpoint.get()); } ));
    stop();
  }

  /**
   * 
   */
  @Override public void periodic() {
    Telemetry.logDouble("Gyro Pitch", armPivotGyro.getPitch());
    Telemetry.logDouble("Gyro Yaw", armPivotGyro.getYaw());
    Telemetry.logDouble("Gyro Roll (Angle)", getAngle());
    Telemetry.logDouble("Winch Motor Output Voltage", winchMotor.getAppliedOutput());
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
  private double getAngle() {
    return Math.round(-armPivotGyro.getRoll() * 100.0) / 100.0;
  }

  /**
   * 
   */
  public void stop() {
    winchMotor.stopMotor();
  }

  /**
   * 
   */
  public void setAngle(double setpoint) {
    this.reachedPosition = false;
    this.setpoint.set(setpoint);
  }

  /**
   * Note: Doesn't work since NavX gyro only resets yaw axis, which doesn't work with our mounting configuration.
   */
  private void resetGyro() {
    armPivotGyro.reset();
    ;
  }
}
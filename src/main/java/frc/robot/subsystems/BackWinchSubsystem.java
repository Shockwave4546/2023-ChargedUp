package frc.robot.subsystems;

import static frc.robot.utils.Utils.configureSparkMax;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.BackWinch;
import frc.robot.utils.shuffleboard.DebugMotorCommand;
import frc.robot.utils.shuffleboard.ShuffleboardDouble;

/**
 * 
 */
public class BackWinchSubsystem extends PIDSubsystem {
  private final ShuffleboardTab tab = Shuffleboard.getTab("BackWinchSubsystem");
  private final CANSparkMax backWinchMotor = configureSparkMax(new CANSparkMax(BackWinch.MOTOR_ID, MotorType.kBrushless));
  private final RelativeEncoder backWinchEncoder = backWinchMotor.getAlternateEncoder(8192);
  private final ShuffleboardDouble backWinchAngle = new ShuffleboardDouble(tab, "Back Winch Angle");

  /**
   * 
   */
  public BackWinchSubsystem() {
    super(new PIDController(BackWinch.P, BackWinch.I, BackWinch.D));
    backWinchEncoder.setPositionConversionFactor(BackWinch.POSITION_CONVERSION_FACTOR);

    resetPosition();

    new DebugMotorCommand(tab, "Back Winch", backWinchMotor, this);
    tab.add("Back Winch PID Controller", getController());
  }

  @Override public void periodic() {
    setSetpoint(backWinchAngle.get());
    super.periodic();
  }

  /**
   * 
   * @param output
   * @param setpoint
   */
  @Override protected void useOutput(double output, double setpoint) {
    backWinchMotor.setVoltage(output);
  }

  /**
   * 
   * @return
   */
  @Override protected double getMeasurement() {
    return backWinchEncoder.getPosition();
  }

  /**
   * Zeros the angle reading of the encoder.
   */
  private void resetPosition() {
    backWinchEncoder.setPosition(0.0);
  }
}
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
  private final CANSparkMax backWinchMotor = new CANSparkMax(BackWinch.MOTOR_ID, MotorType.kBrushless);
  private final RelativeEncoder backWinchEncoder = backWinchMotor.getEncoder();
  private final ShuffleboardDouble backWinchAngle = new ShuffleboardDouble(tab, "Back Winch Angle");

  /*IT PYL*TJJ2
   * 
   */
  public BackWinchSubsystem() {
    super(new PIDController(BackWinch.P, BackWinch.I, BackWinch.D));
    backWinchMotor.restoreFactoryDefaults();
    // backWinchEncoder.setPositionConversionFactor(10.0F);
    backWinchEncoder.setPositionConversionFactor(BackWinch.POSITION_CONVERSION_FACTOR);
    backWinchMotor.setInverted(true);
    // backWinchMotor.burnFlash();

    resetPosition();

    new DebugMotorCommand(tab, "Back Winch", backWinchMotor, this);
    tab.add("Back Winch PID Controller", getController());
    tab.addNumber("Encoder D", () -> backWinchEncoder.getPosition());
  }

  @Override public void periodic() {
    System.out.println("--------------------------------------------- " + backWinchEncoder.getPosition());
    
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
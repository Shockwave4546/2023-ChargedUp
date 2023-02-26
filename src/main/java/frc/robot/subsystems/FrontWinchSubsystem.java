package frc.robot.subsystems;

import static frc.robot.utils.Utils.configureSparkMax;
import static frc.robot.utils.Utils.configureVictorSPX;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.FrontWinch;
import frc.robot.utils.shuffleboard.DebugMotorCommand;
import frc.robot.utils.shuffleboard.ShuffleboardDouble;

/**
 * 
 */
public class FrontWinchSubsystem extends PIDSubsystem {
  private final ShuffleboardTab tab = Shuffleboard.getTab("FrontWinchSubsystem");
  // private final CANSparkMax frontWinchMotor = configureSparkMax(new CANSparkMax(FrontWinch.MOTOR_ID, MotorType.kBrushless));
  // private final RelativeEncoder frontWinchEncoder = frontWinchMotor.getAlternateEncoder(8192);
  private final WPI_VictorSPX frontWinchMotor = configureVictorSPX(new WPI_VictorSPX(FrontWinch.MOTOR_ID));
  private final Encoder frontWinchEncoder = new Encoder(FrontWinch.ENCODER[0], FrontWinch.ENCODER[1]);
  private final ShuffleboardDouble frontWinchAngle = new ShuffleboardDouble(tab, "Front Winch Angle");

  /**
   * 
   */
  public FrontWinchSubsystem() {
    super(new PIDController(FrontWinch.P, FrontWinch.I, FrontWinch.D));
    // frontWinchEncoder.setPositionConversionFactor(FrontWinch.POSITION_CONVERSION_FACTOR);
    frontWinchEncoder.setDistancePerPulse(FrontWinch.DISTANCE_PER_PULSE);
    resetPosition();

    new DebugMotorCommand(tab, "Front Winch", frontWinchMotor, this);
    tab.add("Front Winch PID Controller", getController());
  }

  @Override public void periodic() {
    setSetpoint(frontWinchAngle.get());
    super.periodic();
  }

  /**
   * 
   * @param output
   * @param setpoint
   */
  @Override protected void useOutput(double output, double setpoint) {
    frontWinchMotor.setVoltage(output);
  }

  /**
   * 
   * @return
   */
  @Override protected double getMeasurement() {
    // return frontWinchEncoder.getPosition();
    return frontWinchEncoder.getDistance();
  }

  /**
   * Zeros the angle reading of the encoder.
   */
  private void resetPosition() {
    // frontWinchEncoder.setPosition(0.0);
    frontWinchEncoder.reset();
  }
}
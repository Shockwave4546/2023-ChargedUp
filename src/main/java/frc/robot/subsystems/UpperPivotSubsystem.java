package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.Constants.UpperPivot;
import frc.robot.utils.shuffleboard.ShuffleboardDouble;
import frc.robot.utils.telemetry.Telemetry;

import static frc.robot.utils.Utils.configureSparkMax;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Represents the upper arm responsible for carrying the intake to set angles.
 */
public class UpperPivotSubsystem extends ProfiledPIDSubsystem {
  private final ShuffleboardTab tab = Shuffleboard.getTab("UpperPivotSubsystem");
  private final CANSparkMax upperPivotMotor = configureSparkMax(new CANSparkMax(UpperPivot.MOTOR_ID, MotorType.kBrushless));
  private final RelativeEncoder upperPivotEncoder = upperPivotMotor.getEncoder(); 
  private final ShuffleboardDouble upperPivotAngle = new ShuffleboardDouble(tab, "Upper Pivot Angle");
  private final ArmFeedforward feedForward = new ArmFeedforward(UpperPivot.KA, UpperPivot.KG, UpperPivot.KV, UpperPivot.KS);

  /**
   * Initalizes the {@link edu.wpi.first.math.controller.ProfiledPIDController}, responsible for gradually move the arm to appropriate angle setpoints.
   * Zeros all the encoders and setpoints in the event of previous data stored on them.
   * Configures the encoder's conversion factor to be in degrees.
   */
  public UpperPivotSubsystem() {
    super(new ProfiledPIDController(UpperPivot.P, UpperPivot.I, UpperPivot.D, new TrapezoidProfile.Constraints(UpperPivot.MAX_VELOCITY, UpperPivot.MAX_ACCELERATION)), 0);
    upperPivotEncoder.setPositionConversionFactor(-1 * UpperPivot.POSITION_CONVERSION_FACTOR);
    upperPivotEncoder.setPosition(0.0);
    setGoal(0.0);

    tab.add("Upper Pivot PID Controller", getController());
    tab.addNumber("Upper Pivot Encoder Position", () -> upperPivotEncoder.getPosition());
    tab.addNumber("Upper Pivot Motor Output Voltage", () -> upperPivotMotor.getAppliedOutput());
  }

  
  /**
   * Although, in reality, this function isn't necessary, for debugging, it's important to be able to quickly set the setpoint (angle) of the pivot.
   * @see edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem#periodic()
   */
  @Override public void periodic() {
    Telemetry.logDouble("Setpoint", getController().getGoal().position);
    Telemetry.logDouble("Upper Pivot Encoder Position", upperPivotEncoder.getPosition());
    Telemetry.logDouble("Upper Pivot Motor Output Voltage", upperPivotMotor.getAppliedOutput());

    setGoal(upperPivotAngle.get());
    super.periodic();
  }

  /**
   * 
   */
  public void setRawAngle(double angle) {
    upperPivotAngle.set(angle);
  }

  /**
   * With a {@link edu.wpi.first.math.controller.ArmFeedforward} used in conjunction with a ProfiledPIDController, a voltage
   * is applied to the motor to gradually approach the goal angle.
   * @see edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem#useOutput(double, edu.wpi.first.math.trajectory.TrapezoidProfile.State)
   */
  @Override public void useOutput(double output, TrapezoidProfile.State setpoint) {
    upperPivotMotor.setVoltage(output + feedForward.calculate(setpoint.position, setpoint.velocity));
  }

  /** 
   * Returns the angle of the upper pivot.
   * 
   * @return the angle of the upper pivot in degrees.
   */
  @Override public double getMeasurement() {
    return upperPivotEncoder.getPosition();
  }

  /**
   * Zeros the angle reading of the encoder.
   */
  public void resetPosition() { 
    upperPivotEncoder.setPosition(0.0);
  }
}
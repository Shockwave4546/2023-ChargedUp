package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.Constants.UpperPivot;
import frc.robot.utils.EncoderConversionFactor;
import frc.robot.utils.ShockwaveArmFeedforward;
import frc.robot.utils.shuffleboard.DebugMotorCommand;
import frc.robot.utils.shuffleboard.ShuffleboardDouble;
import frc.robot.utils.telemetry.Telemetry;

import static frc.robot.RobotContainer.tab;
import static frc.robot.utils.Utils.configureSparkMax;

/**
 * Represents the upper arm responsible for carrying the intake to set angles.
 */
public class UpperPivotSubsystem extends ProfiledPIDSubsystem {
  private static final EncoderConversionFactor RADIANS = new EncoderConversionFactor(EncoderConversionFactor.ConversionType.RADIANS);
  private final ShuffleboardTab tab = tab("UpperPivot");
  private final CANSparkMax upperPivotMotor = configureSparkMax(new CANSparkMax(UpperPivot.MOTOR_ID, MotorType.kBrushless));
  private final Encoder upperPivotEncoder = new Encoder(UpperPivot.ENCODER[0], UpperPivot.ENCODER[1]);
  private final ShuffleboardDouble upperPivotAngle = new ShuffleboardDouble(tab, "Upper Pivot Angle");
  private final ShockwaveArmFeedforward feedForward = new ShockwaveArmFeedforward(UpperPivot.KA, UpperPivot.KG, UpperPivot.KV, UpperPivot.KS);

  /**
   * Initializes the {@link edu.wpi.first.math.controller.ProfiledPIDController}, responsible for gradually move the arm to appropriate angle setpoints.
   * Zeros all the encoders and setpoints in the event of previous data stored on them.
   * Configures the encoder's conversion factor to be in degrees.
   */
  public UpperPivotSubsystem() {
    super(new ProfiledPIDController(UpperPivot.P, UpperPivot.I, UpperPivot.D, new TrapezoidProfile.Constraints(UpperPivot.MAX_VELOCITY, UpperPivot.MAX_ACCELERATION)), 0);
    RADIANS.applyTo(upperPivotEncoder);
    resetPosition();

    upperPivotMotor.setSmartCurrentLimit(40);

    setGoal(0.0);
    new DebugMotorCommand(tab, "Upper Pivot", upperPivotMotor);

    tab.add("Upper Pivot PID Controller", getController());
    tab.add("Upper Pivot Feedforward", feedForward);
    tab.addNumber("Upper Pivot Encoder Angle (Degrees)", () -> Units.radiansToDegrees(upperPivotEncoder.getDistance()));
    // tab.addNumber("Upper Pivot Motor Output Voltage", () -> upperPivotMotor.getAppliedOutput());
  }

  /**
   * Although, in reality, this function isn't necessary, for debugging, it's important to be able to quickly set the setpoint (angle) of the pivot.
   * @see edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem#periodic()
   */
  @Override public void periodic() {
    Telemetry.logDouble("Upper Pivot Setpoint", getController().getGoal().position);
    Telemetry.logDouble("Upper Pivot Encoder Angle (Degrees)", Units.radiansToDegrees(upperPivotEncoder.getDistance()));

    setGoal(Units.degreesToRadians(upperPivotAngle.get()));
    super.periodic();
  }

  /**
   * @return
   */
  public double getAngleDegrees() {
    return upperPivotAngle.get();
  }

  /**
   *
   */
  public void setRawAngle(double angle) {
    if (angle >= 110) {
      upperPivotAngle.set(110);
    } else if (angle < 0) {
      upperPivotAngle.set(0);
    } else {
      upperPivotAngle.set(angle);
    }
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
   * @return the angle of the upper pivot in radians.
   */
  @Override public double getMeasurement() {
    return upperPivotEncoder.getDistance();
  }

  /**
   * Zeros the angle reading of the encoder.
   */
  public void resetPosition() {
    upperPivotEncoder.reset();
  }
}
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.UpperPivot;
import frc.robot.utils.shuffleboard.DebugMotorCommand;
import frc.robot.utils.shuffleboard.ShuffleboardFloat;
import frc.robot.utils.telemetry.Telemetry;

import static frc.robot.RobotContainer.tab;
import static frc.robot.utils.Utils.configureSparkMax;

/**
 * Represents the upper arm responsible for carrying the intake to set angles.
 */
public class UpperPivotSubsystem extends SubsystemBase {
  private static final int MAX_ANGLE = 120;
  private static final int SPARK_MAX_SLOT_ID = 0;
  private static final int[] ENCODER = new int[] {7, 8};

  private final ShuffleboardTab tab = tab("UpperPivot");

  /**
   * SparkMax doubles are all represented as float32s.
   */
  private final ShuffleboardFloat P = new ShuffleboardFloat(tab, "P", 1.0F);
  private final ShuffleboardFloat I = new ShuffleboardFloat(tab, "I", 0.0F);
  private final ShuffleboardFloat D = new ShuffleboardFloat(tab, "D", 0.0F);
  private final ShuffleboardFloat FF = new ShuffleboardFloat(tab, "FF", 0.0F);

  private final CANSparkMax upperPivotMotor = configureSparkMax(new CANSparkMax(UpperPivot.MOTOR_ID, MotorType.kBrushless));
  public final SparkMaxPIDController pidController = upperPivotMotor.getPIDController();
  public final RelativeEncoder upperPivotEncoder = upperPivotMotor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, 8092);
  public final ShuffleboardFloat upperPivotAngle = new ShuffleboardFloat(tab, "Upper Pivot Angle");
  /**
   * Initializes the {@link edu.wpi.first.math.controller.ProfiledPIDController}, responsible for gradually move the arm to appropriate angle setpoints.
   * Zeros all the encoders and setpoints in the event of previous data stored on them.
   * Configures the encoder's conversion factor to be in degrees.
   */
  public UpperPivotSubsystem() {
    upperPivotEncoder.setPositionConversionFactor(360.0);
    upperPivotEncoder.setPosition(0.0);

    pidController.setFeedbackDevice(upperPivotEncoder);

    upperPivotMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

    upperPivotMotor.setSmartCurrentLimit(40);

    setReference(0.0);
    new DebugMotorCommand(tab, "Upper Pivot", upperPivotMotor);

    tab.addNumber("Abcdef", upperPivotEncoder::getPositionConversionFactor);
    tab.addNumber("Upper Pivot Encoder Position", upperPivotEncoder::getPosition);
    // tab.addNumber("Upper Pivot Motor Output Voltage", () -> upperPivotMotor.getAppliedOutput());
  }

  /**
   * Although, in reality, this function isn't necessary, for debugging, it's important to be able to quickly set the setpoint (angle) of the pivot.
   * @see edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem#periodic()
   */
  @Override public void periodic() {
    Telemetry.logDouble("Upper Pivot Setpoint", upperPivotAngle.get());
    Telemetry.logDouble("Upper Pivot Encoder Position", upperPivotEncoder.getPosition());

    if (P.get() != pidController.getP()) pidController.setP(P.get(), SPARK_MAX_SLOT_ID);
    if (I.get() != pidController.getI()) pidController.setI(I.get(), SPARK_MAX_SLOT_ID);
    if (D.get() != pidController.getD()) pidController.setP(D.get(), SPARK_MAX_SLOT_ID);
    if (FF.get() != pidController.getFF()) pidController.setFF(FF.get(), SPARK_MAX_SLOT_ID);
//    pidController.setSmartMotionMaxVelocity(0.0, SPARK_MAX_SLOT_ID);
//    pidController.setSmartMotionMaxAccel(0.0, SPARK_MAX_SLOT_ID);

    setReference((float) upperPivotAngle.get());
  }

  public void setReference(double angle) {
    pidController.setReference(angle, CANSparkMax.ControlType.kPosition);
  }

  /**
   * @return
   */
  public float getAngle() {
    return upperPivotAngle.get();
  }

  /**
   *
   */
  public void setRawAngle(double angle) {
    if (angle >= MAX_ANGLE) {
      upperPivotAngle.set(MAX_ANGLE);
    } else if (angle < 0) {
      upperPivotAngle.set(0);
    } else {
      upperPivotAngle.set(angle);
    }
  }

  /**
   * Zeros the angle reading of the encoder.
   */
  public void resetPosition() {
    upperPivotEncoder.setPosition(0.0);
  }
}
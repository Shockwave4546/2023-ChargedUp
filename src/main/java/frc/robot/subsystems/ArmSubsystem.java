package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Arm;
import frc.robot.utils.shuffleboard.ShuffleboardDouble;

public class ArmSubsystem extends SubsystemBase {
  private static final double DELTA_TIME = 0.02;
  private static final TrapezoidProfile.Constraints CONSTRAINTS = new TrapezoidProfile.Constraints(1.75, 0.75);
  private final ShuffleboardDouble upperPivotDegree = new ShuffleboardDouble("Upper Arm Degree", 0.0);
  // private final CANSparkMax lowerPivot = new CANSparkMax(Arm.LowerPivot.ID, MotorType.kBrushless);
  // private final RelativeEncoder lowerPivotEncoder = lowerPivot.getEncoder();
  private final CANSparkMax upperPivot = new CANSparkMax(Arm.UpperPivot.ID, MotorType.kBrushless);
  private final RelativeEncoder upperPivotEncoder = upperPivot.getEncoder();
  private TrapezoidProfile.State setpoint = new TrapezoidProfile.State();

  public ArmSubsystem() {
    // configureSparkMax(lowerPivot);
    // lowerPivotEncoder.setPositionConversionFactor(Arm.LowerPivot.COUNT_PER_DEGREE);
    // lowerPivot.getPIDController().setP(Arm.LowerPivot.P);
    // lowerPivot.getPIDController().setI(Arm.LowerPivot.I);
    // lowerPivot.getPIDController().setD(Arm.LowerPivot.D);

    configureSparkMax(upperPivot);
    upperPivotEncoder.setPositionConversionFactor(Arm.UpperPivot.COUNT_PER_DEGREE);
    upperPivot.getPIDController().setP(Arm.UpperPivot.P);
    upperPivot.getPIDController().setI(Arm.UpperPivot.I);
    upperPivot.getPIDController().setD(Arm.UpperPivot.D);
  }

  @Override public void periodic() {
    final var upperArmProfile = new TrapezoidProfile(CONSTRAINTS, new TrapezoidProfile.State(upperPivotDegree.get(), 0), setpoint);
    setpoint = upperArmProfile.calculate(DELTA_TIME);
    upperPivot.getPIDController().setReference(setpoint.position, ControlType.kPosition);
  }

  public void resetPosition() {
    // lowerPivotEncoder.setPosition(0.0);
    upperPivotEncoder.setPosition(0.0);
  }

  private void configureSparkMax(CANSparkMax sparkmax) {
    sparkmax.restoreFactoryDefaults();
    sparkmax.setIdleMode(IdleMode.kBrake);
    sparkmax.setSmartCurrentLimit(20);
    sparkmax.burnFlash();
  }
}
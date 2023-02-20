package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.Constants.Arm;
import frc.robot.utils.DataLogger;
import frc.robot.utils.Utils;
import frc.robot.utils.shuffleboard.ShuffleboardDouble;
import frc.robot.utils.shuffleboard.ShuffleboardSpeed;
import frc.robot.utils.shuffleboard.Tab;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class LowerPivotSubsystem extends ProfiledPIDSubsystem implements PivotSubsystem{ 
  private final CANSparkMax lowerPivotMotor = new CANSparkMax(24, MotorType.kBrushless);
  private final RelativeEncoder lowerPivotEncoder = lowerPivotMotor.getEncoder(); 
  private final ShuffleboardDouble lowerPivotDegree = new ShuffleboardDouble("Lower Pivot Angle");
  private final ArmFeedforward feedForward = new ArmFeedforward(Arm.LowerPivot.KA, Arm.LowerPivot.KG, Arm.LowerPivot.KV, Arm.LowerPivot.KS);

  public LowerPivotSubsystem() {
    super(new ProfiledPIDController(Arm.LowerPivot.P, Arm.LowerPivot.I, Arm.LowerPivot.D, new TrapezoidProfile.Constraints(Arm.LowerPivot.MAX_VELOCITY, Arm.LowerPivot.MAX_ACCELERATION)), 0);
    lowerPivotMotor.restoreFactoryDefaults();
    lowerPivotMotor.setInverted(true);
    // lowerPivotEncoder.setPositionConversionFactor(Arm.POSITION_CONVERSION_FACTOR);
    lowerPivotEncoder.setPositionConversionFactor((1.0F/3.0F) * 360.0F);
    lowerPivotEncoder.setPosition(0.0);
    Utils.configureSparkMax(lowerPivotMotor);
    setGoal(0.0);
    // Tab.MISC.add("Lower Pivot PID Controller", getController());
    // Tab.MISC.addNumber("Lower Pivot Encoder Position", () -> lowerPivotEncoder.getPosition());
    // Tab.MISC.addNumber("Lower Pivot Applied Voltage", () -> lowerPivotMotor.getAppliedOutput());
  }

  @Override public void resetPosition() {
    lowerPivotEncoder.setPosition(0.0);
  }

  @Override public void periodic() {
    DataLogger.logPID(this.getName(), 0, getMeasurement(), getController());
    setGoal(lowerPivotDegree.get()); 
    super.periodic();
  }

  @Override public void useOutput(double output, TrapezoidProfile.State setpoint) {
    lowerPivotMotor.setVoltage(output + feedForward.calculate(setpoint.position, setpoint.velocity));
  }

  @Override public double getMeasurement() {
    return lowerPivotEncoder.getPosition();
  }

  public void debugMotor() {
    final var speed = new ShuffleboardSpeed("Lower Pivot Speed");
    Tab.MISC.add("Run Lower Pivot", new InstantCommand(() -> lowerPivotMotor.set(speed.get()), this));
  }
}

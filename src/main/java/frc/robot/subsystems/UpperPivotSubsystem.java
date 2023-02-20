package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.Constants.Arm;
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

public class UpperPivotSubsystem extends ProfiledPIDSubsystem {
  private final CANSparkMax upperPivotMotor = new CANSparkMax(Arm.UpperPivot.MOTOR_ID, MotorType.kBrushless);
  private final RelativeEncoder upperPivotEncoder = upperPivotMotor.getEncoder(); 
  private final ShuffleboardDouble upperPivotAngle = new ShuffleboardDouble("Upper Pivot Angle");
  private final ArmFeedforward feedForward = new ArmFeedforward(Arm.UpperPivot.KA, Arm.UpperPivot.KG, Arm.UpperPivot.KV, Arm.UpperPivot.KS);

  public UpperPivotSubsystem() {
    super(new ProfiledPIDController(Arm.UpperPivot.P, Arm.UpperPivot.I, Arm.UpperPivot.D, new TrapezoidProfile.Constraints(Arm.UpperPivot.MAX_VELOCITY, Arm.UpperPivot.MAX_ACCELERATION)), 0);
    upperPivotMotor.restoreFactoryDefaults();
    upperPivotEncoder.setPositionConversionFactor(Arm.POSITION_CONVERSION_FACTOR);
    upperPivotEncoder.setPosition(0.0);
    Utils.configureSparkMax(upperPivotMotor);
    setGoal(0.0);
    Tab.MISC.add("Upper Pivot PID Controller", getController());
    Tab.MISC.addNumber("Upper Pivot Encoder Position", () -> upperPivotEncoder.getPosition());
    Tab.MISC.addNumber("Upper Pivot Applied Voltage", () -> upperPivotMotor.getAppliedOutput());
  }

  @Override public void periodic() {
    setGoal(upperPivotAngle.get());
    super.periodic();
  }

  public void resetPosition() { 
    upperPivotEncoder.setPosition(0.0);
  }

  @Override public void useOutput(double output, TrapezoidProfile.State setpoint) {
    upperPivotMotor.setVoltage(output + feedForward.calculate(setpoint.position, setpoint.velocity));
  }

  @Override public double getMeasurement() {
    return upperPivotEncoder.getPosition();
  }
  
  public void debugMotor() {
    final var speed = new ShuffleboardSpeed("Upper Pivot Speed");
    Tab.MISC.add("Run Upper Pivot", new InstantCommand(() -> upperPivotMotor.set(speed.get()), this));
  }
}
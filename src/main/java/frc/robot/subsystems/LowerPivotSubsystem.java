package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.Constants.Arm;
import frc.robot.utils.shuffleboard.ShuffleboardDouble;
import frc.robot.utils.shuffleboard.Tab;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class LowerPivotSubsystem extends ProfiledPIDSubsystem implements PivotSubsystem {
  private final CANSparkMax lowerPivotMotor = new CANSparkMax(Arm.LowerPivot.ID, MotorType.kBrushless);
  private final RelativeEncoder lowerPivotEncoder = lowerPivotMotor.getEncoder(); 
  private final ShuffleboardDouble lowerPivotDegree = new ShuffleboardDouble("Lower Pivot Angle");

  public LowerPivotSubsystem() {
    super(new ProfiledPIDController(Arm.LowerPivot.P, Arm.LowerPivot.I, Arm.LowerPivot.D, new TrapezoidProfile.Constraints(Arm.LowerPivot.MAX_VELOCITY, Arm.LowerPivot.MAX_ACCELERATION)), 0);
    lowerPivotMotor.setInverted(true);
    lowerPivotEncoder.setPositionConversionFactor(Arm.LowerPivot.COUNT_PER_DEGREE);
    lowerPivotEncoder.setPosition(0.0);
    setGoal(0.0);
    Tab.MISC.addNumber("Lower Pivot Encoder Position", () -> lowerPivotEncoder.getPosition());
    Tab.MISC.add("Lower Pivot PIDController", getController());
  }

  @Override public void resetPosition() {
    lowerPivotEncoder.setPosition(0.0);
  }

  @Override public void periodic() {
    setGoal(lowerPivotDegree.get()); 
    super.periodic();
  }

  @Override public void useOutput(double output, TrapezoidProfile.State setpoint) {
    lowerPivotMotor.setVoltage(output);
  }

  @Override public double getMeasurement() {
    return lowerPivotEncoder.getPosition();
  }
}

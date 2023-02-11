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

public class UpperPivotSubsystem extends ProfiledPIDSubsystem implements PivotSubsystem {
  private final CANSparkMax upperPivotMotor = new CANSparkMax(Arm.UpperPivot.ID, MotorType.kBrushless);
  private final RelativeEncoder upperPivotEncoder = upperPivotMotor.getEncoder(); 
  private final ShuffleboardDouble upperPivotAngle = new ShuffleboardDouble("Upper Pivot Angle");

  public UpperPivotSubsystem() {
    super(new ProfiledPIDController(Arm.UpperPivot.P, Arm.UpperPivot.I, Arm.UpperPivot.D, new TrapezoidProfile.Constraints(Arm.UpperPivot.MAX_VELOCITY, Arm.UpperPivot.MAX_ACCELERATION)), 0);
    upperPivotEncoder.setPositionConversionFactor(Arm.UpperPivot.COUNT_PER_DEGREE);
    upperPivotEncoder.setPosition(0.0);
    setGoal(0.0);
    Tab.MISC.addNumber("Upper Pivot Encoder Position", () -> upperPivotEncoder.getPosition());
  }

  @Override public void resetPosition() {
    upperPivotEncoder.setPosition(0.0);
  }
  
  @Override public void periodic() {
    setGoal(upperPivotAngle.get());
    super.periodic();
  }

  @Override public void useOutput(double output, TrapezoidProfile.State setpoint) {
    upperPivotMotor.setVoltage(output);
  }

  @Override public double getMeasurement() {
    return upperPivotEncoder.getPosition();
  }
}

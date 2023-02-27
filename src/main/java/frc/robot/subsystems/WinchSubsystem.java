package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BackWinch;
import frc.robot.Constants.FrontWinch;
import frc.robot.utils.shuffleboard.DebugMotorCommand;
import frc.robot.utils.shuffleboard.ShuffleboardDouble;

public class WinchSubsystem extends SubsystemBase {
  private static final double VERTICAL_ANGLE = 25.0;
  private final ShuffleboardTab tab = Shuffleboard.getTab("WinchSubsystem");
  private final CANSparkMax backWinchMotor = new CANSparkMax(BackWinch.MOTOR_ID, MotorType.kBrushless);
  private final ProfiledPIDController backWinchPID = new ProfiledPIDController(1.0, 0, 0, new Constraints(3.0, 1.0));

  private final CANSparkMax frontWinchMotor = new CANSparkMax(FrontWinch.MOTOR_ID, MotorType.kBrushed);
  private final ProfiledPIDController frontWinchPID = new ProfiledPIDController(1.0, 0, 0, new Constraints(3.0, 1.0));

  private final ShuffleboardDouble setpoint = new ShuffleboardDouble(tab, "Angle Setpoint");
  private final AHRS armPivotGyro = new AHRS(SerialPort.Port.kUSB);
  private double previousAngle = 0.0;

  public WinchSubsystem() {
    backWinchMotor.setInverted(true);
    resetGyro();

    new DebugMotorCommand(tab, "Run Back Winch Motor", backWinchMotor, this);
    new DebugMotorCommand(tab, "Run Front Winch Motor", frontWinchMotor, this);
    tab.addNumber("Previous Angle", () -> previousAngle);
    tab.add("Gyro", armPivotGyro);
    tab.add("Back Winch PID", backWinchPID);
    tab.add("Front Winch PID", frontWinchPID);
    tab.add("Reset Gyro", new InstantCommand(this::resetGyro, this));
  }

  @Override public void periodic() {
    // runWinches(); 
    runWinchesSync(true, armPivotGyro.getAngle());
  }

  private void runWinches() {
    final var currentAngle = armPivotGyro.getAngle();
    if (previousAngle != currentAngle) { // Prevent an issue with the angle being the same I think
      previousAngle = currentAngle; 
    }
    
    if (currentAngle < VERTICAL_ANGLE && previousAngle < currentAngle) { // Below the vertical point and approaching towards above the vertical
      runWinchesSync(true, currentAngle);
    } else if (currentAngle > VERTICAL_ANGLE && previousAngle > currentAngle) { // Above the vertical point and approaching towards below the vertical
      runWinchesSync(false, currentAngle);
    } else {
      backWinchMotor.setVoltage(backWinchPID.calculate(currentAngle, setpoint.get())); // Run back winch normally to drop the arm
    }

    previousAngle = currentAngle;
  }

  private void runWinchesSync(boolean approachingAboveVertical, double currentAngle) {
    if (approachingAboveVertical) {
      frontWinchMotor.setVoltage(frontWinchPID.calculate(currentAngle, setpoint.get())); 
      backWinchMotor.setVoltage(-backWinchPID.calculate(currentAngle, setpoint.get()));
    } else {
      frontWinchMotor.setVoltage(-frontWinchPID.calculate(currentAngle, setpoint.get())); 
      backWinchMotor.setVoltage(backWinchPID.calculate(currentAngle, setpoint.get()));
    }
  }

  public void setSetpoint(double setpoint) {
    this.setpoint.set(setpoint);
  }

  private void resetGyro() {
    armPivotGyro.reset();
  }
}
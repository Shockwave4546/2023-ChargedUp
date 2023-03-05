package frc.robot.subsystems;

import java.math.BigDecimal;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BackWinch;
import frc.robot.Constants.FrontWinch;
import frc.robot.utils.shuffleboard.DebugMotorCommand;
import frc.robot.utils.shuffleboard.ShuffleboardBoolean;
import frc.robot.utils.shuffleboard.ShuffleboardDouble;

/**
 * Roll = angle
 */
public class WinchSubsystem extends SubsystemBase {
  private static final double VERTICAL_ANGLE = 22.0;
  private static final double BACK_SPEED = 0.27;
  private static final double BACK_REVERSE_SPEED = BACK_SPEED + 0.05;
  private static final double FRONT_SPEED = 0.60;
  private static final double SETPOINT_TOLERANCE = 1.0;

  private final ShuffleboardTab tab = Shuffleboard.getTab("WinchSubsystem");
  private final CANSparkMax backWinchMotor = new CANSparkMax(BackWinch.MOTOR_ID, MotorType.kBrushless);
  // private final ProfiledPIDController backWinchPID = new ProfiledPIDController(0.1, 0, 0, new Constraints(0.2, 0.2));

  private final CANSparkMax frontWinchMotor = new CANSparkMax(FrontWinch.MOTOR_ID, MotorType.kBrushed);
  // private final ProfiledPIDController frontWinchPID = new ProfiledPIDController(7.0, 0, 0, new Constraints(10.0, 2.0));

  private final ShuffleboardDouble setpoint = new ShuffleboardDouble(tab, "Angle Setpoint");
  private final AHRS armPivotGyro = new AHRS(SerialPort.Port.kUSB);
  private double previousAngle = 0.0;
  private ShuffleboardBoolean enabled = new ShuffleboardBoolean(tab, "Enabled");
  private final LinearFilter filter = LinearFilter.movingAverage(5);

  public WinchSubsystem() {
    armPivotGyro.calibrate();
    backWinchMotor.setInverted(true);
    resetGyro();

    new DebugMotorCommand(tab, "Run Back Winch Motor", backWinchMotor, this);
    new DebugMotorCommand(tab, "Run Front Winch Motor", frontWinchMotor, this);
    tab.addNumber("Previous Angle", () -> previousAngle);
    tab.addNumber("Gyro Pitch", () -> armPivotGyro.getPitch());
    tab.addNumber("Gyro Yaw", () -> armPivotGyro.getYaw());
    tab.addNumber("Gyro Roll", () -> -armPivotGyro.getRoll());
    tab.add("Reset Gyro", new InstantCommand(this::resetGyro, this));
    frontWinchMotor.stopMotor();
    backWinchMotor.stopMotor();

  }

  @Override public void periodic() {
    if (!enabled.get()) {
      frontWinchMotor.stopMotor();
      backWinchMotor.stopMotor();
      return;
    }

    runWinches(); 
  }

  private void runWinches() {
    final var currentAngle = filter.calculate(Math.round(-armPivotGyro.getRoll() * 100.0) / 100.0); // Get to nearest tenth because we don't need 10 million decimal places.
    final var reachedSetpoint = currentAngle > setpoint.get() - SETPOINT_TOLERANCE && currentAngle < setpoint.get() + SETPOINT_TOLERANCE;

    
    // System.out.println("Setpoint: " + setpoint.get());
    // System.out.println("Range: " + (setpoint.get() - TOLERANCE) + " to " + (setpoint.get() + TOLERANCE));
    // System.out.println("Reached Setpoint: " + reachedSetpoint);
    System.out.println(previousAngle + "   " + currentAngle);

    // current: 14
    // previous: 13.8
    if (currentAngle < (previousAngle - 0.02) || currentAngle > (previousAngle + 0.02) && previousAngle != currentAngle) { // Prevent an issue with the angle being the same I think
      System.out.println("Debug");
      previousAngle = currentAngle;
    }

    if (reachedSetpoint) {
      System.out.println("Reached setpoint");
      frontWinchMotor.stopMotor();
      backWinchMotor.stopMotor();
      return;
    }

    final var needsToGoUp = setpoint.get() > currentAngle;
    System.out.println(currentAngle < VERTICAL_ANGLE);
    System.out.println(previousAngle < currentAngle);
    if (currentAngle < VERTICAL_ANGLE && needsToGoUp) { // Below the vertical point and approaching towards above the vertical
      System.out.println("Approaching above vertical");
      runWinchesSync(true);  
    } else if (currentAngle < VERTICAL_ANGLE && !needsToGoUp) { // Above the vertical point and approaching towards below the vertical
      System.out.println("Approaching below vertical");
      runWinchesSync(false);
    } else {
      if (currentAngle > setpoint.get()) {
        backWinchMotor.set(-1.0 * BACK_REVERSE_SPEED);
      } else {
        backWinchMotor.set(BACK_SPEED);
      }
    }
  }

  // setpoint: 25 degrees`
  // currentAngle: 23 degrees
  // range setpoint - 2, setpoint + 2
  private void runWinchesSync(boolean approachingAboveVertical) {    
    frontWinchMotor.set((approachingAboveVertical ? 1.0 : -1.0) * FRONT_SPEED);
    backWinchMotor.set((approachingAboveVertical ? 1.0 : -1.0) * (approachingAboveVertical ? BACK_SPEED : BACK_REVERSE_SPEED));
  }

  public void setSetpoint(double setpoint) {
    this.setpoint.set(setpoint);
  }

  private void resetGyro() {
    armPivotGyro.reset();;
  }
} 
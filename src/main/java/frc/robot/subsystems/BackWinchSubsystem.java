package frc.robot.subsystems;

import static frc.robot.utils.Utils.configureSparkMax;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BackWinch;
import frc.robot.utils.shuffleboard.DebugMotorCommand;

public class BackWinchSubsystem extends SubsystemBase {
  private final ShuffleboardTab tab = Shuffleboard.getTab("BackWinchSubsystem");
  private final CANSparkMax backWinchMotor = configureSparkMax(new CANSparkMax(BackWinch.MOTOR_ID, MotorType.kBrushless));

  public BackWinchSubsystem() {
    new DebugMotorCommand(tab, "Back Winch", backWinchMotor, this);
  } 
}
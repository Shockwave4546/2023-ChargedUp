package frc.robot.subsystems;
import static frc.robot.utils.Utils.configureSparkMax;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FrontWinch;
import frc.robot.utils.shuffleboard.DebugMotorCommand;

public class FrontWinchSubsystem extends SubsystemBase {
  private final ShuffleboardTab tab = Shuffleboard.getTab("FrontWinchSubsystem");
  private final CANSparkMax frontWinchMotor = configureSparkMax(new CANSparkMax(FrontWinch.MOTOR_ID, MotorType.kBrushless));

  public FrontWinchSubsystem() {
    new DebugMotorCommand(tab, "Front Winch", frontWinchMotor, this);
  } 
}
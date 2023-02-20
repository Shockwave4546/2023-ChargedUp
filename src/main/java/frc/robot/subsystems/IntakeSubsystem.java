package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Intake;
import frc.robot.utils.Utils;
import frc.robot.utils.shuffleboard.ShuffleboardBoolean;
import frc.robot.utils.shuffleboard.ShuffleboardSpeed;

public class IntakeSubsystem extends SubsystemBase {
  private final CANSparkMax intakeMotor = new CANSparkMax(Intake.ID, MotorType.kBrushless);
  private final ShuffleboardSpeed speed = new ShuffleboardSpeed("Intake Speed");
  private final ShuffleboardBoolean enabled = new ShuffleboardBoolean("Intake Enabled?");

  public IntakeSubsystem() {
    intakeMotor.restoreFactoryDefaults();
    Utils.configureSparkMax(intakeMotor);
  }

  @Override public void periodic() {
    if (!enabled.get()) {
      stop();
      return;
    }

    intakeMotor.set(speed.get());
  }

  public void setSpeed(double speed) {
    intakeMotor.set(speed);
  }

  public void stop() {
    intakeMotor.stopMotor();
  }
}
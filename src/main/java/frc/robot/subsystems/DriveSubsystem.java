package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.shuffleboard.ShuffleboardBoolean;
import frc.robot.utils.shuffleboard.ShuffleboardSpeed;

public class DriveSubsystem extends SubsystemBase {
  private final CANSparkMax leader = new CANSparkMax(14, MotorType.kBrushed);
  private final CANSparkMax follower = new CANSparkMax(15, MotorType.kBrushed);
  private final Encoder encoder = new Encoder(0, 1);
  private final ShuffleboardTab tab = Shuffleboard.getTab("Drive Subsystem");

  private final ShuffleboardSpeed speed = new ShuffleboardSpeed(tab, "Speed");
  private final ShuffleboardBoolean enabled = new ShuffleboardBoolean(tab, "Enabled?");

  public DriveSubsystem() {
    follower.follow(leader);
    encoder.setDistancePerPulse((6 * Math.PI) / 8192);
    tab.add(encoder);
  }

  @Override public void periodic() {
    if (!enabled.get()) {
      stop();
      return;
    }

    leader.set(speed.get());
  }

  public void stop() {
    leader.stopMotor();
  }
}
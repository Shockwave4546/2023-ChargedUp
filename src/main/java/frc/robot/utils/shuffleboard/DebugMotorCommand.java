package frc.robot.utils.shuffleboard;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DebugMotorCommand extends CommandBase {
  private final MotorController motor;
  private final ShuffleboardSpeed speed;

  public DebugMotorCommand(ShuffleboardTab tab, String name, MotorController motor, SubsystemBase subsystem) {
    this.motor = motor;
    this.speed = new ShuffleboardSpeed(tab, name + " Speed");
    tab.add("Debug " + name + " Motor", this);
    addRequirements(subsystem);
  }

  @Override public void initialize() {
    motor.stopMotor();
  }

  @Override public void execute() {
    motor.set(speed.get());
  }

  @Override public void end(boolean interrupted) {
    motor.stopMotor();
  }
}
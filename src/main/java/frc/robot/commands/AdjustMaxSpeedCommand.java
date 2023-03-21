package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AdjustMaxSpeedCommand extends CommandBase {
  private final double maxSpeed;
  private final DriveSubsystem drive;

  public AdjustMaxSpeedCommand(double maxSpeed, DriveSubsystem drive) {
    this.maxSpeed = maxSpeed;
    this.drive = drive;
  }

  @Override public void execute() {
    drive.setMaxSpeed(maxSpeed);
  }

  @Override public void end(boolean interrupted) {
    drive.setMaxSpeed(0.85);
  }
}

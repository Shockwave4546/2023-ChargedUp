package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ArmSubsystem;

public class ResetArmPositionCommand extends InstantCommand {
  public ResetArmPositionCommand(ArmSubsystem arm) {
    super(() -> arm.resetPosition(), arm);
  }
}
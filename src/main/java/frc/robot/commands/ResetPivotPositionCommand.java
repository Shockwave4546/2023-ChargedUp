package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.PivotSubsystem;

public class ResetPivotPositionCommand extends InstantCommand {
  public ResetPivotPositionCommand(PivotSubsystem pivot) {
    super(() -> pivot.resetPosition(), pivot);
  }
}
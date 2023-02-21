package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class HoldGamePieceCommand extends CommandBase {
  private final IntakeSubsystem intake;

  public HoldGamePieceCommand(IntakeSubsystem intake) {
    this.intake = intake;
  }

  @Override public void execute() {
    intake.holdGamePiece();
  }
}
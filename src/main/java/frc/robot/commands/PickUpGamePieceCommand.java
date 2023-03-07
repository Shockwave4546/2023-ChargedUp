package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem.GamePiece;

public class PickUpGamePieceCommand extends CommandBase {
  private final GamePiece piece;
  private final IntakeSubsystem intake;

  public PickUpGamePieceCommand(GamePiece piece, IntakeSubsystem intake) {
    this.piece = piece;
    this.intake = intake;
    addRequirements(intake);
  }

  @Override public void execute() {
    intake.pickUpGamePiece(piece);
  }

  @Override public void end(boolean interrupted) {
    intake.stop();
  }
}
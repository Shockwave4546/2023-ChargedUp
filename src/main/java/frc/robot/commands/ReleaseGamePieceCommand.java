package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem.GamePiece;

public class ReleaseGamePieceCommand extends CommandBase {
  private final GamePiece piece;
  private final IntakeSubsystem intake;

  public ReleaseGamePieceCommand(GamePiece piece, IntakeSubsystem intake) {
    this.piece = piece;
    this.intake = intake;
    addRequirements(intake);
  }

  public ReleaseGamePieceCommand(IntakeSubsystem intake) {
    this(null, intake);
  }

  @Override public void execute() {
    if (piece == null) intake.releaseGamePiece(); else intake.releaseGamePiece(piece);
  }

  @Override public void end(boolean interrupted) {
    intake.stop();
  }
}
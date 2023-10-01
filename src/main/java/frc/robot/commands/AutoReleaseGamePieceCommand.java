package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem.GamePiece;

public class AutoReleaseGamePieceCommand extends CommandBase {
  private final GamePiece piece;
  private final IntakeSubsystem intake;
  private long endTime;

  public AutoReleaseGamePieceCommand(GamePiece piece, IntakeSubsystem intake) {
    this.piece = piece;
    this.intake = intake;
    addRequirements(intake);
  }

  public AutoReleaseGamePieceCommand(IntakeSubsystem intake) {
    this(null, intake);
  }

  @Override
  public void initialize() {
    this.endTime = System.currentTimeMillis() + 1500;
  }

  @Override public void execute() {
    if (piece == null) intake.releaseGamePiece(); else intake.releaseGamePiece(piece);
  }

  @Override public void end(boolean interrupted) {
    intake.stop();
  }

  @Override public boolean isFinished() {
    return System.currentTimeMillis() > endTime;
  }
}
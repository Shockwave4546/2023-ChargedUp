package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.IntakeSubsystem.GamePiece;

public class PickUpGamePieceCommand extends CommandBase {
  private final GamePiece piece;
  private final IntakeSubsystem intake;
  private final LEDSubsystem led;

  public PickUpGamePieceCommand(GamePiece piece, IntakeSubsystem intake, LEDSubsystem led) {
    this.piece = piece;
    this.intake = intake;
    this.led = led;
    addRequirements(intake, led);
  }

  @Override public void execute() {
    intake.pickUpGamePiece(piece);
    if (piece == GamePiece.NOTHING) return;
    led.setStaticColor(piece == GamePiece.CONE ? Color.kYellow : Color.kPurple);
  }

  @Override public void end(boolean interrupted) {
    intake.stop();
  }
}
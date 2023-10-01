package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.PositionPreset;
import frc.robot.subsystems.UpperPivotSubsystem;

public class AutoGoToPositionPresetCommand extends CommandBase {
  private final PositionPreset preset;
  private final UpperPivotSubsystem upperPivot;

  public AutoGoToPositionPresetCommand(PositionPreset preset, UpperPivotSubsystem upperPivot) {
    this.preset = preset;
    this.upperPivot = upperPivot;
    addRequirements(upperPivot);
  }

  @Override public void initialize() {
    upperPivot.setRawAngle(preset.upperPivotAngle);
    GoToPositionPresetCommand.currentPosition = preset;
  }

  @Override public boolean isFinished() {
    return upperPivot.getController().atSetpoint();
  }
}
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.PositionPreset;
import frc.robot.subsystems.UpperPivotSubsystem;
import frc.robot.subsystems.WinchSubsystem;

public class AutoGoToPositionPresetCommand extends CommandBase {
  private final PositionPreset preset;
  private final boolean runUpperPivot;
  private final boolean runWinch;
  private final UpperPivotSubsystem upperPivot;
  private final WinchSubsystem winch;

  public AutoGoToPositionPresetCommand(PositionPreset preset, boolean runUpperPivot, boolean runWinch, UpperPivotSubsystem upperPivot, WinchSubsystem winch) {
    this.preset = preset;
    this.runUpperPivot = runUpperPivot;
    this.runWinch = runWinch;
    this.upperPivot = upperPivot;
    this.winch = winch;
    addRequirements(upperPivot, winch);
  }

  @Override public void initialize() {
    if (runUpperPivot) upperPivot.setRawAngle(preset.upperPivotAngle);
    GoToPositionPresetCommand.currentPosition = preset;
  }

  @Override public boolean isFinished() {
    if (runWinch && runUpperPivot) {
      return winch.atSetpoint() && upperPivot.getController().atSetpoint();
    } else if (runWinch) {
      return winch.atSetpoint();
    } else if (runUpperPivot) {
      return upperPivot.getController().atSetpoint();
    } else {
      throw new UnsupportedOperationException("??????????????????");
    }
  }
}
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.PositionPreset;
import frc.robot.subsystems.UpperPivotSubsystem;
import frc.robot.subsystems.WinchSubsystem;

public class GoToPositionPresetCommand extends InstantCommand {
  public static PositionPreset currentPosition = PositionPreset.STARTING;

  public GoToPositionPresetCommand(PositionPreset preset, UpperPivotSubsystem upperPivot) {
    super(() -> {
      upperPivot.setRawAngle(preset.upperPivotAngle);
      currentPosition = preset;
    }, upperPivot);
  }
}
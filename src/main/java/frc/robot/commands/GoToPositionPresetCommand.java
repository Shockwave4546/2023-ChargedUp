package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.UpperPivotSubsystem;
import frc.robot.subsystems.WinchSubsystem;

public class GoToPositionPresetCommand extends InstantCommand {
  public GoToPositionPresetCommand(PositionPreset preset, UpperPivotSubsystem upperPivot, WinchSubsystem winch) {
    super(() -> {
      upperPivot.setRawAngle(preset.upperPivotAngle);
      winch.setAngle(preset.winchAngle);
    }, upperPivot, winch);
  }

  public enum PositionPreset {
    HUMAN_PLAYER_PICKUP(1.0, 1.0),
    FLOOR(0.0, 73.0),
    HIGH_CONE(90.0, 23.0),
    MID_CONE(1.0, 1.0),
    HIGH_CUBE(1.0, 1.0),
    MID_CUBE(1.0, 1.0),
    STARTING(0.0, 3.0);
    
    public final double upperPivotAngle;
    public final double winchAngle;

    PositionPreset(double upperPivotAngle, double winchAngle) {
      this.upperPivotAngle = upperPivotAngle;
      this.winchAngle = winchAngle;
    }
  }
}
package frc.robot;

import frc.robot.commands.ResetPivotPositionCommand;
import frc.robot.subsystems.LowerPivotSubsystem;
import frc.robot.subsystems.UpperPivotSubsystem;
import frc.robot.utils.shuffleboard.Tab;

public class RobotContainer {
  private final LowerPivotSubsystem lowerPivot = new LowerPivotSubsystem();
  private final UpperPivotSubsystem upperPivot = new UpperPivotSubsystem();

  public RobotContainer() {
    lowerPivot.enable();
    configureDebugTab();
  }

  private void configureDebugTab() {
    Tab.DEBUG.add("Reset Lower Pivot Position", new ResetPivotPositionCommand(lowerPivot));
    Tab.DEBUG.add("Reset Upper Pivot Position", new ResetPivotPositionCommand(upperPivot));
  }
}
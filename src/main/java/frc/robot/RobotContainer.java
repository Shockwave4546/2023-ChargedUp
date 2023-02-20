package frc.robot;

import edu.wpi.first.wpilibj.DataLogManager;
import frc.robot.commands.ResetPivotPositionCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LowerPivotSubsystem;
import frc.robot.subsystems.UpperPivotSubsystem;
import frc.robot.utils.shuffleboard.Tab;

public class RobotContainer {
  private final DriveSubsystem drive = new DriveSubsystem();
  // private final IntakeSubsystem intake = new IntakeSubsystem();
  // private final LowerPivotSubsystem lowerPivot = new LowerPivotSubsystem();
  // private final UpperPivotSubsystem upperPivot = new UpperPivotSubsystem();

  public RobotContainer() {
    // lowerPivot.enable();
    // upperPivot.enable();
    configureDebugTab();
    // lowerPivot.debugMotor();
    // upperPivot.debugMotor();
  }

  private void configureDebugTab() {
    // Tab.DEBUG.add("Reset Lower Pivot Position", new ResetPivotPositionCommand(lowerPivot));
    // Tab.DEBUG.add("Reset Upper Pivot Position", new ResetPivotPositionCommand(upperPivot));
  }
}
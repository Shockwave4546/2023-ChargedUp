package frc.robot;

import frc.robot.commands.ResetArmPositionCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.utils.shuffleboard.Tab;

public class RobotContainer {
  private final ArmSubsystem arm = new ArmSubsystem();

  public RobotContainer() {
    configureDebugTab();
  }

  private void configureDebugTab() {
    Tab.DEBUG.add("Reset Arm Position", new ResetArmPositionCommand(arm));
  }
}
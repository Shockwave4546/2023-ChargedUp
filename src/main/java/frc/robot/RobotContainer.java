package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.IO;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LowerPivotSubsystem;
import frc.robot.subsystems.UpperPivotSubsystem;

public class RobotContainer {
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final LowerPivotSubsystem lowerPivot = new LowerPivotSubsystem();
  private final UpperPivotSubsystem upperPivot = new UpperPivotSubsystem();
  protected final DriveSubsystem drive = new DriveSubsystem();
  private final AutonomousManager auto = new AutonomousManager(drive);
  protected final CommandXboxController driveController = new CommandXboxController(IO.DRIVE_CONTROLLER_PORT);
  private final CommandXboxController operatorController = new CommandXboxController(IO.OPERATOR_CONTROLLER_PORT);

  public RobotContainer() {
    configureAuto();
    // lowerPivot.enable();
    // upperPivot.enable();
  }

  private void configureAuto() {
    auto.addPath("StriaghtLine");
  }

  private void configureControllers() {

  }
} 
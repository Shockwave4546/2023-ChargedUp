package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.IO;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.UpperPivotSubsystem;
import frc.robot.subsystems.IntakeSubsystem.GamePiece;

public class RobotContainer {
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final UpperPivotSubsystem upperPivot = new UpperPivotSubsystem();
  protected final DriveSubsystem drive = new DriveSubsystem();
  private final AutonomousManager auto = new AutonomousManager(drive);
  protected final CommandXboxController driveController = new CommandXboxController(IO.DRIVE_CONTROLLER_PORT);
  private final CommandXboxController operatorController = new CommandXboxController(IO.OPERATOR_CONTROLLER_PORT);

  public RobotContainer() {
    configureAuto();
    configureControllers();
    // lowerPivot.enable();
  }

  private void configureAuto() {
    auto.addPath("StraightLine");
  }

  private void configureControllers() {
    operatorController.leftBumper().onTrue(new InstantCommand(() -> intake.pickUpGamePiece(GamePiece.CONE)));
    operatorController.rightBumper().onTrue(new InstantCommand(() -> intake.pickUpGamePiece(GamePiece.CUBE)));
    operatorController.rightTrigger().onTrue(new InstantCommand(() -> intake.releaseGamePiece()));

    // TODO: add static setpoints for each button
  }
} 
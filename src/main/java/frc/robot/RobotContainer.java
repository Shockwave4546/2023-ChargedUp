package frc.robot;

import com.pathplanner.lib.PathConstraints;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.IO;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.UpperPivotSubsystem;
import frc.robot.subsystems.IntakeSubsystem.GamePiece;

/**
 * 
 */
public class RobotContainer {
  public static final PowerDistribution PDP = new PowerDistribution();
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final UpperPivotSubsystem upperPivot = new UpperPivotSubsystem();
  protected final DriveSubsystem drive = new DriveSubsystem();
  private final AutonomousManager auto = new AutonomousManager(drive);
  protected final CommandXboxController driveController = new CommandXboxController(IO.DRIVE_CONTROLLER_PORT);
  private final CommandXboxController operatorController = new CommandXboxController(IO.OPERATOR_CONTROLLER_PORT);

  /**
   * 
   */
  public RobotContainer() {
    configureAuto();
    configureControllers();
    // lowerPivot.enable();
  }

  /**
   * 
   */
  private void configureAuto() {
    auto.addPath("StraightLine3Meters", new PathConstraints(3.0, 1.0));
    auto.addPath("ChargeStation", new PathConstraints(3.0, 1.0));
    auto.addPath("Mobility", new PathConstraints(3.0, 1.0));
    auto.addPath("Top2Piece", new PathConstraints(3.0, 1.0));
  }

  /**
   * 
   */
  private void configureControllers() {
    operatorController.leftBumper().onTrue(new InstantCommand(() -> intake.pickUpGamePiece(GamePiece.CONE)));
    operatorController.rightBumper().onTrue(new InstantCommand(() -> intake.pickUpGamePiece(GamePiece.CUBE)));
    operatorController.rightTrigger().onTrue(new InstantCommand(() -> intake.releaseGamePiece()));

    // TODO: add static setpoints for each button
  }
} 
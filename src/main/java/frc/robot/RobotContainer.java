package frc.robot;

import java.util.HashMap;
import java.util.Map;

import com.pathplanner.lib.PathConstraints;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.IO;
import frc.robot.commands.AutoBalanceCommand;
import frc.robot.commands.GoToPositionPresetCommand;
import frc.robot.commands.PickUpGamePieceCommand;
import frc.robot.commands.ReleaseGamePieceCommand;
import frc.robot.commands.GoToPositionPresetCommand.PositionPreset;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.UpperPivotSubsystem;
import frc.robot.subsystems.WinchSubsystem;
import frc.robot.subsystems.IntakeSubsystem.GamePiece;
import frc.robot.utils.shuffleboard.GlobalTab;

/**
 * 
 */
public class RobotContainer {
  public static final PowerDistribution PDP = new PowerDistribution();
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final UpperPivotSubsystem upperPivot = new UpperPivotSubsystem();
  // protected final DriveSubsystem drive = new DriveSubsystem();
  // private final AutonomousManager auto = new AutonomousManager(drive, true);
  private final LEDSubsystem led = new LEDSubsystem();
  private final WinchSubsystem winch = new WinchSubsystem();
  // protected final CommandXboxController driveController = new CommandXboxController(IO.DRIVE_CONTROLLER_PORT);
  private final CommandXboxController operatorController = new CommandXboxController(IO.OPERATOR_CONTROLLER_PORT);

  /**
   * 
   */
  public RobotContainer() {
    GlobalTab.DEBUG.add("PDP", PDP);
    configureAuto();
    configureControllers();
    upperPivot.enable();
  }

  /**
   * 
   */
  private void configureAuto() {
    // auto.addPath("ChargeStation", new PathConstraints(3.0, 1.0), Map.of(
    //   "GoHighConePosition", new GoToPositionPresetCommand(PositionPreset.HIGH_CONE, upperPivot, winch),
    //   "ReleaseCone", new ReleaseGamePieceCommand(GamePiece.CONE, intake),
    //   "AutoBalance", new AutoBalanceCommand(drive)
    // ));

    // auto.addPath("StraightLine3Meters", new PathConstraints(3.0, 1.0), Map.of(
    //   "Wait3Command", new WaitCommand(3),
    //   "PrintCommand", new PrintCommand("Start Print Command"),
    //   "Wait2Command", new WaitCommand(2),
    //   "EndPrintCommand", new PrintCommand("End Print Command")
    // ));

    // auto.addPath("Mobility", new PathConstraints(3.0, 1.0), Map.of());
    // auto.addPath("Top2Piece", new PathConstraints(3.0, 1.0), Map.of());
  }

  /**
   * 
   */
  private void configureControllers() {
    operatorController.leftBumper().whileTrue(new PickUpGamePieceCommand(GamePiece.CONE, intake, led));
    operatorController.rightBumper().whileTrue(new PickUpGamePieceCommand(GamePiece.CUBE, intake, led));
    operatorController.rightTrigger().whileTrue(new ReleaseGamePieceCommand(intake));


    // TODO: add static setpoints for each button
  }
} 
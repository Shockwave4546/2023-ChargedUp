package frc.robot;

import java.util.Map;

import com.pathplanner.lib.PathConstraints;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.IO;
import frc.robot.commands.AdjustMaxSpeedCommand;
import frc.robot.commands.AutoGoToPositionPresetCommand;
import frc.robot.commands.GoToPositionPresetCommand;
import frc.robot.commands.PickUpGamePieceCommand;
import frc.robot.commands.ReleaseGamePieceCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.UpperPivotSubsystem;
import frc.robot.subsystems.WinchSubsystem;
import frc.robot.subsystems.IntakeSubsystem.GamePiece;

/**
 * 
 */
public class RobotContainer {
  // public static final PowerDistribution PDP = new PowerDistribution();
  private final IntakeSubsystem intake = new IntakeSubsystem();
  public final UpperPivotSubsystem upperPivot = new UpperPivotSubsystem();
  protected final DriveSubsystem drive = new DriveSubsystem();
  public final AutonomousManager auto = new AutonomousManager(drive, true);
  private final LEDSubsystem led = new LEDSubsystem();
  public final WinchSubsystem winch = new WinchSubsystem();
  public final WinchSubsystem winch = new WinchSubsystem();
  protected final CommandXboxController driveController = new CommandXboxController(IO.DRIVE_CONTROLLER_PORT);
  protected final CommandXboxController operatorController = new CommandXboxController(IO.OPERATOR_CONTROLLER_PORT);
  private final UsbCamera camera = CameraServer.startAutomaticCapture();

  /**
   * 
   */
  public RobotContainer() {
    drive.setMaxSpeed(0.85);
    // GlobalTab.DEBUG.add("PDP", PDP);
    camera.setResolution(1280, 720);
    camera.setFPS(30);
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

    // auto.addPath("Mobility", new PathConstraints(3.0, 1.0), false, Map.of());
    auto.addPath("OnePiece", new PathConstraints(3.0, 1.0), true, Map.of(
      "HighConePosition", new AutoGoToPositionPresetCommand(PositionPreset.HIGH_CONE, true, false, upperPivot, winch)
        .andThen(new AutoGoToPositionPresetCommand(PositionPreset.HIGH_CONE, false, true, upperPivot, winch)),
      "ReleaseCone", new ReleaseGamePieceCommand(GamePiece.CONE, intake)
    ));
    // auto.addPath("Top2Piece", new PathConstraints(3.0, 1.0), Map.of());
  }

  /**
   * 
   */
  private void configureControllers() {
    driveController.leftBumper().whileTrue(new AdjustMaxSpeedCommand(0.35, drive));

    operatorController.leftBumper().whileTrue(new PickUpGamePieceCommand(GamePiece.CONE, intake, led));
    operatorController.rightBumper().whileTrue(new PickUpGamePieceCommand(GamePiece.CUBE, intake, led));
    operatorController.rightTrigger().whileTrue(new ReleaseGamePieceCommand(intake));

    operatorController.a().onTrue(new GoToPositionPresetCommand(PositionPreset.HIGH_CONE, upperPivot, winch));
    operatorController.b().onTrue(new GoToPositionPresetCommand(PositionPreset.STARTING, upperPivot, winch));
    operatorController.x().onTrue(new GoToPositionPresetCommand(PositionPreset.FLOOR_CONE, upperPivot, winch));
    operatorController.y().onTrue(new GoToPositionPresetCommand(PositionPreset.FLOOR_CUBE, upperPivot, winch));

    operatorController.povLeft().onTrue(new GoToPositionPresetCommand(PositionPreset.CUBE, upperPivot, winch));
    operatorController.povUp().onTrue(new GoToPositionPresetCommand(PositionPreset.HUMAN_PLAYER_PICKUP_CONE, upperPivot, winch));
    operatorController.povRight().onTrue(new GoToPositionPresetCommand(PositionPreset.HUMAN_PLAYER_PICKUP_CUBE, upperPivot, winch));
    operatorController.povDown().onTrue(new GoToPositionPresetCommand(PositionPreset.DRIVING, upperPivot, winch));
  }
} 
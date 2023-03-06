package frc.robot;

import java.util.HashMap;

import com.pathplanner.lib.PathConstraints;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.IO;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.UpperPivotSubsystem;
import frc.robot.subsystems.VisionSubsystem;
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
  // private final LEDSubsystem led = new LEDSubsystem();
  private final VisionSubsystem vision = new VisionSubsystem();
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
    // auto.addPath("StraightLine3Meters", new PathConstraints(3.0, 1.0), new HashMap<>());
    // auto.addPath("ChargeStation", new PathConstraints(3.0, 1.0), new HashMap<>());
    // auto.addPath("Mobility", new PathConstraints(3.0, 1.0), new HashMap<>());
    // auto.addPath("Top2Piece", new PathConstraints(3.0, 1.0), new HashMap<>());
  }

  /**
   * 
   */
  private void configureControllers() {
    operatorController.leftBumper().whileTrue(new FunctionalCommand(() -> {} , () -> { intake.pickUpGamePiece(GamePiece.CONE); } , ($) -> { intake.stop(); } , () -> false , intake));
    operatorController.rightBumper().whileTrue(new FunctionalCommand(() -> {} , () -> { intake.pickUpGamePiece(GamePiece.CUBE); } , ($) -> { intake.stop(); } , () -> false , intake));
    operatorController.rightTrigger().whileTrue(new FunctionalCommand(() -> {} , () -> { intake.releaseGamePiece(); } , ($) -> { intake.stop(); } , () -> false , intake));


    // TODO: add static setpoints for each button
  }
} 
package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.pathplanner.lib.PathConstraints;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.BackWinch;
import frc.robot.Constants.IO;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.UpperPivotSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.WinchSubsystem;
import frc.robot.subsystems.IntakeSubsystem.GamePiece;
import frc.robot.utils.shuffleboard.DebugMotorCommand;
import frc.robot.utils.shuffleboard.GlobalTab;

/**
 * 
 */
public class RobotContainer {
  // public static final PowerDistribution PDP = new PowerDistribution();
  // private final IntakeSubsystem intake = new IntakeSubsystem();
  // private final UpperPivotSubsystem upperPivot = new UpperPivotSubsystem();
  protected final DriveSubsystem drive = new DriveSubsystem();
  // private final AutonomousManager auto = new AutonomousManager(drive, true);
  // private final LEDSubsystem led = new LEDSubsystem();
  // private final VisionSubsystem vision = new VisionSubsystem();
  // private final WinchSubsystem winch = new WinchSubsystem();
  // protected final CommandXboxController driveController = new CommandXboxController(IO.DRIVE_CONTROLLER_PORT);
  private final CommandXboxController operatorController = new CommandXboxController(IO.OPERATOR_CONTROLLER_PORT);

  /**
   * 
   */
  public RobotContainer() {
    // new DebugMotorCommand(GlobalTab.DEBUG, "Dehgifr", a);
    // GlobalTab.DEBUG.add("PDP", PDP);
    configureAuto();
    configureControllers();
    // upperPivot.enable();
  }

  /**
   * 
   */
  private void configureAuto() {
    // auto.addPath("StraightLine3Meters", new PathConstraints(3.0, 1.0));
    // auto.addPath("ChargeStation", new PathConstraints(3.0, 1.0));
    // auto.addPath("Mobility", new PathConstraints(3.0, 1.0));
    // auto.addPath("Top2Piece", new PathConstraints(3.0, 1.0));
  }

  /**
   * 
   */
  private void configureControllers() {
    // final var intakeConeCommand = new FunctionalCommand(() -> {} , () -> { intake.pickUpGamePiece(GamePiece.CONE); } , (end) -> { intake.stop(); } , () -> false , intake);
    // final var intakeCubeCommand = new FunctionalCommand(() -> {} , () -> { intake.pickUpGamePiece(GamePiece.CUBE); } , (end) -> { intake.stop(); } , () -> false , intake);
    // final var releaseGamePieceCommand = new FunctionalCommand(() -> {} , () -> { intake.releaseGamePiece(); } , (end) -> { intake.stop(); } , () -> false , intake);

    // operatorController.leftBumper().whileTrue(intakeConeCommand);
    // operatorController.rightBumper().whileTrue(intakeCubeCommand);
    // operatorController.rightTrigger().whileTrue(releaseGamePieceCommand);


    // TODO: add static setpoints for each button
  }
} 

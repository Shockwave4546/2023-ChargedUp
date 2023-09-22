package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.utils.CheesyDriveHelper;

/**
 * 
 */
public class CheesyDriveCommand extends CommandBase {
  private static final double DEADBAND = 0.02;
  private static final double QUICK_TURN_THRESHOLD = 0.2;  
  private final DriveSubsystem drive;
  private final CommandXboxController controller;
  private final CheesyDriveHelper helper = new CheesyDriveHelper();

  /**
   * @param drive
   * @param controller
   */
  public CheesyDriveCommand(DriveSubsystem drive, CommandXboxController controller) {
    this.drive = drive;
    this.controller = controller;
    addRequirements(drive);
  }

  /**
   * 
   */
  @Override public void initialize() {
    drive.stop(); 
  }

  /**
   * 
   */
  @Override public void execute() {
    final var rotateValue = CheesyDriveHelper.handleDeadband(controller.getRightX() * 0.5, DEADBAND);
    final var moveValue = CheesyDriveHelper.handleDeadband(controller.getLeftY() * 1.00, DEADBAND);
    final var quickTurn = (moveValue < QUICK_TURN_THRESHOLD && moveValue > -QUICK_TURN_THRESHOLD);
    final var speeds = helper.cheesyDrive(moveValue, rotateValue, quickTurn);
    drive.tankDrive(speeds.getFirst(), speeds.getSecond());
  }

  /**
   * @param interrupted
   */
  @Override public void end(boolean interrupted) {
    drive.stop();
  }
}
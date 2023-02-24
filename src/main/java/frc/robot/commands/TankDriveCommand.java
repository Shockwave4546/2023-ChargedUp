package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DriveSubsystem;

/**
 * 
 */
public class TankDriveCommand extends CommandBase {
  private final DriveSubsystem drive;
  private final CommandXboxController controller;

  /**
   * @param drive
   * @param controller
   */
  public TankDriveCommand(DriveSubsystem drive, CommandXboxController controller) {
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
    drive.tankDrive(controller.getLeftY(), controller.getRightY());
  }

  /**
   * @param interrupted
   */
  @Override public void end(boolean interrupted) {
    drive.stop();
  }
}
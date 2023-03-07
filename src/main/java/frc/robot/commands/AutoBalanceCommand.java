package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.Drive;
import frc.robot.subsystems.DriveSubsystem;

public class AutoBalanceCommand extends PIDCommand {
  public AutoBalanceCommand(DriveSubsystem drive) {
    super(
      new PIDController(Drive.BALANCE_P, Drive.BALANCE_I, Drive.BALANCE_D), 
      drive::getGyroAngle, 0.0, 
      (output) -> { drive.tankDriveVolts(output, output); }, 
      drive
    );

    drive.tab.add("Balance PID Controller", getController());
  }
}
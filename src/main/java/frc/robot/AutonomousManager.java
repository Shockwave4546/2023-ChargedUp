package frc.robot;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.commands.PPRamseteCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.Drive;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.utils.shuffleboard.GlobalTab;

public class AutonomousManager {
  private final SendableChooser<Command> chooser = new SendableChooser<>();
  private final DriveSubsystem drive;

  public AutonomousManager(DriveSubsystem drive) {
    this.drive = drive;
    GlobalTab.MATCH.add("Autonomous Chooser", chooser).withSize(3, 2);
    chooser.setDefaultOption("Do Nothing", new InstantCommand());
  }

  public void addPath(String pathName) {
    chooser.addOption(pathName, loadPathPlannerTrajectoryToRamseteCommand(pathName, true));
  }

  // TODO: Implement with RamseteAutoBuilder
  private Command loadPathPlannerTrajectoryToRamseteCommand(String fileName, boolean resetOdometry) {
    final var path = PathPlanner.loadPath(fileName, new PathConstraints(3, 1));
    final var ramseteCommand = new PPRamseteCommand(
      path, 
      drive::getPose, 
      new RamseteController(Drive.RAMSETE_B, Drive.RAMSETE_ZETA), 
      new SimpleMotorFeedforward(Drive.KS_VOLTS, Drive.KV_VOLT_SECONDS_PER_METER, Drive.KA_VOLT_SECONDS_SQUARED_PER_METER), 
      drive.getKinematics(), 
      drive::getWheelSpeeds, 
      new PIDController(Drive.P_DRIVE_VELOCITY, 0, 0), 
      new PIDController(Drive.P_DRIVE_VELOCITY, 0, 0), 
      drive::tankDriveVolts,
      true,
      drive
    );

    final var stopCommand = new InstantCommand(() -> drive.stop());
    return resetOdometry ? new SequentialCommandGroup(new InstantCommand(() -> drive.resetOdometry(path.getInitialPose())), ramseteCommand, stopCommand) : ramseteCommand.andThen(stopCommand);
  }
}
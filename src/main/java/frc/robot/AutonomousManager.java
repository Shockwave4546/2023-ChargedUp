package frc.robot;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.RamseteAutoBuilder;
import com.pathplanner.lib.server.PathPlannerServer;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.Drive;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.utils.shuffleboard.GlobalTab;

import java.util.Map;

/**
 * 
 */
public class AutonomousManager {
  private final SendableChooser<Command> chooser = new SendableChooser<>();
  private final DriveSubsystem drive;

  /**
   * @param drive
   */
  public AutonomousManager(DriveSubsystem drive, boolean debug) {
    this.drive = drive;
    if (debug) PathPlannerServer.startServer(5811);
    GlobalTab.MATCH.add("Autonomous Chooser", chooser).withSize(3, 2);
    chooser.setDefaultOption("Do Nothing", new InstantCommand());
  }

  /**
   * @param pathName
   */
  public void addPath(String pathName, PathConstraints constraints, boolean reversed, Map<String, Command> eventMap) {
    chooser.addOption(pathName, loadPathPlannerTrajectoryToRamseteCommand(pathName, constraints, reversed, eventMap));
  }

  /**
   * 
   */
  public void executeRoutine() {
    chooser.getSelected().schedule();
  }

  /**
   * @param fileName
   * @param constraints
   * @param eventMap
   * @return
   */
  private Command loadPathPlannerTrajectoryToRamseteCommand(String fileName, PathConstraints constraints, boolean reversed, Map<String, Command> eventMap) {
    final var path = PathPlanner.loadPath(fileName, constraints, reversed);
    final var autoBuilder = new RamseteAutoBuilder(
      drive::getPose, 
      drive::resetOdometry, 
      new RamseteController(Drive.RAMSETE_B, Drive.RAMSETE_ZETA), 
      drive.getKinematics(),
      new SimpleMotorFeedforward(Drive.KS_VOLTS, Drive.KV_VOLT_SECONDS_PER_METER, Drive.KA_VOLT_SECONDS_SQUARED_PER_METER), 
      drive::getWheelSpeeds, 
      new PIDConstants(Drive.P_DRIVE_VELOCITY, 0.0, 0.0),
      drive::tankDriveVolts,
      eventMap,
      true, 
      drive);

    return autoBuilder.fullAuto(path).andThen(new InstantCommand(drive::stop));
  }
}
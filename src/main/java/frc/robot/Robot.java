package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * 
 */
public class Robot extends TimedRobot {
  private final RobotContainer container = new RobotContainer();

  /* (non-Javadoc)
   * @see edu.wpi.first.wpilibj.IterativeRobotBase#robotInit()
   */
  @Override public void robotInit() {
 
  }

  /* (non-Javadoc)
   * @see edu.wpi.first.wpilibj.IterativeRobotBase#robotPeriodic()
   */
  @Override public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  /* (non-Javadoc)
   * @see edu.wpi.first.wpilibj.IterativeRobotBase#autonomousInit()
   */
  @Override public void autonomousInit() {
    // container.auto.executeRoutine();
  }

  /* (non-Javadoc)
   * @see edu.wpi.first.wpilibj.IterativeRobotBase#autonomousPeriodic()
   */
  @Override public void autonomousPeriodic() {

  }
  

  /* (non-Javadoc)
   * @see edu.wpi.first.wpilibj.IterativeRobotBase#teleopInit()
   */
  @Override public void teleopInit() {
    container.drive.initTeleop(container.driveController);
  }

  /* (non-Javadoc)
   * @see edu.wpi.first.wpilibj.IterativeRobotBase#teleopPeriodic()
   */
  @Override public void teleopPeriodic() {

  }
}
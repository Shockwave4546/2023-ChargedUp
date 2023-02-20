package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private final RobotContainer container = new RobotContainer();

  @Override public void robotInit() {
 
  }

  @Override public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override public void autonomousInit() {

  }

  @Override public void autonomousPeriodic() {

  }
  

  @Override public void teleopInit() {
    container.drive.initTeleop(container.driveController);
  }

  @Override public void teleopPeriodic() {

  }
}
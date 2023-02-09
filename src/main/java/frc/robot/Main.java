package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

public final class Main {
  private Main() {
    throw new UnsupportedOperationException("The main class should never get instantiated!");
  }

  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
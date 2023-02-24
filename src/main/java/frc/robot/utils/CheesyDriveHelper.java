package frc.robot.utils;

import edu.wpi.first.math.Pair;

/**
 * Source: https://github.com/Team254/FRC-2016-Public/blob/811d5e11867ef7c659f98e01f48e3720d8724df1/src/com/team254/frc2016/CheesyDriveHelper.java
 */
public class CheesyDriveHelper {
  private static final double SPEED_DEADBAND = 0.02;
  private static final double ROT_DEADBAND = 0.02;
  private static final double TURN_SENSITIVTY = 1.0;
  private double quickStopAccumulator;

  /**
   * @param speed
   * @param rotValue
   * @param isQuickTurn
   * @return
   */
  public Pair<Double, Double> cheesyDrive(double speed, double rotValue, boolean isQuickTurn) {
    speed = handleDeadband(speed, SPEED_DEADBAND);
    rotValue = handleDeadband(rotValue, ROT_DEADBAND);

    double overPower;
    double angularPower;

    if (isQuickTurn) {
      if (Math.abs(speed) < 0.2) {
        final var alpha = 0.1;
        quickStopAccumulator = (1 - alpha) * quickStopAccumulator + alpha * limit(rotValue, 1.0) * 2;
      }
      overPower = 1.0;
      angularPower = rotValue;
    } else {
      overPower = 0.0;
      angularPower = Math.abs(speed) * rotValue * TURN_SENSITIVTY - quickStopAccumulator;
      if (quickStopAccumulator > 1) {
        quickStopAccumulator -= 1;
      } else if (quickStopAccumulator < -1) {
        quickStopAccumulator += 1;
      } else {
        quickStopAccumulator = 0.0;
      }
    }

    var rightSpeed = speed - angularPower;
    var leftSpeed = speed + angularPower;
    if (leftSpeed > 1.0) {
      rightSpeed -= overPower * (leftSpeed - 1.0);
      leftSpeed = 1.0;
    } else if (rightSpeed > 1.0) {
      leftSpeed -= overPower * (rightSpeed - 1.0);
      rightSpeed = 1.0;
    } else if (leftSpeed < -1.0) {
      rightSpeed += overPower * (-1.0 - leftSpeed);
      leftSpeed = -1.0;
    } else if (rightSpeed < -1.0) {
      leftSpeed += overPower * (-1.0 - rightSpeed);
      rightSpeed = -1.0;
    }

    return new Pair<Double,Double>(leftSpeed, rightSpeed);
  }

  /**
   * @param val
   * @param deadband
   * @return
   */
  public static double handleDeadband(double val, double deadband) {
    return Math.abs(val) > Math.abs(deadband) ? val : 0.0;
  }

  /**
   * @param val
   * @param limit
   * @return
   */
  private static double limit(double val, double limit) {
    return Math.abs(val) < limit ? val : limit * (val < 0 ? -1 : 1);
  }
}
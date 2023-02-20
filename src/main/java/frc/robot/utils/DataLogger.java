package frc.robot.utils;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.wpilibj.DataLogManager;

public class DataLogger {
  private static final DataLog LOG = DataLogManager.getLog();

  static {
    DataLogManager.start();
  }

  public static void logPID(String subsystem, int trial, double measurement, PIDController controller) {
    final var name = trial + "/" + subsystem + "/PID";
    new DoubleLogEntry(LOG, name + "/P").append(controller.getP());
    new DoubleLogEntry(LOG, name + "/I").append(controller.getI());
    new DoubleLogEntry(LOG, name + "/D").append(controller.getD());
    new DoubleLogEntry(LOG, name + "/Setpoint").append(controller.getSetpoint());
    new DoubleLogEntry(LOG, name + "/Measurement").append(measurement);
  } 

  public static void logPID(String subsystem, int trial, double measurement, ProfiledPIDController controller) {
    final var name = trial + "/" + subsystem + "/ProfiledPID";
    new DoubleLogEntry(LOG, name + "/P").append(controller.getP());
    new DoubleLogEntry(LOG, name + "/I").append(controller.getI());
    new DoubleLogEntry(LOG, name + "/D").append(controller.getD());
    new DoubleLogEntry(LOG, name + "/Position Goal").append(controller.getGoal().position);
    new DoubleLogEntry(LOG, name + "/Velocity Goal").append(controller.getGoal().velocity);
    new DoubleLogEntry(LOG, name + "/Measurement").append(measurement);
  } 
}
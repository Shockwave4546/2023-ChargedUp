package frc.robot.utils.telemetry;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DataLogEntry;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.util.datalog.IntegerLogEntry;
import edu.wpi.first.util.datalog.StringLogEntry;
import edu.wpi.first.wpilibj.DataLogManager;

/**
 * 
 */
public class Telemetry {
  private static final DataLog LOG = DataLogManager.getLog();
  private static final Map<String, DataLogEntry> CACHE = new HashMap<>();

  /**
   * @param name
   * @param value
   */
  public static void logDouble(String name, double value) {
    if (!CACHE.containsKey(name)) CACHE.put(name, new DoubleLogEntry(LOG, name));
    ((DoubleLogEntry) CACHE.get(name)).append(value);
  }

  /**
   * @param name
   * @param value
   */
  public static void logInt(String name, int value) {
    if (!CACHE.containsKey(name)) CACHE.put(name, new IntegerLogEntry(LOG, name));
    ((IntegerLogEntry) CACHE.get(name)).append(value);
  }

  /**
   * @param name
   * @param value
   */
  public static void logBoolean(String name, boolean value) {
    if (!CACHE.containsKey(name)) CACHE.put(name, new BooleanLogEntry(LOG, name));
    ((BooleanLogEntry) CACHE.get(name)).append(value);
  }

  /**
   * @param name
   * @param value
   */
  public static void logString(String name, String value) {
    if (!CACHE.containsKey(name)) CACHE.put(name, new StringLogEntry(LOG, name));
    ((StringLogEntry) CACHE.get(name)).append(value);
  }
}
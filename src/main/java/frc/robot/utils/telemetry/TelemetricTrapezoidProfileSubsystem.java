package frc.robot.utils.telemetry;

import java.util.HashMap;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DataLogEntry;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.util.datalog.IntegerLogEntry;
import edu.wpi.first.util.datalog.StringLogEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.TrapezoidProfileSubsystem;

public abstract class TelemetricTrapezoidProfileSubsystem extends TrapezoidProfileSubsystem {
  private static final DataLog LOG = DataLogManager.getLog();
  private final HashMap<String, DataLogEntry> cache = new HashMap<>();
  protected final ShuffleboardTab telemetryTab = Shuffleboard.getTab(getName());

  public TelemetricTrapezoidProfileSubsystem(TrapezoidProfile.Constraints constraints, double initialPosition, double period) {
    super(constraints, initialPosition, period);
    telemetryInit();
  }

  public TelemetricTrapezoidProfileSubsystem(TrapezoidProfile.Constraints constraints, double initialPosition) {
    super(constraints, initialPosition);
    telemetryInit();
  }

  public TelemetricTrapezoidProfileSubsystem(TrapezoidProfile.Constraints constraints) {
    super(constraints);
    telemetryInit();
  }
  
  @Override public void periodic() {
    super.periodic();
    telemetryPeriodic();
  }

  public void addTelemetry(String name, Sendable sendable) {
    telemetryTab.add(name, sendable);
  }

  public void logDouble(String name, double value, boolean addToTab, boolean log) {
    if (addToTab) telemetryTab.addNumber(name, () -> value);
    if (!log) return;
    if (!cache.containsKey(name)) cache.put(name, new DoubleLogEntry(LOG, name));
    ((DoubleLogEntry) cache.get(name)).append(value);
  }

  public void logDouble(String name, double value) {
    logDouble(name, value, true, true);
  }

  public void logInt(String name, int value, boolean addToTab, boolean log) {
    if (addToTab) telemetryTab.addNumber(name, () -> value);
    if (!log) return;
    if (!cache.containsKey(name)) cache.put(name, new IntegerLogEntry(LOG, name));
    ((IntegerLogEntry) cache.get(name)).append(value);
  }

  public void logInt(String name, int value) {
    logInt(name, value, true, true);
  }

  public void logBoolean(String name, boolean value, boolean addToTab, boolean log) {
    if (addToTab) telemetryTab.addBoolean(name, () -> value);
    if (!log) return;
    if (!cache.containsKey(name)) cache.put(name, new BooleanLogEntry(LOG, name));
    ((BooleanLogEntry) cache.get(name)).append(value);
  }

  public void logBoolean(String name, boolean value) {
    logBoolean(name, value, true, true);
  }

  public void logString(String name, String value, boolean addToTab, boolean log) {
    if (addToTab) telemetryTab.addString(name, () -> value);
    if (!log) return;
    if (!cache.containsKey(name)) cache.put(name, new StringLogEntry(LOG, name));
    ((StringLogEntry) cache.get(name)).append(value);
  }

  public void logString(String name, String value) {
    logString(name, value, true, true);
  }

  public abstract void telemetryInit();

  public abstract void telemetryPeriodic();
}
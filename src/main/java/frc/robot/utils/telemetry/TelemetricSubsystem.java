package frc.robot.utils.telemetry;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class TelemetricSubsystem extends SubsystemBase {
  protected final ShuffleboardTab telemetryTab = Shuffleboard.getTab(getName());

  public TelemetricSubsystem() {
    telemetryInit();
  }

  @Override public void periodic() {
    telemetryPeriodic();
  }

  public void addTelemetry(String name, Sendable sendable) {
    telemetryTab.add(name, sendable);
  }

  public void addDouble(String name, DoubleSupplier supplier) {
    telemetryTab.addDouble(name, supplier);
  }

  public void addInt(String name, LongSupplier supplier) {
    telemetryTab.addInteger(name, supplier);
  }

  public void addBoolean(String name, BooleanSupplier supplier) {
    telemetryTab.addBoolean(name, supplier);
  }

  public void addString(String name, Supplier<String> supplier) {
    telemetryTab.addString(name, supplier);
  }

  public void logDouble(String name, double value) {
    Telemetry.logDouble(name, value);
  }

  public void logInt(String name, int value) {
    Telemetry.logInt(name, value);
  }

  public void logBoolean(String name, boolean value) {
    Telemetry.logBoolean(name, value);
  }

  public void logString(String name, String value) {
    Telemetry.logString(name, value);
  }

  public abstract void telemetryInit();

  public abstract void telemetryPeriodic();
}
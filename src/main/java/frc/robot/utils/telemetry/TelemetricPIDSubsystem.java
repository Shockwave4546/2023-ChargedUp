package frc.robot.utils.telemetry;

import static edu.wpi.first.util.ErrorMessages.requireNonNullParam;

import edu.wpi.first.math.controller.PIDController;

public abstract class TelemetricPIDSubsystem extends TelemetricSubsystem {
  protected final PIDController controller;
  protected boolean enabled;

  public TelemetricPIDSubsystem(PIDController controller, double initialPosition) {
    this.controller = requireNonNullParam(controller, "controller", "TelemetricSubsystem");
    setSetpoint(initialPosition);
    addChild("PID Controller", controller);
    telemetryInit();
  }

  public TelemetricPIDSubsystem(PIDController controller) {
    this(controller, 0.0);
  }

  @Override public void periodic() {
    if (enabled) useOutput(getMeasurement(), getSetpoint());
    telemetryPeriodic();
  }

  public PIDController getController() {
    return controller;
  }

  public void setSetpoint(double setpoint) {
    controller.setSetpoint(setpoint);
  }

  public double getSetpoint() {
    return controller.getSetpoint();
  }

  public void enable() {
    enabled = true;
    controller.reset();
  }

  public void disable() {
    enabled = false;
    useOutput(0, 0);
  }

  public boolean isEnabled() {
    return enabled;
  }

  protected abstract void useOutput(double output, double setpoint);

  protected abstract double getMeasurement();
}
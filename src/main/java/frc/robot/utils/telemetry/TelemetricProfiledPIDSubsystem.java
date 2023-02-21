package frc.robot.utils.telemetry;

import static edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import static edu.wpi.first.util.ErrorMessages.requireNonNullParam;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public abstract class TelemetricProfiledPIDSubsystem extends TelemetricSubsystem {
  protected final ProfiledPIDController controller;
  protected boolean enabled;

  public TelemetricProfiledPIDSubsystem(ProfiledPIDController controller, double initialPosition) {
    this.controller = requireNonNullParam(controller, "controller", "TelemetricProfiledPIDSubsystem");
    setGoal(initialPosition);
    telemetryInit();
  }

  public TelemetricProfiledPIDSubsystem(ProfiledPIDController controller) {
    this(controller, 0.0);
  }

  @Override public void periodic() {
    if (enabled) useOutput(controller.calculate(getMeasurement()), controller.getSetpoint());
    telemetryPeriodic();
  }

  public ProfiledPIDController getController() {
    return controller;
  }

  public void setGoal(TrapezoidProfile.State goal) {
    controller.setGoal(goal);
  }

  public void setGoal(double goal) {
    setGoal(new TrapezoidProfile.State(goal, 0));
  }

  public void enable() {
    enabled = true;
    controller.reset(getMeasurement());
  }

  public void disable() {
    enabled = false;
    useOutput(0, new State());
  }

  public boolean isEnabled() {
    return enabled;
  }
  
  protected abstract void useOutput(double output, State setpoint);

  protected abstract double getMeasurement();
}
package frc.robot.utils.telemetry;

import static edu.wpi.first.util.ErrorMessages.requireNonNullParam;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

public abstract class TelemetricTrapezoidProfileSubsystem extends TelemetricSubsystem {
  private final TrapezoidProfile.Constraints constraints;
  private final double period;
  private TrapezoidProfile.State state;
  private TrapezoidProfile.State goal;
  private boolean enabled = true;

  public TelemetricTrapezoidProfileSubsystem(TrapezoidProfile.Constraints constraints, double initialPosition, double period) {
    this.constraints = requireNonNullParam(constraints, "constraints", "TelemetricTrapezoidProfileSubsystem");
    this.period = period;
    this.state = new TrapezoidProfile.State(initialPosition, 0);
    setGoal(initialPosition);
    telemetryInit();
  }

  public TelemetricTrapezoidProfileSubsystem(TrapezoidProfile.Constraints constraints, double initialPosition) {
    this(constraints, initialPosition, 0.02);
  }

  public TelemetricTrapezoidProfileSubsystem(TrapezoidProfile.Constraints constraints) {
    this(constraints, 0, 0.02);
  }

  @Override public void periodic() {
    final var profile = new TrapezoidProfile(constraints, goal, state);
    state = profile.calculate(period);
    if (enabled) useState(state);
    telemetryPeriodic();
  }

  public void setGoal(TrapezoidProfile.State goal) {
    this.goal = goal;
  }

  public void setGoal(double goal) {
    setGoal(new TrapezoidProfile.State(goal, 0));
  }

  public void enable() {
    enabled = true;
  }

  public void disable() {
    enabled = false;
  }

  protected abstract void useState(TrapezoidProfile.State state);
}
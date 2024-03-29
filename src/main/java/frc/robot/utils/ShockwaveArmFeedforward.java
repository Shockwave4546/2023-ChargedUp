package frc.robot.utils;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * A helper class that computes feedforward outputs for a simple arm (modeled as a motor acting
 * against the force of gravity on a beam suspended at an angle).
 */
public class ShockwaveArmFeedforward implements Sendable {
  private double ks;
  private double kg;
  private double kv;
  private double ka;

  /**
   * Creates a new ArmFeedforward with the specified gains. Units of the gain values will dictate
   * units of the computed feedforward.
   *
   * @param ks The static gain.
   * @param kg The gravity gain.
   * @param kv The velocity gain.
   * @param ka The acceleration gain.
   */
  public ShockwaveArmFeedforward(double ks, double kg, double kv, double ka) {
    this.ks = ks;
    this.kg = kg;
    this.kv = kv;
    this.ka = ka;
  }

  /**
   * Creates a new ArmFeedforward with the specified gains. Acceleration gain is defaulted to zero.
   * Units of the gain values will dictate units of the computed feedforward.
   *
   * @param ks The static gain.
   * @param kg The gravity gain.
   * @param kv The velocity gain.
   */
  public ShockwaveArmFeedforward(double ks, double kg, double kv) {
    this(ks, kg, kv, 0);
  }

  public double getKs() {
    return ks;
  }

  public void setKs(double ks) {
    this.ks = ks;
  }

  public double getKg() {
    return kg;
  }

  public void setKg(double kg) {
    this.kg = kg;
  }

  public double getKv() {
    return kv;
  }

  public void setKv(double kv) {
    this.kv = kv;
  }

  public double getKa() {
    return ka;
  }

  public void setKa(double ka) {
    this.ka = ka;
  }

  /**
   * VERY IMPORTANT NOTE: Math#cos has been changed to Math#sin for the purposes of our Robot design.
   *
   * Calculates the feedforward from the gains and setpoints.
   *
   * @param positionRadians       The position (angle) setpoint. This angle should be measured from the
   *                              horizontal (i.e. if the provided angle is 0, the arm should be parallel with the floor). If
   *                              your encoder does not follow this convention, an offset should be added.
   * @param velocityRadPerSec     The velocity setpoint.
   * @param accelRadPerSecSquared The acceleration setpoint.
   * @return The computed feedforward.
   */
  public double calculate(double positionRadians, double velocityRadPerSec, double accelRadPerSecSquared) {
    return ks * Math.signum(velocityRadPerSec)
            + kg * Math.sin(positionRadians)
            + kv * velocityRadPerSec
            + ka * accelRadPerSecSquared;
  }

  @Override public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("PIDController");
    builder.addDoubleProperty("ks", this::getKs, this::setKs);
    builder.addDoubleProperty("kg", this::getKg, this::setKg);
    builder.addDoubleProperty("kv", this::getKv, this::setKv);
    builder.addDoubleProperty("ka", this::getKa, this::setKa);
  }

  /**
   * Calculates the feedforward from the gains and velocity setpoint (acceleration is assumed to be
   * zero).
   *
   * @param positionRadians The position (angle) setpoint. This angle should be measured from the
   *                        horizontal (i.e. if the provided angle is 0, the arm should be parallel with the floor). If
   *                        your encoder does not follow this convention, an offset should be added.
   * @param velocity        The velocity setpoint.
   * @return The computed feedforward.
   */
  public double calculate(double positionRadians, double velocity) {
    return calculate(positionRadians, velocity, 0);
  }
}

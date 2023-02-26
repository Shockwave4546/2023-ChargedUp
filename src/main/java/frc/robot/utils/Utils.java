package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

/**
 * Class containing small helper functions.
 */
public final class Utils {
  /**
   * Constructor should never be called as this is a utility class.
   */
  private Utils() {
    throw new UnsupportedOperationException("The util class should never get instantiated!");
  }
  
  /**
   * Sets brake mode on a victor spx motor.
   * 
   * @param sparkMax the motor to be configured.
   * @return the configured motor.
   */
  public static CANSparkMax configureSparkMax(CANSparkMax sparkMax) {
    sparkMax.setIdleMode(IdleMode.kBrake);
    sparkMax.burnFlash();
    return sparkMax;
  }

  /**
   * Sets brake mode on a victor spx motor.
   * 
   * @param victorSPX the motor to be configured.
   * @return the configured motor.
   */
  public static WPI_VictorSPX configureVictorSPX(WPI_VictorSPX victorSPX) {
    victorSPX.setNeutralMode(NeutralMode.Brake);
    return victorSPX;
  }
}
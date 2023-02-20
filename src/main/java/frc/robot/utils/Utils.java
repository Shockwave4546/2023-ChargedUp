package frc.robot.utils;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

public final class Utils {
  private Utils() {
    throw new UnsupportedOperationException("The util class should never get instantiated!");
  }

  public static void configureSparkMax(CANSparkMax sparkMax) {
    sparkMax.setIdleMode(IdleMode.kBrake);
    sparkMax.burnFlash();
  }
}

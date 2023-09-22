package frc.robot;

import edu.wpi.first.math.util.Units;

/*
 * 
 */
public class Constants {
  private static final double QUAD_ENCODER_PULSES_PER_REVOLUTION = 2048.0;

  /**
   * 
   */
  public static class IO {
    public static final int DRIVE_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;
  }

  /**
   * 
   */
  public static class Drive {
    private static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(6);
    public static final double DISTANCE_PER_PULSE = (Math.PI * WHEEL_DIAMETER_METERS) / QUAD_ENCODER_PULSES_PER_REVOLUTION;
    public static final double TRACK_WIDTH_METERS = Units.inchesToMeters(24);

    public static final int FRONT_LEFT_ID = 2;
    public static final int BACK_LEFT_ID = 3;
    public static final int FRONT_RIGHT_ID = 0;
    public static final int BACK_RIGHT_ID = 1;
    public static final int[] LEFT_ENCODER = new int[] {0, 1};
    public static final int[] RIGHT_ENCODER = new int[] {2, 3};

    public static final double KS_VOLTS = 2.55921;
    public static final double KV_VOLT_SECONDS_PER_METER = 1.0509;
    public static final double KA_VOLT_SECONDS_SQUARED_PER_METER = 1.8342;
    public static final double P_DRIVE_VELOCITY = 0.1509;
    
    // Source: https://docs.wpilib.org/en/stable/docs/software/pathplanning/trajectory-tutorial/entering-constants.html#:~:text=public%20static%20final%20double%20kRamseteB,double%20kRamseteZeta%20%3D%200.7%3B
    public static final double RAMSETE_B = 2;
    public static final double RAMSETE_ZETA = 0.7;

    public static final double BALANCE_P = 0.0015;
    public static final double BALANCE_I = 0.0;
    public static final double BALANCE_D = 0.0;
  }

  /**
   * 
   */
  public static class Intake {
    public static final int MOTOR_ID = 4;
    public static final double HOLD_SPEED = 0.07;
    public static final double PICK_UP_SPEED = 1.00;
    public static final double RELEASE_SPEED = 1.00;
  }

  /**
   * 
   */
  public static class UpperPivot {
    public static final double DISTANCE_PER_PULSE = 360.0 / QUAD_ENCODER_PULSES_PER_REVOLUTION;
    public static final int MOTOR_ID = 5;
    public static final double P = 0.087182;
    public static final double I = 0.0;
    public static final double D = 0.0020132;
    public static final double KS = 0.16466;
    public static final double KV = 0.042168;
    public static final double KA = 0.00092383;
    public static final double KG = 0.12365;
    public static final double MAX_VELOCITY = 250;
    public static final double MAX_ACCELERATION = 125;
    public static final int[] ENCODER = new int[] {7, 8};
  } 

  /**
   * 
   */
  public static class Winch {
    public static final int MOTOR_ID = 6;
    public static final double DEFAULT_SPEED = 1.00;
    public static final double SETPOINT_TOLERANCE = 1.0; // degrees
  }

  /**
   * 
   */
  public static class LED {
    public static final int LED_PORT = 0;
    public static final int BUFFER_LENGTH = 120;
  }
}
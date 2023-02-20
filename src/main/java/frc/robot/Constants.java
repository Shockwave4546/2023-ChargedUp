package frc.robot;

import edu.wpi.first.math.util.Units;

public class Constants {
  public static class IO {
    public static final int DRIVE_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;
  }

  public static class Drive {
    private static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(6);
    private static final double PULSES_PER_REVOLUTION = 2048;
    public static final double DISTANCE_PER_PULSE =  (Math.PI * WHEEL_DIAMETER_METERS) / PULSES_PER_REVOLUTION;
    public static final double TRACK_WIDTH_METERS = Units.inchesToMeters(24);

    public static final int FRONT_LEFT_ID = 0;
    public static final int BACK_LEFT_ID = 0;
    public static final int FRONT_RIGHT_ID = 0;
    public static final int BACK_RIGHT_ID = 0;
    public static final int[] LEFT_ENCODER = new int[] {0, 1};
    public static final int[] RIGHT_ENCODER = new int[] {2, 3};

    public static final double KS_VOLTS = 1.24;
    public static final double KV_VOLT_SECONDS_PER_METER = 2.9;
    public static final double KA_VOLT_SECONDS_SQUARED_PER_METER = 1.1;
    public static final double P_DRIVE_VELOCITY = 1.5;
    
    // Source: https://docs.wpilib.org/en/stable/docs/software/pathplanning/trajectory-tutorial/entering-constants.html#:~:text=public%20static%20final%20double%20kRamseteB,double%20kRamseteZeta%20%3D%200.7%3B
    public static final double RAMSETE_B = 2;
    public static final double RAMSETE_ZETA = 0.7;
  }

  public static class Intake {
    public static final int MOTOR_ID = 4;
  }

  public static class Arm {
    private static final float GEAR_RATIO = 125.0F;
    public static final float POSITION_CONVERSION_FACTOR = (1.0F / GEAR_RATIO) * 360.0F;

    public static class LowerPivot {
      public static final int MOTOR_ID = 1; 
      public static final double P = 0.20193;
      public static final double I = 0.0;
      public static final double D = 0.0051316;
      public static final double KS = 0.37344;
      public static final double KV = 2.44;
      public static final double KA = 0.12;
      public static final double KG = 2.26;
      public static final double MAX_VELOCITY = 100;
      public static final double MAX_ACCELERATION = 250;
    }

    public static class UpperPivot {
      public static final int MOTOR_ID = 2;
      public static final double P = 0.087182;
      public static final double I = 0.0;
      public static final double D = 0.0020132;
      public static final double KS = 0.16466;
      public static final double KV = 0.042168;
      public static final double KA = 0.00092383;
      public static final double KG = 0.12365;
      public static final double MAX_VELOCITY = 500;
      public static final double MAX_ACCELERATION = 250;
    }
  } 
}

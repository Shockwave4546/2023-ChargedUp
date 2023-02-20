package frc.robot;

public class Constants {
  public static class Intake {
    public static final int ID = 4;
  }

  public static class Arm {
    private static final float GEAR_RATIO = 125.0F;
    public static final float POSITION_CONVERSION_FACTOR = (1.0F / GEAR_RATIO) * 360.0F;

    public static class LowerPivot {
      public static final int ID = 1; 
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
      public static final int ID = 2;
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

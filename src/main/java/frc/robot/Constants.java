package frc.robot;

public class Constants {
  public static class Arm {
    private static final double COUNT_PER_REV = 42.0;

    public static class LowerPivot {
      private static final double GEAR_RATIO = 64.0;
      public static final int ID = 1; 
      public static final double P = 0.09;
      public static final double I = 0.0;
      public static final double D = 0.015;
      public static final double COUNT_PER_DEGREE = (COUNT_PER_REV * GEAR_RATIO) / 360.0; // 7.466 count/degree
      public static final double MAX_VELOCITY = 200;
      public static final double MAX_ACCELERATION = 120;
    }

    public static class UpperPivot {
      private static final double GEAR_RATIO = 125.0;
      public static final int ID = 2;
      public static final double P = 0.09;
      public static final double I = 0.0;
      public static final double D = 0.015;
      public static final double COUNT_PER_DEGREE = (COUNT_PER_REV * GEAR_RATIO) / 360.0; // 14.583 count/degree
      public static final double MAX_VELOCITY = 200;
      public static final double MAX_ACCELERATION = 120;
    }
  } 
}

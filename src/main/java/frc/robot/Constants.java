package frc.robot;

public class Constants {
  public static class Arm {
    private static final double COUNT_PER_REV = 42.0;

    public static class LowerPivot {
      private static final double GEAR_RATIO = 64.0;
      public static final int ID = 0; // TODO: Change
      public static final double P = 0.0;
      public static final double I = 0.0;
      public static final double D = 0.0;
      public static final double COUNT_PER_DEGREE = (COUNT_PER_REV * GEAR_RATIO) / 360.0; // 7.466 count/degree
    }

    public static class UpperPivot {
      private static final double GEAR_RATIO = 64.0;
      public static final int ID = 2;
      public static final double P = 0.05;
      public static final double I = 0.0;
      public static final double D = 0.009;
      public static final double COUNT_PER_DEGREE = (COUNT_PER_REV * GEAR_RATIO) / 360.0; // 7.466 count/degree
    }
  } 
}

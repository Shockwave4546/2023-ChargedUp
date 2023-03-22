package frc.robot;

public enum PositionPreset {
  HUMAN_PLAYER_PICKUP_CONE(90.0, 25.0),
  HUMAN_PLAYER_PICKUP_CUBE(106.0, 25.0),
  FLOOR_CONE(0.0, 75.0),
  FLOOR_CUBE(0.0, 80.0),
  HIGH_CONE(105.0, 27.0),
  MID_CONE(109.0, 20.0),
  CUBE(90, 40.0),
  STARTING(0.0, 0.0),
  DRIVING(60.0, 0.0);
  
  public final double upperPivotAngle;
  public final double winchAngle;

  PositionPreset(double upperPivotAngle, double winchAngle) {
    this.upperPivotAngle = upperPivotAngle;
    this.winchAngle = winchAngle;
  }
}